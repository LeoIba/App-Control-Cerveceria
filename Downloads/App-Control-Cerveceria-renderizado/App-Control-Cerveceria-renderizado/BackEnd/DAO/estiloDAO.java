package BackEnd.DAO;

import BackEnd.DBConnection;
import BackEnd.models.estilo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class estiloDAO {
    public boolean insertar(estilo x) {
        String sql = "INSERT INTO Estilo (pt_estilo_nombre, pt_densidad_ini_minima, pt_densidad_ini_maxima, pt_densidad_fin_minima, pt_densidad_fin_maxima, pt_alcohol_minimo, pt_alcohol_maximo, pt_amargor_minimo, pt_amargor_maximo, pt_color_minimo, pt_color_maximo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getPtEstiloNombre());
            consulta.setDouble(2, x.getPtDensidadIniMinima());
            consulta.setDouble(3, x.getPtDensidadIniMaxima());
            consulta.setDouble(4, x.getPtDensidadFinMinima());
            consulta.setDouble(5, x.getPtDensidadFinMaxima());
            consulta.setDouble(6, x.getPtAlcoholMinimo());
            consulta.setDouble(7, x.getPtAlcoholMaximo());
            consulta.setDouble(8, x.getPtAmargorMinimo());
            consulta.setDouble(9, x.getPtAmargorMaximo());
            consulta.setDouble(10, x.getPtColorMinimo());
            consulta.setDouble(11, x.getPtColorMaximo());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<estilo> getAll() {
        List<estilo> lista = new ArrayList<>();
        String sql = "SELECT * FROM Estilo";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rs = consulta.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new estilo(rs.getInt("pt_cod"), rs.getString("pt_estilo_nombre"), rs.getDouble("pt_densidad_ini_minima"), rs.getDouble("pt_densidad_ini_maxima"), rs.getDouble("pt_densidad_fin_minima"), rs.getDouble("pt_densidad_fin_maxima"), rs.getDouble("pt_alcohol_minimo"), rs.getDouble("pt_alcohol_maximo"), rs.getDouble("pt_amargor_minimo"), rs.getDouble("pt_amargor_maximo"), rs.getDouble("pt_color_minimo"), rs.getDouble("pt_color_maximo")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public estilo getPorID(int id) {
        String sql = "SELECT * FROM Estilo WHERE pt_cod = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rs = consulta.executeQuery()) {
                if (rs.next()) {
                    return new estilo(rs.getInt("pt_cod"), rs.getString("pt_estilo_nombre"), rs.getDouble("pt_densidad_ini_minima"), rs.getDouble("pt_densidad_ini_maxima"), rs.getDouble("pt_densidad_fin_minima"), rs.getDouble("pt_densidad_fin_maxima"), rs.getDouble("pt_alcohol_minimo"), rs.getDouble("pt_alcohol_maximo"), rs.getDouble("pt_amargor_minimo"), rs.getDouble("pt_amargor_maximo"), rs.getDouble("pt_color_minimo"), rs.getDouble("pt_color_maximo"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(estilo x) {
        String sql = "UPDATE Estilo SET pt_estilo_nombre=?, pt_densidad_ini_minima=?, pt_densidad_ini_maxima=?, pt_densidad_fin_minima=?, pt_densidad_fin_maxima=?, pt_alcohol_minimo=?, pt_alcohol_maximo=?, pt_amargor_minimo=?, pt_amargor_maximo=?, pt_color_minimo=?, pt_color_maximo=? WHERE pt_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, x.getPtEstiloNombre());
            consulta.setDouble(2, x.getPtDensidadIniMinima());
            consulta.setDouble(3, x.getPtDensidadIniMaxima());
            consulta.setDouble(4, x.getPtDensidadFinMinima());
            consulta.setDouble(5, x.getPtDensidadFinMaxima());
            consulta.setDouble(6, x.getPtAlcoholMinimo());
            consulta.setDouble(7, x.getPtAlcoholMaximo());
            consulta.setDouble(8, x.getPtAmargorMinimo());
            consulta.setDouble(9, x.getPtAmargorMaximo());
            consulta.setDouble(10, x.getPtColorMinimo());
            consulta.setDouble(11, x.getPtColorMaximo());
            consulta.setInt(12, x.getPtCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
