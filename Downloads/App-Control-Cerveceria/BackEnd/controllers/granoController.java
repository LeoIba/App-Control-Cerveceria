package BackEnd.controllers;

import BackEnd.DAO.granoDAO;
import BackEnd.models.grano;
import java.util.List;

public class granoController {
    private granoDAO dao = new granoDAO();

    public boolean altaGrano(grano x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<grano> listarTodos() {
        return dao.getAll();
    }

    public grano busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(grano x) {
        if (x == null || x.getgCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(grano x) {
        return x != null && (x.getgNombre() != null && !x.getgNombre().trim().isEmpty()) && (x.getgTipo() != null && !x.getgTipo().trim().isEmpty()) && x.getgPorcentaje() >= 0 && x.getgPeso() >= 0 && (x.getgPotencialExtraccion() != null && !x.getgPotencialExtraccion().trim().isEmpty()) && (x.getgColor() != null && !x.getgColor().trim().isEmpty()) && x.getgPorcentaje() <= 100;
    }
}
