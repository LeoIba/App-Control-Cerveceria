package BackEnd.apiWeb;

import BackEnd.controllers.aditivoController;
import BackEnd.models.*;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/aditivos")
public class aditivoServlets extends HttpServlet {
    private aditivoController controller = new aditivoController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::getAll, controller::busquedaPorID));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> {
            aditivo a = ApiUtil.leer(req, aditivo.class);
            ApiUtil.resultado(resp, controller.altaAditivo(a.getNombreAditivo(), a.getTipoAditivo(), a.getCantidadAditivo()));
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.actualizar(ApiUtil.leer(req, aditivo.class))));
    }
}
