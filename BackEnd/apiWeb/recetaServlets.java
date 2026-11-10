package BackEnd.apiWeb;

import BackEnd.controllers.recetaController;
import BackEnd.models.aditivo;
import BackEnd.models.grano;
import BackEnd.models.levadura;
import BackEnd.models.lupulo;
import BackEnd.models.receta;
import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/recetas")
public class recetaServlets extends HttpServlet {
    private recetaController controller = new recetaController();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> ApiUtil.listarOBuscar(req, resp, controller::listarTodos, controller::busquedaPorID));
    }

    // El JSON es plano: los campos de la receta y de cada ingrediente (gCod/gNombre..., luCod/luVariedad..., etc.)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.manejar(resp, () -> {
            JsonObject o = ApiUtil.leerJson(req);
            ApiUtil.resultado(resp, controller.altaReceta(ApiUtil.GSON.fromJson(o, receta.class),
                    ApiUtil.GSON.fromJson(o, grano.class), ApiUtil.GSON.fromJson(o, lupulo.class),
                    ApiUtil.GSON.fromJson(o, levadura.class), ApiUtil.GSON.fromJson(o, aditivo.class)));
        });
    }

    // receta_alta es el único procedimiento de recetas: por ahora no hay modificación
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ApiUtil.noSoportado(resp);
    }
}
