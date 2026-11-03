package BackEnd.controllers;

import BackEnd.DAO.entregaDAO;
import java.sql.Date;

public class entregaController {
    private entregaDAO dao = new entregaDAO();

    public boolean registrar(String remito, Date fEntrega, double tiempoPerm, Date fDevolucion, int barrilCod,
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
}
