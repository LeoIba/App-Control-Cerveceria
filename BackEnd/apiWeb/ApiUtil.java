package BackEnd.apiWeb;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

// Utilidades compartidas por los servlets: lectura y escritura de JSON, manejo de errores y conversión de campos
public class ApiUtil {
    // Las fechas viajan como yyyy-MM-dd (es lo que envía el input type="date" del front)
    public static final Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    public interface Accion {
        void ejecutar() throws Exception;
    }

    // Ejecuta la acción y traduce las excepciones a respuestas HTTP
    public static void manejar(HttpServletResponse resp, Accion accion) throws IOException {
        try {
            accion.ejecutar();
        } catch (JsonParseException | IllegalArgumentException e) {
            responder(resp, 400, Map.of("error", "Datos mal formados: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            responder(resp, 500, Map.of("error", "Error interno del servidor"));
        }
    }

    public static void responder(HttpServletResponse resp, int estado, Object cuerpo) throws IOException {
        resp.setStatus(estado);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(GSON.toJson(cuerpo));
    }

    public static void resultado(HttpServletResponse resp, boolean ok) throws IOException {
        if (ok)
            responder(resp, 200, Map.of("ok", true));
        else
            responder(resp, 400,
                    Map.of("error", "No se pudo guardar: revisá los datos (el detalle queda en el log del servidor)."));
    }

    // Para operaciones que devuelven el mensaje de error (null si salió bien)
    public static void resultado(HttpServletResponse resp, String error) throws IOException {
        if (error == null)
            responder(resp, 200, Map.of("ok", true));
        else
            responder(resp, 400, Map.of("error", error));
    }

    // GET /api/x devuelve todo; GET /api/x?id=5 devuelve uno solo
    public static <T> void listarOBuscar(HttpServletRequest req, HttpServletResponse resp, Supplier<List<T>> todos,
            Function<Integer, T> porId) throws IOException {
        String id = req.getParameter("id");
        if (id == null) {
            List<T> lista = todos.get();
            if (lista == null)
                responder(resp, 500, Map.of("error", "No se pudo consultar la base de datos"));
            else
                responder(resp, 200, lista);
            return;
        }
        T obj = porId.apply(Integer.valueOf(id));
        if (obj == null)
            responder(resp, 404, Map.of("error", "No encontrado"));
        else
            responder(resp, 200, obj);
    }

    public static void noSoportado(HttpServletResponse resp) throws IOException {
        responder(resp, 405, Map.of("error", "Operación no soportada"));
    }

    // ---- Lectura del cuerpo ----
    public static JsonObject leerJson(HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("UTF-8");
        JsonElement e = JsonParser.parseReader(req.getReader());
        if (e == null || !e.isJsonObject())
            throw new JsonParseException("se esperaba un objeto JSON");
        return e.getAsJsonObject();
    }

    public static <T> T leer(HttpServletRequest req, Class<T> clase) throws IOException {
        T obj = GSON.fromJson(leerJson(req), clase);
        if (obj == null)
            throw new JsonParseException("cuerpo vacío");
        return obj;
    }

    // ---- Lectura de campos sueltos (vacío o ausente = null) ----
    public static String str(JsonObject o, String k) {
        JsonElement e = o.get(k);
        if (e == null || e.isJsonNull() || e.getAsString().trim().isEmpty())
            return null;
        return e.getAsString().trim();
    }

    public static Double numObj(JsonObject o, String k) {
        String s = str(o, k);
        return s == null ? null : Double.valueOf(s);
    }

    public static double num(JsonObject o, String k) {
        Double d = numObj(o, k);
        return d == null ? 0 : d;
    }

    public static Integer entObj(JsonObject o, String k) {
        String s = str(o, k);
        return s == null ? null : Integer.valueOf(s);
    }

    public static int ent(JsonObject o, String k) {
        Integer i = entObj(o, k);
        return i == null ? 0 : i;
    }

    public static Date fecha(JsonObject o, String k) {
        String s = str(o, k);
        return s == null ? null : Date.valueOf(s.substring(0, Math.min(10, s.length())));
    }
}
