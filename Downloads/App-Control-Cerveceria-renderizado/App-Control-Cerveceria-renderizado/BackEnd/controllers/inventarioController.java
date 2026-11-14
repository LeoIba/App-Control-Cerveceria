package BackEnd.controllers;

import BackEnd.DAO.inventarioDAO;
import BackEnd.models.inventario;
import java.util.List;
import java.util.Arrays;

public class inventarioController {
    private inventarioDAO dao = new inventarioDAO();

    private final List<String> EstadosPosibles = Arrays.asList(
            "Fermentación/Maduración",
            "Para Envasar",
            "Envasados");

    public boolean altaInventario(Double litrosTotales, String estadoEtapa, int barrilCod) {
        if (litrosTotales == null || litrosTotales < 0) {
            return false;
        }
        if (!EstadosPosibles.contains(estadoEtapa)) {
            return false;
        }
        /*
         * if (barrilCod <= 0) {
         * return false;
         * }
         */
        return dao.registrarInventarioPLSQL(litrosTotales, estadoEtapa, barrilCod);
    }

    public List<inventario> listarTodos() {
        return dao.getAll();
    }

    public inventario busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getporID(id);
    }

    public boolean actualizar(inventario inventario) {
        if (inventario == null || inventario.getiCod() <= 0)
            return false;
        if (!EstadosPosibles.contains(inventario.getiEstadoEtapa())) {
            return false;
        }
        return dao.modificar(inventario);
    }
}
