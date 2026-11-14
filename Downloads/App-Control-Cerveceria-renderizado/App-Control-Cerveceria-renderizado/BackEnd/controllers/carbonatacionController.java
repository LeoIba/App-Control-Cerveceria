package BackEnd.controllers;

import BackEnd.DAO.carbonatacionDAO;
import BackEnd.models.carbonatacion;
import java.util.List;

public class carbonatacionController {
    private carbonatacionDAO dao = new carbonatacionDAO();

    public boolean altaCarbonatacion(carbonatacion x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<carbonatacion> listarTodos() {
        return dao.getAll();
    }

    public carbonatacion busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(carbonatacion x) {
        if (x == null || x.getCarCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(carbonatacion x) {
        return x != null && x.getCarVolObjCO2() >= 0 && (x.getCarMetodoEmpleado() != null && !x.getCarMetodoEmpleado().trim().isEmpty());
    }
}
