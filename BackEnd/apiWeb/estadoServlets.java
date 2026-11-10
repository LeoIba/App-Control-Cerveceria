package BackEnd.apiWeb;

import BackEnd.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.Map;

// Diagnóstico: el front lo consulta al abrir para avisar si el servidor no llega a la base de datos
@WebServlet("/api/estado")
public class estadoServlets extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Map<String, Object> estado = new LinkedHashMap<>();
        try (Connection conn = DBConnection.getConnection()) {
            estado.put("db", true);
        } catch (Exception e) {
            estado.put("db", false);
            estado.put("error", String.valueOf(e.getMessage()));
        }
        ApiUtil.responder(resp, 200, estado);
    }
}
