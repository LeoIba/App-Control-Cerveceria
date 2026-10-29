package BackEnd.DAO;

import BackEnd.DBConnection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;

public class loteDAO {
    public boolean registrarLotePLSQL(Double volTotal, Date fVenc, Date fMac, Double cMac, Date fHerv, Double cHerv,
            Date fWhir, Double cWhir, Date fFerm, Double cFerm, Date fDry, Double cDry, Date fCarb, Double cCarb,
            Date fFin, int recetaCod, int barrilCod, Date fInicioComp, double bCap, String bConector, String bEstado,
            String bUbi, int bInvCod) {
        // Mamita que quedo larga la tx
        String sql = "{call registrar_lote_transaccion(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (Connection conn = DBConnection.getConnection();
                CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setDouble(1, volTotal);
            stmt.setDate(2, fVenc);

            // Las etapas pueden ser nulas porque todavia no pasan por ahí
            if (fMac != null)
                stmt.setDate(3, fMac);
            else
                stmt.setNull(3, Types.DATE);
            if (cMac != null)
                stmt.setDouble(4, cMac);
            else
                stmt.setNull(4, Types.NUMERIC);
            if (fHerv != null)
                stmt.setDate(5, fHerv);
            else
                stmt.setNull(5, Types.DATE);
            if (cHerv != null)
                stmt.setDouble(6, cHerv);
            else
                stmt.setNull(6, Types.NUMERIC);
            if (fWhir != null)
                stmt.setDate(7, fWhir);
            else
                stmt.setNull(7, Types.DATE);
            if (cWhir != null)
                stmt.setDouble(8, cWhir);
            else
                stmt.setNull(8, Types.NUMERIC);
            if (fFerm != null)
                stmt.setDate(9, fFerm);
            else
                stmt.setNull(9, Types.DATE);
            if (cFerm != null)
                stmt.setDouble(10, cFerm);
            else
                stmt.setNull(10, Types.NUMERIC);
            if (fDry != null)
                stmt.setDate(11, fDry);
            else
                stmt.setNull(11, Types.DATE);
            if (cDry != null)
                stmt.setDouble(12, cDry);
            else
                stmt.setNull(12, Types.NUMERIC);
            if (fCarb != null)
                stmt.setDate(13, fCarb);
            else
                stmt.setNull(13, Types.DATE);
            if (cCarb != null)
                stmt.setDouble(14, cCarb);
            else
                stmt.setNull(14, Types.NUMERIC);
            if (fFin != null)
                stmt.setDate(15, fFin);
            else
                stmt.setNull(15, Types.DATE);
            stmt.setInt(16, recetaCod);
            stmt.setInt(17, barrilCod);
            stmt.setDate(18, fInicioComp);
            stmt.setDouble(19, bCap);
            stmt.setString(20, bConector);
            stmt.setString(21, bEstado);
            stmt.setString(22, bUbi);
            stmt.setInt(23, bInvCod);
            stmt.execute();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en la Transacción de Alta de Lote: " + e.getMessage());
            return false;
        }
    }
}
