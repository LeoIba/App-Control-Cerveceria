package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.dryHopping;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class dryHoppingDAO {
    public boolean insertar(dryHopping x) {
        String sql = "INSERT INTO Dry_Hopping (tiempo_contacto, temperatura) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getTiempoContacto());
            consulta.setDouble(2, x.getTemperatura());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<dryHopping> getAll() {
        List<dryHopping> lista = new ArrayList<>();
        String sql = "SELECT * FROM Dry_Hopping";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new dryHopping(rs.getInt("f_dry_cod"), rs.getDouble("tiempo_contacto"), rs.getDouble("temperatura")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public dryHopping getPorID(int id) {
        String sql = "SELECT * FROM Dry_Hopping WHERE f_dry_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new dryHopping(rs.getInt("f_dry_cod"), rs.getDouble("tiempo_contacto"), rs.getDouble("temperatura"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(dryHopping x) {
        String sql = "UPDATE Dry_Hopping SET tiempo_contacto=?, temperatura=? WHERE f_dry_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getTiempoContacto());
            consulta.setDouble(2, x.getTemperatura());
            consulta.setInt(3, x.getfDryCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
