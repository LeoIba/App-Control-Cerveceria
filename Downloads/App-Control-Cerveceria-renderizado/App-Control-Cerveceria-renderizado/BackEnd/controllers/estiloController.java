package BackEnd.controllers;

import BackEnd.DAO.estiloDAO;
import BackEnd.models.estilo;
import java.util.List;

public class estiloController {
    private estiloDAO dao = new estiloDAO();

    public boolean altaEstilo(estilo x) {
        if (!esValido(x))
            return false;
        return dao.insertar(x);
    }

    public List<estilo> listarTodos() {
        return dao.getAll();
    }

    public estilo busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(estilo x) {
        if (x == null || x.getPtCod() <= 0 || !esValido(x))
            return false;
        return dao.modificar(x);
    }

    private boolean esValido(estilo x) {
        return x != null && (x.getPtEstiloNombre() != null && !x.getPtEstiloNombre().trim().isEmpty()) && x.getPtDensidadIniMinima() >= 0 && x.getPtDensidadIniMaxima() >= 0 && x.getPtDensidadFinMinima() >= 0 && x.getPtDensidadFinMaxima() >= 0 && x.getPtAlcoholMinimo() >= 0 && x.getPtAlcoholMaximo() >= 0 && x.getPtAmargorMinimo() >= 0 && x.getPtAmargorMaximo() >= 0 && x.getPtColorMinimo() >= 0 && x.getPtColorMaximo() >= 0 && x.getPtDensidadIniMinima() <= x.getPtDensidadIniMaxima() && x.getPtDensidadFinMinima() <= x.getPtDensidadFinMaxima() && x.getPtAlcoholMinimo() <= x.getPtAlcoholMaximo() && x.getPtAmargorMinimo() <= x.getPtAmargorMaximo() && x.getPtColorMinimo() <= x.getPtColorMaximo();
    }
}
