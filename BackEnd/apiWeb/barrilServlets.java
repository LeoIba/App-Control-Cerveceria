package BackEnd.apiWeb;

import BackEnd.controllers.barrilController;
import BackEnd.models.*;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/barriles")
public class barrilServlets extends HttpServlet {
    private barrilController controller = new barrilController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::listarTodos, controller::busquedaPorID));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> {
            JsonObject o = ApiUtil.leerJson(req);
            ApiUtil.resultado(resp, controller.altaBarril(ApiUtil.num(o, "bCapacidad"), ApiUtil.str(o, "bTipoConector"),
                    ApiUtil.str(o, "bEstadoOperativo"), ApiUtil.str(o, "bUbicacionActual"), ApiUtil.ent(o, "inventarioICod"),
                    ApiUtil.ent(o, "loteCod"), ApiUtil.fecha(o, "fechaInicio")));
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.actualizar(ApiUtil.leer(req, barril.class))));
    }
}
