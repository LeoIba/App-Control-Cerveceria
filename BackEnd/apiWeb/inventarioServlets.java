package BackEnd.apiWeb;

import BackEnd.controllers.inventarioController;
import BackEnd.models.*;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/inventario")
public class inventarioServlets extends HttpServlet {
    private inventarioController controller = new inventarioController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::listarTodos, controller::busquedaPorID));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> {
            JsonObject o = ApiUtil.leerJson(req);
            // inventario_alta asocia el nuevo inventario a un barril existente
            ApiUtil.resultado(resp, controller.altaInventario(ApiUtil.numObj(o, "iLitrosTotales"),
                    ApiUtil.str(o, "iEstadoEtapa"), ApiUtil.ent(o, "barrilesBCod")));
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.actualizar(ApiUtil.leer(req, inventario.class))));
    }
}
