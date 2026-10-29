package BackEnd.controllers;

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
}
