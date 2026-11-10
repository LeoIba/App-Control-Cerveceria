package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.aditivo;
import BackEnd.models.grano;
import BackEnd.models.levadura;
import BackEnd.models.lupulo;
import BackEnd.models.receta;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class recetaDAO {

    // receta_alta crea la receta y asocia (o crea) grano, lúpulo, levadura y aditivo en una sola transacción.
    // Devuelve null si salió bien, o el mensaje de error de la base (códigos duplicados, ingredientes inexistentes, etc.)
    public String registrarRecetaPLSQL(receta r, grano g, lupulo lu, levadura le, aditivo ad) {
        String sql = "{call receta_alta(" + String.join(", ", Collections.nCopies(38, "?")) + ")}"; // 37 de entrada + 1 de salida

        try (Connection conn = DBConnection.getConnection(); CallableStatement c = conn.prepareCall(sql)) {
            c.setString(1, r.getrNombre());
            c.setString(2, r.getrCodigo());
            c.setInt(3, r.getrVersion());
            c.setDouble(4, r.getrBatchSize());
            c.setDouble(5, r.getrEficienciaSize());
            c.setString(6, r.getrAutor());
            c.setDate(7, r.getrFechaC());
            c.setInt(8, r.getEstiloPtCod());
            c.setInt(9, r.getHervidoHCod());
            c.setInt(10, r.getWhirpoolWCod());
            c.setInt(11, r.getFermentacionFCod());
            if (r.getDryHoppingFDryCod() != null && r.getDryHoppingFDryCod() > 0)
                c.setInt(12, r.getDryHoppingFDryCod());
            else
                c.setNull(12, Types.NUMERIC);
            c.setInt(13, r.getCarbonatacionCarCod());
            c.setInt(14, r.getMaceracionMCod());

            // Ingredientes: código > 0 = existente; si no, se crean con los datos enviados
            codigo(c, 15, g.getgCod());
            c.setString(16, g.getgNombre());
            c.setString(17, g.getgTipo());
            c.setDouble(18, g.getgPorcentaje());
            c.setDouble(19, g.getgPeso());
            c.setString(20, g.getgPotencialExtraccion());
            c.setString(21, g.getgColor());

            codigo(c, 22, lu.getLuCod());
            c.setString(23, lu.getLuVariedad());
            c.setInt(24, lu.getLuAnioCosecha());
            c.setString(25, lu.getLuFormato());
            c.setDouble(26, lu.getLuPorcentajeAlfaAcido());
            c.setDouble(27, lu.getLuCantidad());
            c.setString(28, lu.getLuProposito());

            codigo(c, 29, le.getLeCod());
            c.setString(30, le.getLeCepa());
            c.setString(31, le.getLeFormatoLev());
            c.setDouble(32, le.getLeCantidadLev());
            c.setDouble(33, le.getLeTasaInoculacion());

            codigo(c, 34, ad.getaCod());
            c.setString(35, ad.getNombreAditivo());
            c.setString(36, ad.getTipoAditivo());
            c.setDouble(37, ad.getCantidadAditivo());

            c.registerOutParameter(38, Types.NUMERIC);
            c.execute();
            return null;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            // "ORA-20015: texto \n ORA-06512: ..." -> "texto"
            Matcher m = Pattern.compile("ORA-\\d+: ([^\\n]*)").matcher(String.valueOf(e.getMessage()));
            return m.find() ? m.group(1).trim() : "No se pudo guardar la receta: " + e.getMessage();
        }
    }

    private void codigo(CallableStatement c, int pos, int cod) throws SQLException {
        if (cod > 0)
            c.setInt(pos, cod);
        else
            c.setNull(pos, Types.NUMERIC);
    }

    private receta leer(ResultSet rs) throws SQLException {
        int dry = rs.getInt("Dry_Hopping_f_dry_cod");
        Integer dryHopping = rs.wasNull() ? null : dry;
        return new receta(rs.getInt("r_cod"), rs.getString("r_nombre"), rs.getString("r_codigo"), rs.getInt("r_version"),
                rs.getDouble("r_batch_size"), rs.getDouble("r_eficiencia_size"), rs.getString("r_autor"),
                rs.getDate("r_fecha_c"), rs.getInt("Estilo_pt_cod"), rs.getInt("Hervido_h_cod"),
                rs.getInt("Whirpool_w_cod"), rs.getInt("Fermentacion_f_cod"), dryHopping,
                rs.getInt("Carbonatacion_car_cod"), rs.getInt("Maceracion_m_cod"));
    }

    public List<receta> getAll() {
        List<receta> lista = new ArrayList<>();
        String sql = "SELECT * FROM Recetas";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(leer(rs));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public receta getPorID(int id) {
        String sql = "SELECT * FROM Recetas WHERE r_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return leer(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}
