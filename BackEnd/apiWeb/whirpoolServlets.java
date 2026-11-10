package BackEnd.apiWeb;

import BackEnd.controllers.whirpoolController;
import BackEnd.models.whirpool;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/whirpools")
public class whirpoolServlets extends HttpServlet {
    private whirpoolController controller = new whirpoolController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::listarTodos, controller::busquedaPorID));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.altaWhirpool(ApiUtil.leer(req, whirpool.class))));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.actualizar(ApiUtil.leer(req, whirpool.class))));
    }
}
