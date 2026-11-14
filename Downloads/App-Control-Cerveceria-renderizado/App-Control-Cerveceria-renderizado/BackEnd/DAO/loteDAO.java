package BackEnd.DAO;

import BackEnd.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import BackEnd.models.lote;

public class loteDAO {
    public boolean registrarLotePLSQL(Double volTotal, Date fVenc, Date fMac, Double cMac, Date fHerv, Double cHerv,
            Date fWhir, Double cWhir, Date fFerm, Double cFerm, Date fDry, Double cDry, Date fCarb, Double cCarb,
            Date fFin, int recetaCod, int barrilCod, Date fInicioComp, double bCap, String bConector, String bEstado,
            String bUbi, int bInvCod) {
        // Mamita que quedo larga la tx
        String sql = "{call lote_alta(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";

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
            if (barrilCod > 0)
                stmt.setInt(17, barrilCod);
            else
                stmt.setNull(17, Types.NUMERIC);
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

    public List<lote> getAll() {
        List<lote> lista = new ArrayList<>();
        String sql = "SELECT * FROM Lotes";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                lista.add(new lote(rs.getInt("lot_cod"), rs.getDouble("lot_volumen_total"),
                        rs.getDate("lot_fecha_vecimiento"), rs.getDate("fecha_maceracion"),
                        rs.getDouble("cantidad_maceracion"), rs.getDate("fecha_hervido"),
                        rs.getDouble("cantidad_hervido"), rs.getDate("fecha_whirpool"),
                        rs.getDouble("cantidad_whirpool"), rs.getDate("fecha_fermentacion"),
                        rs.getDouble("cantidad_fermentacion"), rs.getDate("fecha_dryhopping"),
                        rs.getDouble("cantidad_dryhopping"), rs.getDate("fecha_carbonatacion"),
                        rs.getDouble("cantidad_carbonatacion"), rs.getDate("lot_fecha_fin_preparado"),
                        rs.getInt("Recetas_r_cod")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return lista;
    }

    public lote getPorID(int id) {
        String sql = "SELECT * FROM Lotes WHERE lot_cod = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {
            consulta.setInt(1, id);
            try (ResultSet rta = consulta.executeQuery()) {
                if (rta.next()) {
                    return new lote(
                            rta.getInt("lot_cod"),
                            rta.getDouble("lot_volumen_total"),
                            rta.getDate("lot_fecha_vecimiento"),
                            rta.getDate("fecha_maceracion"),
                            rta.getDouble("cantidad_maceracion"),
                            rta.getDate("fecha_hervido"),
                            rta.getDouble("cantidad_hervido"),
                            rta.getDate("fecha_whirpool"),
                            rta.getDouble("cantidad_whirpool"),
                            rta.getDate("fecha_fermentacion"),
                            rta.getDouble("cantidad_fermentacion"),
                            rta.getDate("fecha_dryhopping"),
                            rta.getDouble("cantidad_dryhopping"),
                            rta.getDate("fecha_carbonatacion"),
                            rta.getDouble("cantidad_carbonatacion"),
                            rta.getDate("lot_fecha_fin_preparado"),
                            rta.getInt("Recetas_r_cod"));
                }
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public boolean modificar(lote lote) {
        String sql = "UPDATE Lotes SET lot_volumen_total=?, lot_fecha_vecimiento=?, fecha_maceracion=?, cantidad_maceracion=?, fecha_hervido=?, cantidad_hervido=?, fecha_whirpool=?, cantidad_whirpool=?, fecha_fermentacion=?, cantidad_fermentacion=?, fecha_dryhopping=?, cantidad_dryhopping=?, fecha_carbonatacion=?, cantidad_carbonatacion=?, lot_fecha_fin_preparado=?, Recetas_r_cod=? WHERE lot_cod=?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement consulta = conn.prepareStatement(sql)) {

            consulta.setDouble(1, lote.getLotVolumenTotal());
            consulta.setDate(2, lote.getLotFechaVecimiento());
            consulta.setObject(3, lote.getFechaMaceracion(), Types.DATE);
            consulta.setObject(4, lote.getCantidadMaceracion(), Types.NUMERIC);
            consulta.setObject(5, lote.getFechaHervido(), Types.DATE);
            consulta.setObject(6, lote.getCantidadHervido(), Types.NUMERIC);
            consulta.setObject(7, lote.getFechaWhirpool(), Types.DATE);
            consulta.setObject(8, lote.getCantidadWhirpool(), Types.NUMERIC);
            consulta.setObject(9, lote.getFechaFermentacion(), Types.DATE);
            consulta.setObject(10, lote.getCantidadFermentacion(), Types.NUMERIC);
            consulta.setObject(11, lote.getFechaDryhopping(), Types.DATE);
            consulta.setObject(12, lote.getCantidadDryhopping(), Types.NUMERIC);
            consulta.setObject(13, lote.getFechaCarbonatacion(), Types.DATE);
            consulta.setObject(14, lote.getCantidadCarbonatacion(), Types.NUMERIC);
            consulta.setObject(15, lote.getLotFechaFinPreparado(), Types.DATE);
            consulta.setInt(16, lote.getRecetasRCod());
            consulta.setInt(17, lote.getLotCod());
            return consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }
}
