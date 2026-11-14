package BackEnd.controllers;

import BackEnd.DAO.lupuloDAO;
import BackEnd.models.lupulo;
import java.util.List;

public class lupuloController {
    private lupuloDAO dao = new lupuloDAO();

    public boolean altaLupulo(lupulo x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<lupulo> listarTodos() {
        return dao.getAll();
    }

    public lupulo busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(lupulo x) {
        if (x == null || x.getLuCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(lupulo x) {
        return x != null && (x.getLuVariedad() != null && !x.getLuVariedad().trim().isEmpty()) && x.getLuAnioCosecha() >= 0 && (x.getLuFormato() != null && !x.getLuFormato().trim().isEmpty()) && x.getLuPorcentajeAlfaAcido() >= 0 && x.getLuCantidad() >= 0 && (x.getLuProposito() != null && !x.getLuProposito().trim().isEmpty()) && java.util.Arrays.asList("Amargor", "Sabor", "Aroma").contains(x.getLuProposito()) && (x.getLuAnioCosecha() >= 1900 && x.getLuAnioCosecha() <= 9999);
    }
}
