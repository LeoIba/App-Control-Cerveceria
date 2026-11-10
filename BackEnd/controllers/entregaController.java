package BackEnd.controllers;

import BackEnd.DAO.entregaDAO;
import BackEnd.models.entrega;
import java.sql.Date;
import java.util.List;

public class entregaController {
    private entregaDAO dao = new entregaDAO();

    public boolean altaEntrega(String remito, Date fEntrega, double tiempoPerm, Date fDevolucion, int barrilCod,
            int clienteCod) {
        if (remito == null) {
            return false;
        }
        if (fEntrega == null) {
            return false;
        }
        if (tiempoPerm <= 0) {
            return false;
        }
        if (barrilCod <= 0 || clienteCod <= 0) {
            return false;
        }
        return dao.registrarEntregaPLSQL(remito, fEntrega, tiempoPerm, fDevolucion, barrilCod, clienteCod);
    }

    public List<entrega> listarTodos() {
        return dao.getAll();
    }

    public entrega busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(entrega en) {
        if (en == null || en.getEnCod() <= 0)
            return false;
        if (en.getEnRemitoFactura() == null || en.getEnFechaEntrega() == null
                || en.getEnTiempoPermanencia() <= 0 || en.getBarrilesBCod() <= 0
                || en.getClientesClCod() <= 0) {
            return false;
        }
        return dao.modificar(en);
    }
}
