package BackEnd.controllers;

import BackEnd.DAO.maceracionDAO;
import BackEnd.models.maceracion;
import java.util.List;

public class maceracionController {
    private maceracionDAO dao = new maceracionDAO();

    public boolean altaMaceracion(maceracion x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<maceracion> listarTodos() {
        return dao.getAll();
    }

    public maceracion busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(maceracion x) {
        if (x == null || x.getmCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(maceracion x) {
        return x != null && x.getmRelacionAguaGrano() >= 0 && x.getmRelacionAguaGrano() > 0;
    }
}
