package BackEnd.controllers;

import BackEnd.DAO.dryHoppingDAO;
import BackEnd.models.dryHopping;
import java.util.List;

public class dryHoppingController {
    private dryHoppingDAO dao = new dryHoppingDAO();

    public boolean altaDryHopping(dryHopping x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<dryHopping> listarTodos() {
        return dao.getAll();
    }

    public dryHopping busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(dryHopping x) {
        if (x == null || x.getfDryCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(dryHopping x) {
        return x != null && x.getTiempoContacto() >= 0 && x.getTemperatura() >= 0;
    }
}
