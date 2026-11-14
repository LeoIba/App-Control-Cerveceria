package BackEnd.controllers;

import BackEnd.DAO.fermentacionDAO;
import BackEnd.models.fermentacion;
import java.util.List;

public class fermentacionController {
    private fermentacionDAO dao = new fermentacionDAO();

    public boolean altaFermentacion(fermentacion x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<fermentacion> listarTodos() {
        return dao.getAll();
    }

    public fermentacion busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    private boolean esValido(fermentacion x) {
        return x != null && true;
    }
}
