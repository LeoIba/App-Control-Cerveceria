package BackEnd.controllers;

import BackEnd.DAO.loteDAO;
import java.sql.Date;

public class loteController {

    private loteDAO dao = new loteDAO();

    public boolean altaLote(Double volTotal, Date fVenc, Date fMac, Double cMac, Date fHerv, Double cHerv, Date fWhir,
            Double cWhir, Date fFerm, Double cFerm, Date fDry, Double cDry, Date fCarb, Double cCarb, Date fFin,
            int recetaCod, int barrilCod, Date fInicioComp, double bCap, String bConector, String bEstado, String bUbi,
            int bInvCod) {
        if (volTotal == null || volTotal <= 0) {
            return false;
        }
        if (fVenc == null) {
            return false;
        }
        if (recetaCod <= 0 || barrilCod <= 0 || fInicioComp == null) {
            return false;
        }
        return dao.registrarLotePLSQL(volTotal, fVenc, fMac, cMac, fHerv, cHerv, fWhir, cWhir, fFerm, cFerm, fDry, cDry,
                fCarb, cCarb, fFin, recetaCod, barrilCod, fInicioComp, bCap, bConector, bEstado, bUbi, bInvCod);
    }
}