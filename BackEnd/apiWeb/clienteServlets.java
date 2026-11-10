package BackEnd.apiWeb;

import BackEnd.controllers.clienteController;
import BackEnd.models.*;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/clientes")
public class clienteServlets extends HttpServlet {
    private clienteController controller = new clienteController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::listarTodos, controller::busquedaPorID));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> {
            JsonObject o = ApiUtil.leerJson(req);
            // Los datos de la primera entrega son opcionales (ver cliente_alta)
            ApiUtil.resultado(resp, controller.altaCliente(ApiUtil.str(o, "clNombre"), ApiUtil.str(o, "clDireccion"),
                    ApiUtil.str(o, "clTelefono"), ApiUtil.entObj(o, "barrilesBCod"), ApiUtil.fecha(o, "enFechaEntrega"),
                    ApiUtil.str(o, "enRemitoFactura"), ApiUtil.numObj(o, "enTiempoPermanencia"),
                    ApiUtil.fecha(o, "enFechaDevolucion")));
        });
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.resultado(resp, controller.actualizar(ApiUtil.leer(req, cliente.class))));
    }
}
