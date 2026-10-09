package BackEnd.controllers;

import BackEnd.DBConnection;
import BackEnd.DAO.aditivoDAO;
import BackEnd.models.aditivo;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class aditivoController {

    private aditivoDAO aditivoDAO;

    public aditivoController() {
        this.aditivoDAO = new aditivoDAO();
    }

    public boolean altaAditivo(String nombre, String tipo, double cantidad) {
        if (nombre == null || nombre.trim().isEmpty() || tipo == null || tipo.trim().isEmpty()) {
            System.err.println("Error! No pueden haber datos vacíos");
            return false;
        }
        if (cantidad <= 0) {
            System.err.println("Error! Cantidad < 0.");
            return false;
        }
        aditivo nuevoAditivo = new aditivo(nombre, tipo, cantidad);
        try (Connection conn = DBConnection.getConnection()) {
            int idGenerado = aditivoDAO.insertGetID(nuevoAditivo, conn);
            System.out.println(idGenerado);
            return true;
        } catch (SQLException e) {
            System.err.println("Error al dar de alta el aditivo: " + e.getMessage());
            return false;
        }
    }

    public List<aditivo> getAll() {
        try {
            return aditivoDAO.getAll();
        } catch (SQLException e) {
            System.err.println("Error al consultar el inventario de aditivos: " + e.getMessage());
            return null;
        }
    }
}