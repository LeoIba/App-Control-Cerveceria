package BackEnd.controllers;

import java.util.List;

import BackEnd.DAO.movimientoDAO;
import BackEnd.models.movimiento;

public class movimientoController {
    private movimientoDAO movimientoDAO;

    public boolean altaMovimiento(movimiento mov) {
        if (mov.getBarrilesBCod() <= 0 || mov.getMovPuntoOrigen().isEmpty() || mov.getMovPuntoDestino().isEmpty()
                || mov.getMovResponsable().isEmpty()) {
            return false;
        }
        return movimientoDAO.registrarMovimientoPLSQL(mov);
    }

    public List<movimiento> listarTodos() {
        return movimientoDAO.getAll();
    }

    public movimiento busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return movimientoDAO.getPorID(id);
    }

    public boolean actualizar(movimiento mov) {
        if (mov == null || mov.getMovCod() <= 0)
            return false;
        if (mov.getBarrilesBCod() <= 0 || mov.getMovPuntoOrigen() == null || mov.getMovPuntoOrigen().isEmpty()) {
            return false;
        }
        return movimientoDAO.modificar(mov);
    }
}
