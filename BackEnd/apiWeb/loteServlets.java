package BackEnd.apiWeb;

import BackEnd.controllers.loteController;
import BackEnd.models.*;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/lotes")
public class loteServlets extends HttpServlet {
    private loteController controller = new loteController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::listarTodos, controller::busquedaPorID));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> {
            JsonObject o = ApiUtil.leerJson(req);
            // Las etapas (fechas y cantidades) son opcionales; barrilesBCod vacío crea un barril nuevo con los datos b*
            ApiUtil.resultado(resp, controller.altaLote(ApiUtil.numObj(o, "lotVolumenTotal"), ApiUtil.fecha(o, "lotFechaVecimiento"),
                    ApiUtil.fecha(o, "fechaMaceracion"), ApiUtil.numObj(o, "cantidadMaceracion"),
                    ApiUtil.fecha(o, "fechaHervido"), ApiUtil.numObj(o, "cantidadHervido"),
                    ApiUtil.fecha(o, "fechaWhirpool"), ApiUtil.numObj(o, "cantidadWhirpool"),
                    ApiUtil.fecha(o, "fechaFermentacion"), ApiUtil.numObj(o, "cantidadFermentacion"),
                    ApiUtil.fecha(o, "fechaDryhopping"), ApiUtil.numObj(o, "cantidadDryhopping"),
                    ApiUtil.fecha(o, "fechaCarbonatacion"), ApiUtil.numObj(o, "cantidadCarbonatacion"),
                    ApiUtil.fecha(o, "lotFechaFinPreparado"), ApiUtil.ent(o, "recetasRCod"), ApiUtil.ent(o, "barrilesBCod"),
                    ApiUtil.fecha(o, "fechaInicioComp"), ApiUtil.num(o, "bCapacidad"), ApiUtil.str(o, "bTipoConector"),
                    ApiUtil.str(o, "bEstadoOperativo"), ApiUtil.str(o, "bUbicacionActual"), ApiUtil.ent(o, "inventarioICod")));
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.actualizar(ApiUtil.leer(req, lote.class))));
    }
}
