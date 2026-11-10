package BackEnd.controllers;

import BackEnd.DAO.whirpoolDAO;
import BackEnd.models.whirpool;
import java.util.List;

public class whirpoolController {
    private whirpoolDAO dao = new whirpoolDAO();

    public boolean altaWhirpool(whirpool x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<whirpool> listarTodos() {
        return dao.getAll();
    }

    public whirpool busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(whirpool x) {
        if (x == null || x.getwCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(whirpool x) {
        return x != null && x.getwTiempoInicio() >= 0 && x.getwTiempoCentrifugado() >= 0 && x.getwTiempoReposo() >= 0;
    }
}
