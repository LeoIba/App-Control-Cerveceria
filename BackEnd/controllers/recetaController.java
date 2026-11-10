package BackEnd.controllers;

import BackEnd.DAO.recetaDAO;
import BackEnd.models.aditivo;
import BackEnd.models.grano;
import BackEnd.models.levadura;
import BackEnd.models.lupulo;
import BackEnd.models.receta;
import java.util.Arrays;
import java.util.List;

public class recetaController {
    private recetaDAO dao = new recetaDAO();

    private boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    // Devuelve null si la receta se dio de alta, o el motivo del rechazo.
    // Cada ingrediente se elige existente (código > 0) o se envía completo para crearlo.
    public String altaReceta(receta r, grano g, lupulo lu, levadura le, aditivo ad) {
        if (r == null || g == null || lu == null || le == null || ad == null) {
            return "Faltan datos de la receta.";
        }
        if (vacio(r.getrNombre()) || vacio(r.getrCodigo()) || vacio(r.getrAutor())
                || r.getrFechaC() == null) {
            return "Nombre, código, autor y fecha de la receta son obligatorios.";
        }
        if (r.getrVersion() <= 0 || r.getrBatchSize() <= 0 || r.getrEficienciaSize() <= 0) {
            return "Versión, tamaño de lote y eficiencia deben ser mayores que cero.";
        }
        if (r.getEstiloPtCod() <= 0 || r.getMaceracionMCod() <= 0 || r.getHervidoHCod() <= 0
                || r.getWhirpoolWCod() <= 0 || r.getFermentacionFCod() <= 0
                || r.getCarbonatacionCarCod() <= 0) {
            return "Elegí estilo, maceración, hervido, whirlpool, fermentación y carbonatación.";
        }
        if (g.getgCod() <= 0 && (vacio(g.getgNombre()) || vacio(g.getgTipo())
                || vacio(g.getgPotencialExtraccion()) || vacio(g.getgColor())
                || g.getgPorcentaje() <= 0 || g.getgPeso() <= 0)) {
            return "Completá los datos del grano nuevo o elegí uno existente.";
        }
        if (lu.getLuCod() <= 0 && (vacio(lu.getLuVariedad()) || vacio(lu.getLuFormato())
                || lu.getLuAnioCosecha() <= 0 || lu.getLuPorcentajeAlfaAcido() <= 0
                || lu.getLuCantidad() <= 0
                || !Arrays.asList("Amargor", "Sabor", "Aroma").contains(lu.getLuProposito()))) {
            return "Completá los datos del lúpulo nuevo o elegí uno existente.";
        }
        if (le.getLeCod() <= 0 && (vacio(le.getLeCepa()) || le.getLeCantidadLev() <= 0
                || le.getLeTasaInoculacion() <= 0
                || !Arrays.asList("Seca", "Líquida").contains(le.getLeFormatoLev()))) {
            return "Completá los datos de la levadura nueva o elegí una existente.";
        }
        if (ad.getaCod() <= 0 && (vacio(ad.getNombreAditivo()) || vacio(ad.getTipoAditivo())
                || ad.getCantidadAditivo() <= 0)) {
            return "Completá los datos del aditivo nuevo o elegí uno existente.";
        }
        return dao.registrarRecetaPLSQL(r, g, lu, le, ad);
    }

    public List<receta> listarTodos() {
        return dao.getAll();
    }

    public receta busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }
}
