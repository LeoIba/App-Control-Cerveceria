package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.carbonatacion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class carbonatacionDAO {
    public boolean insertar(carbonatacion x) {
        String sql = "INSERT INTO Carbonatacion (car_vol_obj_CO2, car_metodo_empleado) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getCarVolObjCO2());
            consulta.setString(2, x.getCarMetodoEmpleado());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<carbonatacion> getAll() {
        List<carbonatacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Carbonatacion";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new carbonatacion(rs.getInt("car_cod"), rs.getDouble("car_vol_obj_CO2"), rs.getString("car_metodo_empleado")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public carbonatacion getPorID(int id) {
        String sql = "SELECT * FROM Carbonatacion WHERE car_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new carbonatacion(rs.getInt("car_cod"), rs.getDouble("car_vol_obj_CO2"), rs.getString("car_metodo_empleado"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(carbonatacion x) {
        String sql = "UPDATE Carbonatacion SET car_vol_obj_CO2=?, car_metodo_empleado=? WHERE car_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setDouble(1, x.getCarVolObjCO2());
            consulta.setString(2, x.getCarMetodoEmpleado());
            consulta.setInt(3, x.getCarCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
