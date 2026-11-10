package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.aditivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class aditivoDAO {

    public int insertGetID(aditivo aditivo, Connection conn) throws SQLException {
        String sql = "INSERT INTO Aditivos (ad_nombre_aditivo, ad_tipo_aditivo, ad_cantidad_aditivo) VALUES (?, ?, ?)";
        String[] columnasGeneradas = { "a_cod" };

        try (PreparedStatement pstmt = conn.prepareStatement(sql, columnasGeneradas)) {
            pstmt.setString(1, aditivo.getNombreAditivo());
            pstmt.setString(2, aditivo.getTipoAditivo());
            pstmt.setDouble(3, aditivo.getCantidadAditivo());
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo obtener el a_cod generado.");
    }

    public List<aditivo> getAll() throws SQLException {
        List<aditivo> lista = new ArrayList<>();
        String sql = "SELECT a_cod, ad_nombre_aditivo, ad_tipo_aditivo, ad_cantidad_aditivo FROM Aditivos";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new aditivo(
                        rs.getInt("a_cod"),
                        rs.getString("ad_nombre_aditivo"),
                        rs.getString("ad_tipo_aditivo"),
                        rs.getDouble("ad_cantidad_aditivo")));
            }
        }
        return lista;
    }

    public aditivo getPorID(int id) throws SQLException {
        String sql = "SELECT a_cod, ad_nombre_aditivo, ad_tipo_aditivo, ad_cantidad_aditivo FROM Aditivos WHERE a_cod = ?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new aditivo(rs.getInt("a_cod"), rs.getString("ad_nombre_aditivo"),
                            rs.getString("ad_tipo_aditivo"), rs.getDouble("ad_cantidad_aditivo"));
                }
            }
        }
        return null;
    }

    public boolean modificar(aditivo a) throws SQLException {
        String sql = "UPDATE Aditivos SET ad_nombre_aditivo=?, ad_tipo_aditivo=?, ad_cantidad_aditivo=? WHERE a_cod=?";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, a.getNombreAditivo());
            pstmt.setString(2, a.getTipoAditivo());
            pstmt.setDouble(3, a.getCantidadAditivo());
            pstmt.setInt(4, a.getaCod());
            return pstmt.executeUpdate() > 0;
        }
    }
}
