package BackEnd.controllers;

import BackEnd.DAO.barrilDAO;
import java.sql.Date;

public class barrilController {
    private barrilDAO dao = new barrilDAO();

    public boolean altaBarril(double capacidad, String conector, String estado, String ubicacion,
            int inventarioCod, int loteCod, Date fechaInicio) {
        if (capacidad <= 0 || loteCod <= 0 || fechaInicio == null) {
            return false;
        }
        return dao.registrarBarrilPLSQL(capacidad, conector, estado, ubicacion, inventarioCod, loteCod, fechaInicio);
    }
}
