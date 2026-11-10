package BackEnd.controllers;

import BackEnd.DAO.levaduraDAO;
import BackEnd.models.levadura;
import java.util.List;

public class levaduraController {
    private levaduraDAO dao = new levaduraDAO();

    public boolean altaLevadura(levadura x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<levadura> listarTodos() {
        return dao.getAll();
    }

    public levadura busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(levadura x) {
        if (x == null || x.getLeCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(levadura x) {
        return x != null && (x.getLeCepa() != null && !x.getLeCepa().trim().isEmpty()) && (x.getLeFormatoLev() != null && !x.getLeFormatoLev().trim().isEmpty()) && x.getLeCantidadLev() >= 0 && x.getLeTasaInoculacion() >= 0 && java.util.Arrays.asList("Seca", "Líquida").contains(x.getLeFormatoLev());
    }
}
