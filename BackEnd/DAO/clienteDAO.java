package BackEnd.DAO;

import BackEnd.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import BackEnd.models.cliente;

public class clienteDAO {

    public boolean registrarClientePLSQL(String nombre, String direccion, String telefono, Integer barrilCod,
            Date fechaEntrega, String remito, Double tiempoPerm, Date fechaDev) {
        String sql = "{call cliente_alta(?, ?, ?, ?, ?, ?, ?, ?)}"; // 8 parámetros

        try (Connection conn = DBConnection.getConnection();
                CallableStatement consulta = conn.prepareCall(sql)) {

            consulta.setString(1, nombre);
            consulta.setString(2, direccion);
            consulta.setString(3, telefono);

            if (barrilCod != null && barrilCod > 0)
                consulta.setInt(4, barrilCod);
            else
                consulta.setNull(4, Types.NUMERIC);
            if (fechaEntrega != null)
                consulta.setDate(5, fechaEntrega);
            else
                consulta.setNull(5, Types.DATE);
            if (remito != null && !remito.isEmpty())
                consulta.setString(6, remito);
            else
                consulta.setNull(6, Types.VARCHAR);
            if (tiempoPerm != null)
                consulta.setDouble(7, tiempoPerm);
            else
                consulta.setNull(7, Types.NUMERIC);
            if (fechaDev != null)
                consulta.setDate(8, fechaDev);
            else
                consulta.setNull(8, Types.DATE);

            consulta.execute();
            return true;

        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<cliente> getAll() {
        List<cliente> lista = new ArrayList<>();
        String sql = "SELECT cl_cod, cl_nombre, cl_direccion, cl_telefono FROM Clientes";

        try (Connection conn = DBConnection.getConnection();
                Statement consulta = conn.createStatement();
                ResultSet rta = consulta.executeQuery(sql)) {

            while (rta.next()) {
                lista.add(new cliente(rta.getInt("cl_cod"), rta.getString("cl_nombre"), rta.getString("cl_direccion"),
                        rta.getString("cl_telefono")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public cliente getPorID(int id) {
        String sql = "SELECT cl_cod, cl_nombre, cl_direccion, cl_telefono FROM Clientes WHERE cl_cod = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rta = consulta.executeQuery()) {
                if (rta.next()) {
                    return new cliente(rta.getInt("cl_cod"), rta.getString("cl_nombre"), rta.getString("cl_direccion"),
                            rta.getString("cl_telefono"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
        }
        return null;
    }

    public boolean modificar(cliente cliente) {
        String sql = "UPDATE Clientes SET cl_nombre = ?, cl_direccion = ?, cl_telefono = ? WHERE cl_cod = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setString(1, cliente.getClNombre());
            consulta.setString(2, cliente.getClDireccion());
            consulta.setString(3, cliente.getClTelefono());
            consulta.setInt(4, cliente.getClCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
