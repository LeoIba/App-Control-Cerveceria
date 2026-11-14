package BackEnd.apiWeb;

import BackEnd.controllers.entregaController;
import BackEnd.models.*;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/entrega")
public class entregaServlets extends HttpServlet {
    private entregaController controller = new entregaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::listarTodos, controller::busquedaPorID));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> {
            JsonObject o = ApiUtil.leerJson(req);
            ApiUtil.resultado(resp, controller.altaEntrega(ApiUtil.str(o, "enRemitoFactura"), ApiUtil.fecha(o, "enFechaEntrega"),
                    ApiUtil.num(o, "enTiempoPermanencia"), ApiUtil.fecha(o, "enFechaDevolucion"),
                    ApiUtil.ent(o, "barrilesBCod"), ApiUtil.ent(o, "clientesClCod")));
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.actualizar(ApiUtil.leer(req, entrega.class))));
    }
}
