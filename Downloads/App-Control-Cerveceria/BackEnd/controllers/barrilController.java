package BackEnd.controllers;

import BackEnd.DAO.barrilDAO;
import java.sql.Date;
import java.util.List;
import BackEnd.models.barril;

public class barrilController {
    private barrilDAO dao = new barrilDAO();

    public boolean altaBarril(double capacidad, String conector, String estado, String ubicacion,
            int inventarioCod, int loteCod, Date fechaInicio) {
        if (capacidad <= 0 || loteCod <= 0 || fechaInicio == null) {
            return false;
        }
        return dao.registrarBarrilPLSQL(capacidad, conector, estado, ubicacion, inventarioCod, loteCod, fechaInicio);
    }

    public List<barril> listarTodos() {
        return dao.getAll();
    }

    public barril busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(barril barril) {
        if (barril == null || barril.getbCod() <= 0)
            return false;
        if (barril.getbCapacidad() <= 0 || barril.getInventarioICod() <= 0) {
            return false;
        }
        return dao.modificar(barril);
    }
}
