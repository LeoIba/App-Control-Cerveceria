package BackEnd.controllers;

import BackEnd.DAO.hervidoDAO;
import BackEnd.models.hervido;
import java.util.List;

public class hervidoController {
    private hervidoDAO dao = new hervidoDAO();

    public boolean altaHervido(hervido x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<hervido> listarTodos() {
        return dao.getAll();
    }

    public hervido busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(hervido x) {
        if (x == null || x.gethCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(hervido x) {
        return x != null && x.gethTiempoEbullicion() >= 0 && x.gethTiempoEbullicion() > 0;
    }
}
