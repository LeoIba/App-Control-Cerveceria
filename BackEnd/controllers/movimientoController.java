package BackEnd.controllers;

import java.util.List;

import BackEnd.DAO.movimientoDAO;
import BackEnd.models.movimiento;

public class movimientoController {
    private movimientoDAO movimientoDAO = new movimientoDAO();

    private boolean vacio(String s) {
        return s == null || s.trim().isEmpty();
    }

    public boolean altaMovimiento(movimiento mov) {
        if (mov == null || mov.getBarrilesBCod() <= 0 || vacio(mov.getMovPuntoOrigen())
                || vacio(mov.getMovPuntoDestino()) || vacio(mov.getMovResponsable())
                || mov.getMovFecha() == null || mov.getMovHora() < 0) {
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
        if (mov.getBarrilesBCod() <= 0 || vacio(mov.getMovPuntoOrigen())
                || vacio(mov.getMovPuntoDestino()) || vacio(mov.getMovResponsable())
                || mov.getMovFecha() == null) {
            return false;
        }
        return movimientoDAO.modificar(mov);
    }
}
