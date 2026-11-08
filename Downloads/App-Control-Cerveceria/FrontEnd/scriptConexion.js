// Si el front se sirve desde otro origen que el servlet, definir antes: window.API_BASE = "http://localhost:8080/Cerveceria"
const API_BASE = window.API_BASE ?? "";

const ESTADOS = ["Sucio/Retornado", "Lavado/Satinizado", "Lleno", "En Uso", "En Mantenimiento", "Extraviado/Baja"];
const COLOR = {
    "Sucio/Retornado": "#8A6D3B",   /* marrón tierra   */
    "Lavado/Satinizado": "#3B7EA1",   /* azul acero      */
    "Lleno": "#3A7D44",   /* verde salvia     */
    "En Uso": "#0D7E5A",   /* verde esmeralda  */
    "En Mantenimiento": "#7A5BA6",   /* violeta          */
    "Extraviado/Baja": "#B23A2E"    /* rojo ladrillo    */
};
const ETAPAS = ["Fermentación/Maduración", "Para Envasar", "Envasados"];

const $ = (s, r = document) => r.querySelector(s);
const esc = v => String(v ?? "").replace(/[&<>"']/g, c => ({ "&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#39;" }[c]));
const local = v => new Date(String(v).slice(0, 10) + "T00:00:00");
const fecha = v => { if (!v) return "—"; const d = local(v); return isNaN(d) ? esc(v) : d.toLocaleDateString("es-AR"); };
const pill = v => `<span class="pill" style="--c:${COLOR[v] ?? ""}">${esc(v)}</span>`;

/* ---------- Entidades (columnas y formularios) ----------
   cols: [campo, título, formato opcional]
   form: [campo, título, tipo, opciones | ref, soloAlta]  */
const E = {
    barriles: {
        label: "Barriles", path: "/api/barriles", id: "bCod", nuevo: true, editar: true,
        cols: [["bCod", "N°"], ["bCapacidad", "Capacidad (L)"], ["bTipoConector", "Conector"],
        ["bEstadoOperativo", "Estado", pill], ["bUbicacionActual", "Ubicación"]],
        form: [["bCapacidad", "Capacidad (L)", "number"], ["bTipoConector", "Conector", "select", ["G", "A", "S", "Micromatic"]],
        ["bEstadoOperativo", "Estado", "select", ESTADOS], ["bUbicacionActual", "Ubicación actual", "text"],
        ["inventarioICod", "Inventario", "ref", ["inventario", r => `#${r.iCod} · ${r.iEstadoEtapa}`]],
        ["loteCod", "Lote de origen", "ref", ["lotes", r => `Lote #${r.lotCod}`], true],
        ["fechaInicio", "Fecha de inicio", "date", null, true]]
    },
    lotes: {
        label: "Lotes", path: "/api/lotes", id: "lotCod", nuevo: true, editar: false,
        cols: [["lotCod", "N°"], ["recetasRCod", "Receta"], ["lotVolumenTotal", "Volumen (L)"],
        ["lotFechaVecimiento", "Vence", fecha], ["lotFechaFinPreparado", "Fin de preparado", fecha]],
        form: [["recetasRCod", "N° de receta", "number"], ["lotVolumenTotal", "Volumen total (L)", "number"],
        ["lotFechaVecimiento", "Fecha de vencimiento", "date"]]
    },
    clientes: {
        label: "Clientes", path: "/api/clientes", id: "clCod", nuevo: true, editar: true,
        cols: [["clNombre", "Nombre"], ["clDireccion", "Dirección"], ["clTelefono", "Teléfono"]],
        form: [["clNombre", "Nombre", "text"], ["clDireccion", "Dirección", "text"], ["clTelefono", "Teléfono", "tel"]]
    },
    entregas: {
        label: "Entregas", path: "/api/entrega", id: "enCod", nuevo: true, editar: true,
        cols: [["enRemitoFactura", "Remito / factura"], ["clientesClCod", "Cliente", (v, r, d) => esc(d.clientes?.[v] ?? "#" + v)],
        ["barrilesBCod", "Barril", v => "#" + v], ["enFechaEntrega", "Entrega", fecha],
        ["enFechaDevolucion", "Devolución", v => v ? fecha(v) : '<span class="pill" style="--c:#d4901a">Pendiente</span>']],
        form: [["enRemitoFactura", "Remito o factura", "text"], ["clientesClCod", "Cliente", "ref", ["clientes", r => r.clNombre]],
        ["barrilesBCod", "Barril", "ref", ["barriles", r => `#${r.bCod} · ${r.bCapacidad} L · ${r.bEstadoOperativo}`]],
        ["enFechaEntrega", "Fecha de entrega", "date"], ["enTiempoPermanencia", "Días previstos en el cliente", "number"],
        ["enFechaDevolucion", "Fecha de devolución (dejar vacía si no volvió)", "date"]]
    },
    movimientos: {
        label: "Movimientos", path: "/api/movimientos", id: "movCod", nuevo: true, editar: false,
        cols: [["movFecha", "Fecha", fecha], ["movHora", "Hora", v => v + ":00"], ["barrilesBCod", "Barril", v => "#" + v],
        ["movPuntoOrigen", "Origen"], ["movPuntoDestino", "Destino"], ["movResponsable", "Responsable"]],
        form: [["barrilesBCod", "Barril", "ref", ["barriles", r => `#${r.bCod} · ${r.bUbicacionActual}`]],
        ["movFecha", "Fecha", "date"], ["movHora", "Hora (0 a 23)", "number"], ["movPuntoOrigen", "Origen", "text"],
        ["movPuntoDestino", "Destino", "text"], ["movResponsable", "Responsable", "text"]]
    },
    inventario: {
        label: "Inventario", path: "/api/inventario", id: "iCod", nuevo: false, editar: false,
        cols: [["iCod", "N°"], ["iEstadoEtapa", "Etapa"], ["iLitrosTotales", "Litros totales"]], form: []
    }
};
/* Recetas: sigue el contrato del procedimiento receta_alta (proceso existente + un grano, lúpulo, levadura y aditivo, existentes o nuevos) */
const n = (k, t) => [k, t, "number"], tx = (k, t) => [k, t, "text"];
const nuevoDe = (g, campos) => campos.map(([k, t, tipo, op]) => [k, t, tipo, op, false, g]);
const cat = (label, path, id, cols, form) => ({ label, path, id, nuevo: true, editar: true, cols: [[id, "N°"], ...cols], form });
Object.assign(E, {
    recetas: {
        label: "Recetas", path: "/api/recetas", id: "rCod", nuevo: true, editar: false,
        cols: [["rCodigo", "Código"], ["rNombre", "Nombre"], ["rVersion", "Versión"],
        ["estiloPtCod", "Estilo", (v, r, d) => esc(d.estilos?.[v] ?? "#" + v)], ["rBatchSize", "Lote (L)"], ["rAutor", "Autor"], ["rFechaC", "Creada", fecha]],
        form: [tx("rNombre", "Nombre"), tx("rCodigo", "Código de receta"), n("rVersion", "Versión"), n("rBatchSize", "Tamaño de lote (L)"),
        n("rEficienciaSize", "Eficiencia (%)"), tx("rAutor", "Autor"), ["rFechaC", "Fecha de creación", "date"],
        ["", "Proceso", "h"],
        ["estiloPtCod", "Estilo", "ref", ["estilos", r => r.ptEstiloNombre]],
        ["maceracionMCod", "Maceración", "ref", ["maceraciones", r => `#${r.mCod} · agua/grano ${r.mRelacionAguaGrano}`]],
        ["hervidoHCod", "Hervido", "ref", ["hervidos", r => `#${r.hCod} · ${r.hTiempoEbullicion} min`]],
        ["whirpoolWCod", "Whirlpool", "ref", ["whirpools", r => `#${r.wCod} · reposo ${r.wTiempoReposo} min`]],
        ["fermentacionFCod", "Fermentación", "ref", ["fermentaciones", r => `#${r.fCod}`]],
        ["dryHoppingFDryCod", "Dry hopping", "refopt", ["dryhopping", r => `#${r.fDryCod} · ${r.tiempoContacto} h a ${r.temperatura} °C`, "Sin dry hopping"]],
        ["carbonatacionCarCod", "Carbonatación", "ref", ["carbonataciones", r => `#${r.carCod} · ${r.carVolObjCO2} vol. · ${r.carMetodoEmpleado}`]],
        ["", "Grano", "h"], ["gCod", "Usar uno existente", "refopt", ["granos", r => r.gNombre, "Crear nuevo…"]],
        ...nuevoDe("gCod", [tx("gNombre", "Nombre"), tx("gTipo", "Tipo"), n("gPorcentaje", "Porcentaje (%)"), n("gPeso", "Peso"), tx("gPotencialExtraccion", "Potencial de extracción"), tx("gColor", "Color")]),
        ["", "Lúpulo", "h"], ["luCod", "Usar uno existente", "refopt", ["lupulos", r => `${r.luVariedad} ${r.luAnioCosecha}`, "Crear nuevo…"]],
        ...nuevoDe("luCod", [tx("luVariedad", "Variedad"), n("luAnioCosecha", "Año de cosecha"), tx("luFormato", "Formato"), n("luPorcentajeAlfaAcido", "Alfa ácidos (%)"), n("luCantidad", "Cantidad"), ["luProposito", "Propósito", "select", ["Amargor", "Sabor", "Aroma"]]]),
        ["", "Levadura", "h"], ["leCod", "Usar una existente", "refopt", ["levaduras", r => r.leCepa, "Crear nueva…"]],
        ...nuevoDe("leCod", [tx("leCepa", "Cepa"), ["leFormatoLev", "Formato", "select", ["Seca", "Líquida"]], n("leCantidadLev", "Cantidad"), n("leTasaInoculacion", "Tasa de inoculación")]),
        ["", "Aditivo", "h"], ["aCod", "Usar uno existente", "refopt", ["aditivos", r => r.nombreAditivo, "Crear nuevo…"]],
        ...nuevoDe("aCod", [tx("nombreAditivo", "Nombre"), tx("tipoAditivo", "Tipo"), n("cantidadAditivo", "Cantidad")])]
    },
    lotes: {
        ...E.lotes,
        form: [["recetasRCod", "Receta", "ref", ["recetas", r => `${r.rCodigo} · ${r.rNombre} v${r.rVersion}`]],
        n("lotVolumenTotal", "Volumen total (L)"), ["lotFechaVecimiento", "Fecha de vencimiento", "date"],
        ["fechaInicioComp", "Fecha de inicio del llenado", "date"],
        ["", "Barril", "h"], ["barrilesBCod", "Usar un barril existente", "refopt", ["barriles", r => `#${r.bCod} · ${r.bCapacidad} L · ${r.bEstadoOperativo}`, "Crear barril nuevo…"]],
        ...nuevoDe("barrilesBCod", [n("bCapacidad", "Capacidad (L)"), ["bTipoConector", "Conector", "select", ["G", "A", "S", "Micromatic"]],
        ["bEstadoOperativo", "Estado", "select", ESTADOS], tx("bUbicacionActual", "Ubicación actual"),
        ["inventarioICod", "Inventario", "ref", ["inventario", r => `#${r.iCod} · ${r.iEstadoEtapa}`]]])]
    },
    estilos: cat("Estilos", "/api/estilos", "ptCod", [["ptEstiloNombre", "Estilo"], ["ptAlcoholMinimo", "Alcohol mín."], ["ptAlcoholMaximo", "Alcohol máx."], ["ptAmargorMinimo", "Amargor mín."], ["ptAmargorMaximo", "Amargor máx."]],
        [tx("ptEstiloNombre", "Nombre del estilo"), n("ptDensidadIniMinima", "Densidad inicial mínima"), n("ptDensidadIniMaxima", "Densidad inicial máxima"), n("ptDensidadFinMinima", "Densidad final mínima"),
        n("ptDensidadFinMaxima", "Densidad final máxima"), n("ptAlcoholMinimo", "Alcohol mínimo"), n("ptAlcoholMaximo", "Alcohol máximo"), n("ptAmargorMinimo", "Amargor mínimo"),
        n("ptAmargorMaximo", "Amargor máximo"), n("ptColorMinimo", "Color mínimo"), n("ptColorMaximo", "Color máximo")]),
    maceraciones: cat("Maceraciones", "/api/maceraciones", "mCod", [["mRelacionAguaGrano", "Relación agua/grano"]], [n("mRelacionAguaGrano", "Relación agua/grano")]),
    hervidos: cat("Hervidos", "/api/hervidos", "hCod", [["hTiempoEbullicion", "Ebullición (min)"]], [n("hTiempoEbullicion", "Tiempo de ebullición (min)")]),
    whirpools: cat("Whirlpools", "/api/whirpools", "wCod", [["wTiempoInicio", "Inicio"], ["wTiempoCentrifugado", "Centrifugado"], ["wTiempoReposo", "Reposo"]],
        [n("wTiempoInicio", "Tiempo de inicio"), n("wTiempoCentrifugado", "Tiempo de centrifugado"), n("wTiempoReposo", "Tiempo de reposo")]),
    fermentaciones: { ...cat("Fermentaciones", "/api/fermentaciones", "fCod", [], []), editar: false },
    dryhopping: cat("Dry hopping", "/api/dryhopping", "fDryCod", [["tiempoContacto", "Contacto (h)"], ["temperatura", "Temperatura (°C)"]], [n("tiempoContacto", "Tiempo de contacto (h)"), n("temperatura", "Temperatura (°C)")]),
    carbonataciones: cat("Carbonataciones", "/api/carbonataciones", "carCod", [["carVolObjCO2", "Volúmenes de CO₂"], ["carMetodoEmpleado", "Método"]], [n("carVolObjCO2", "Volúmenes de CO₂ objetivo"), tx("carMetodoEmpleado", "Método empleado")]),
    granos: cat("Granos", "/api/granos", "gCod", [["gNombre", "Nombre"], ["gTipo", "Tipo"], ["gPorcentaje", "%"], ["gPeso", "Peso"], ["gColor", "Color"]],
        [tx("gNombre", "Nombre"), tx("gTipo", "Tipo"), n("gPorcentaje", "Porcentaje (%)"), n("gPeso", "Peso"), tx("gPotencialExtraccion", "Potencial de extracción"), tx("gColor", "Color")]),
    lupulos: cat("Lúpulos", "/api/lupulos", "luCod", [["luVariedad", "Variedad"], ["luAnioCosecha", "Cosecha"], ["luFormato", "Formato"], ["luPorcentajeAlfaAcido", "Alfa ácidos (%)"], ["luProposito", "Propósito"]],
        [tx("luVariedad", "Variedad"), n("luAnioCosecha", "Año de cosecha"), tx("luFormato", "Formato"), n("luPorcentajeAlfaAcido", "Alfa ácidos (%)"), n("luCantidad", "Cantidad"), ["luProposito", "Propósito", "select", ["Amargor", "Sabor", "Aroma"]]]),
    levaduras: cat("Levaduras", "/api/levaduras", "leCod", [["leCepa", "Cepa"], ["leFormatoLev", "Formato"], ["leCantidadLev", "Cantidad"], ["leTasaInoculacion", "Inoculación"]],
        [tx("leCepa", "Cepa"), ["leFormatoLev", "Formato", "select", ["Seca", "Líquida"]], n("leCantidadLev", "Cantidad"), n("leTasaInoculacion", "Tasa de inoculación")]),
    aditivos: cat("Aditivos", "/api/aditivos", "aCod", [["nombreAditivo", "Nombre"], ["tipoAditivo", "Tipo"], ["cantidadAditivo", "Cantidad"]], [tx("nombreAditivo", "Nombre"), tx("tipoAditivo", "Tipo"), n("cantidadAditivo", "Cantidad")])
});
const CAT = ["estilos", "maceraciones", "hervidos", "whirpools", "fermentaciones", "dryhopping", "carbonataciones", "granos", "lupulos", "levaduras", "aditivos"];
const MENU = { inicio: "Inicio", barriles: "Barriles", entregas: "Entregas", clientes: "Clientes", lotes: "Lotes", recetas: "Recetas", catalogos: "Catálogos", movimientos: "Movimientos", inventario: "Inventario" };

/* ---------- Conexión con el servidor (con modo demo) ---------- */
const hoy = (d = 0) => new Date(Date.now() - d * 864e5).toISOString().slice(0, 10);
const SEED = {
    inventario: [{ iCod: 1, iLitrosTotales: 800, iEstadoEtapa: ETAPAS[0] }, { iCod: 2, iLitrosTotales: 300, iEstadoEtapa: ETAPAS[1] }, { iCod: 3, iLitrosTotales: 450, iEstadoEtapa: ETAPAS[2] }],
    lotes: [{ lotCod: 1, recetasRCod: 1, lotVolumenTotal: 500, lotFechaVecimiento: hoy(-90), lotFechaFinPreparado: hoy(20) }],
    clientes: [{ clCod: 1, clNombre: "Bar El Fogón", clDireccion: "Belgrano 120", clTelefono: "2664-555-0101" }, { clCod: 2, clNombre: "Resto La Esquina", clDireccion: "Rivadavia 45", clTelefono: "2664-555-0102" }],
    barriles: [
        { bCod: 1, bCapacidad: 30, bTipoConector: "S", bEstadoOperativo: "Lleno", bUbicacionActual: "Cámara fría", inventarioICod: 3 },
        { bCod: 2, bCapacidad: 50, bTipoConector: "A", bEstadoOperativo: "En Uso", bUbicacionActual: "Bar El Fogón", inventarioICod: 3 },
        { bCod: 3, bCapacidad: 30, bTipoConector: "G", bEstadoOperativo: "Sucio/Retornado", bUbicacionActual: "Depósito", inventarioICod: 3 },
        { bCod: 4, bCapacidad: 20, bTipoConector: "Micromatic", bEstadoOperativo: "Lavado/Satinizado", bUbicacionActual: "Planta", inventarioICod: 3 }],
    entregas: [
        { enCod: 1, enRemitoFactura: "R-0001", enFechaEntrega: hoy(12), enTiempoPermanencia: 7, enFechaDevolucion: null, barrilesBCod: 2, clientesClCod: 1 },
        { enCod: 2, enRemitoFactura: "R-0002", enFechaEntrega: hoy(20), enTiempoPermanencia: 7, enFechaDevolucion: hoy(14), barrilesBCod: 3, clientesClCod: 2 }],
    movimientos: [{ movCod: 1, movFecha: hoy(12), movHora: 10, movPuntoOrigen: "Cámara fría", movPuntoDestino: "Bar El Fogón", movResponsable: "Leo", barrilesBCod: 2 }]
};
Object.assign(SEED, {
    estilos: [{ ptCod: 1, ptEstiloNombre: "American IPA", ptDensidadIniMinima: 1.056, ptDensidadIniMaxima: 1.075, ptDensidadFinMinima: 1.008, ptDensidadFinMaxima: 1.014, ptAlcoholMinimo: 5.5, ptAlcoholMaximo: 7.5, ptAmargorMinimo: 40, ptAmargorMaximo: 70, ptColorMinimo: 6, ptColorMaximo: 14 }],
    maceraciones: [{ mCod: 1, mRelacionAguaGrano: 3 }], hervidos: [{ hCod: 1, hTiempoEbullicion: 60 }],
    whirpools: [{ wCod: 1, wTiempoInicio: 0, wTiempoCentrifugado: 5, wTiempoReposo: 15 }], fermentaciones: [{ fCod: 1 }],
    dryhopping: [{ fDryCod: 1, tiempoContacto: 72, temperatura: 18 }], carbonataciones: [{ carCod: 1, carVolObjCO2: 2.4, carMetodoEmpleado: "Forzada" }],
    granos: [{ gCod: 1, gNombre: "Pale Ale", gTipo: "Base", gPorcentaje: 90, gPeso: 5, gPotencialExtraccion: "80%", gColor: "3 SRM" }],
    lupulos: [{ luCod: 1, luVariedad: "Cascade", luAnioCosecha: 2025, luFormato: "Pellet", luPorcentajeAlfaAcido: 5.5, luCantidad: 60, luProposito: "Aroma" }],
    levaduras: [{ leCod: 1, leCepa: "US-05", leFormatoLev: "Seca", leCantidadLev: 11, leTasaInoculacion: 0.75 }],
    aditivos: [{ aCod: 1, nombreAditivo: "Irish Moss", tipoAditivo: "Clarificante", cantidadAditivo: 5 }],
    recetas: [{ rCod: 1, rNombre: "IPA de la casa", rCodigo: "IPA-001", rVersion: 1, rBatchSize: 50, rEficienciaSize: 72, rAutor: "Leo", rFechaC: hoy(30), estiloPtCod: 1, hervidoHCod: 1, whirpoolWCod: 1, fermentacionFCod: 1, dryHoppingFDryCod: 1, carbonatacionCarCod: 1, maceracionMCod: 1 }]
});
let demo = false;
const store = {};

const api = {
    async list(k) {
        if (!demo) {
            try {
                const r = await fetch(API_BASE + E[k].path);
                if (!r.ok) throw new Error(r.status);
                return await r.json();
            } catch { demo = true; $("#demo").hidden = false; }
        }
        return store[k] ??= structuredClone(SEED[k]);
    },
    async save(k, obj, esNuevo) {
        const id = E[k].id;
        if (!demo) {
            const r = await fetch(API_BASE + E[k].path, {
                method: esNuevo ? "POST" : "PUT", headers: { "Content-Type": "application/json" }, body: JSON.stringify(obj)
            });
            if (!r.ok) {
                const j = await r.json().catch(() => ({}));
                throw new Error(j.error || `El servidor rechazó los datos (código ${r.status}).`);
            }
            return;
        }
        const filas = await api.list(k);
        if (esNuevo) filas.push({ ...obj, [id]: Math.max(0, ...filas.map(x => x[id])) + 1 });
        else Object.assign(filas.find(x => x[id] == obj[id]), obj);
    }
};

/* ---------- Vistas ---------- */
function tabla(cols, filas, extra = {}, accion = "") {
    if (!filas.length) return '<p class="vacio">No hay registros todavía.</p>';
    const th = cols.map(c => `<th>${c[1]}</th>`).join("") + (accion ? "<th></th>" : "");
    const tr = filas.map(r => "<tr>" + cols.map(([k, , f]) => `<td>${f ? f(r[k], r, extra) : esc(r[k])}</td>`).join("")
        + (accion ? `<td>${accion(r)}</td>` : "") + "</tr>").join("");
    return `<div class="tabla-wrap"><table><thead><tr>${th}</tr></thead><tbody>${tr}</tbody></table></div>`;
}

let filtroInicial = "";

async function mostrar(k) {
    const activo = CAT.includes(k) ? "catalogos" : k;
    document.querySelectorAll("nav button").forEach(b => b.dataset.k === activo ? b.setAttribute("aria-current", "page") : b.removeAttribute("aria-current"));
    const m = $("#vista");
    m.innerHTML = '<p class="vacio">Cargando…</p>';
    if (k === "inicio") return inicio(m);
    if (k === "catalogos") return catalogos(m);
    const e = E[k];
    const [filas, clientes, estilos] = await Promise.all([api.list(k), k === "entregas" ? api.list("clientes") : [], k === "recetas" ? api.list("estilos") : []]);
    const extra = { clientes: Object.fromEntries(clientes.map(c => [c.clCod, c.clNombre])), estilos: Object.fromEntries(estilos.map(x => [x.ptCod, x.ptEstiloNombre])) };
    m.innerHTML = `<div class="barra"><h2>${e.label}</h2>
        <input type="search" id="q" placeholder="Buscar" aria-label="Buscar en ${e.label}" value="${esc(filtroInicial)}">
        ${CAT.includes(k) ? '<button class="btn sec" id="volver">Catálogos</button>' : ""}
        ${e.nuevo ? '<button class="btn" id="nuevo">Agregar</button>' : ""}</div><div id="lista"></div>`;
    const pintar = () => {
        const q = $("#q").value.toLowerCase();
        const vis = filas.filter(r => !q || JSON.stringify(r).toLowerCase().includes(q));
        $("#lista").innerHTML = tabla(e.cols, vis, extra, e.editar ? r => `<button class="btn sec" data-id="${r[e.id]}">Editar</button>` : "");
    };
    filtroInicial = "";
    pintar();
    $("#q").oninput = pintar;
    $("#lista").onclick = ev => {
        const b = ev.target.closest("[data-id]");
        if (b) formulario(k, filas.find(r => r[e.id] == b.dataset.id));
    };
    if (e.nuevo) $("#nuevo").onclick = () => formulario(k, null);
    if (CAT.includes(k)) $("#volver").onclick = () => mostrar("catalogos");
}

function catalogos(m) {
    m.innerHTML = `<h2>Catálogos de recetas</h2><p class="vacio">Cargá estos datos primero: la receta los elige al crearse.</p>
        <div class="ciclo">${CAT.map(c => `<button class="paso" style="--c:#0D7E5A" data-k="${c}"><b style="font-size:1.4rem">${E[c].label}</b></button>`).join("")}</div>`;
    m.querySelector(".ciclo").onclick = ev => { const b = ev.target.closest("[data-k]"); if (b) mostrar(b.dataset.k); };
}

async function inicio(m) {
    const [b, inv, en, cl] = await Promise.all(["barriles", "inventario", "entregas", "clientes"].map(k => api.list(k)));
    const nombres = Object.fromEntries(cl.map(c => [c.clCod, c.clNombre]));
    const litros = s => inv.filter(x => x.iEstadoEtapa === s).reduce((a, x) => a + x.iLitrosTotales, 0);
    const pend = en.filter(x => !x.enFechaDevolucion);
    const dias = r => Math.floor((Date.now() - local(r.enFechaEntrega)) / 864e5);
    m.innerHTML = `<section><h2>Barriles por estado</h2><div class="ciclo">${ESTADOS.map(s =>
        `<button class="paso" style="--c:${COLOR[s]}" data-estado="${s}"><b>${b.filter(x => x.bEstadoOperativo === s).length}</b><span>${s}</span></button>`).join("")}</div></section>
        <section><h2>Cerveza en planta</h2><div class="litros">${ETAPAS.map(s => `<div><b>${litros(s)} L</b><span>${s}</span></div>`).join("")}</div></section>
        <section><h2>Barriles afuera sin devolver</h2>${tabla([["clientesClCod", "Cliente", (v, r, d) => esc(nombres[v] ?? "#" + v)],
        ["barrilesBCod", "Barril", v => "#" + v], ["enFechaEntrega", "Entregado", fecha],
        ["enCod", "Días afuera", (v, r) => { const d = dias(r), tarde = d > r.enTiempoPermanencia; return `<span class="${tarde ? "atraso" : ""}">${d}${tarde ? " (pasado del plazo)" : ""}</span>`; }]], pend)}</section>`;
    m.querySelector(".ciclo").onclick = ev => {
        const p = ev.target.closest("[data-estado]");
        if (p) { filtroInicial = p.dataset.estado; mostrar("barriles"); }
    };
}

async function formulario(k, fila) {
    const e = E[k], esNuevo = !fila, f = $("#frm");
    const refs = {};
    await Promise.all(e.form.filter(c => c[2]?.startsWith("ref")).map(async c => refs[c[0]] = await api.list(c[3][0])));
    const campos = e.form.filter(c => esNuevo || !c[4]).map(([n, t, tipo, op, , g]) => {
        if (tipo === "h") return `<h3>${t}</h3>`;
        const val = fila?.[n] ?? "";
        let ctl;
        if (tipo === "select") ctl = `<select name="${n}" required>${op.map(o => `<option ${o === val ? "selected" : ""}>${esc(o)}</option>`).join("")}</select>`;
        else if (tipo.startsWith("ref")) ctl = `<select name="${n}" data-grp="${n}" ${tipo === "ref" ? "required" : ""}><option value="">${esc(op[2] ?? "Elegir…")}</option>${refs[n].map(r => { const id = r[E[op[0]].id]; return `<option value="${id}" ${id == val ? "selected" : ""}>${esc(op[1](r))}</option>`; }).join("")}</select>`;
        else {
            const v = tipo === "date" && val ? String(val).slice(0, 10) : val;
            const opc = n === "enFechaDevolucion" ? "" : "required";
            ctl = `<input name="${n}" type="${tipo}" value="${esc(v)}" ${tipo === "number" ? 'step="any" min="0"' : ""} ${opc}>`;
        }
        return `<label ${g ? `data-g="${g}"` : ""}>${t}${ctl}</label>`;
    }).join("");
    f.innerHTML = `<h2>${esNuevo ? "Agregar" : "Editar"} ${e.label.toLowerCase()}</h2>${campos}<p class="error" id="err" role="alert"></p>
        <div class="acciones"><button type="button" class="btn sec" id="cancelar">Cancelar</button><button class="btn">Guardar</button></div>`;
    $("#cancelar").onclick = () => $("#dlg").close();
    f.onsubmit = async ev => {
        ev.preventDefault();
        const obj = fila ? { ...fila } : {};
        for (const [n, , tipo] of e.form) {
            const el = f.elements[n];
            if (!el) continue;
            if (el.closest("label").hidden) { obj[n] = null; continue; }
            obj[n] = tipo === "number" || tipo.startsWith("ref") ? (el.value === "" ? null : Number(el.value)) : (el.value || null);
        }
        try { await api.save(k, obj, esNuevo); $("#dlg").close(); mostrar(k); }
        catch (x) { $("#err").textContent = x.message || "No se pudo conectar con el servidor."; }
    };
    const sync = () => f.querySelectorAll("select[data-grp]").forEach(s => f.querySelectorAll(`[data-g="${s.name}"]`).forEach(l => {
        l.hidden = !!s.value; l.querySelector("input,select").required = !s.value;
    }));
    f.onchange = sync; sync();
    $("#dlg").showModal();
}

/* ---------- Arranque ---------- */
$("#nav").innerHTML = Object.entries(MENU).map(([k, t]) => `<button data-k="${k}">${t}</button>`).join("");
$("#nav").onclick = ev => { const b = ev.target.closest("button"); if (b) mostrar(b.dataset.k); };
fetch(API_BASE + "/api/estado").then(r => r.ok ? r.json() : null).then(j => {
    if (j && !j.db) {
        const d = $("#demo");
        d.textContent = "El servidor no puede conectarse a la base de datos: " + j.error;
        d.style.background = "var(--alerta)";
        d.style.color = "#fff";
        d.hidden = false;
    }
}).catch(() => { });
mostrar("inicio");
