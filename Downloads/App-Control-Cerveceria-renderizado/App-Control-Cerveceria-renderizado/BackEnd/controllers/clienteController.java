package BackEnd.controllers;

import BackEnd.DAO.clienteDAO;
import java.sql.Date;
import java.util.List;
import BackEnd.models.cliente;

public class clienteController {
    private clienteDAO dao = new clienteDAO();

    public boolean altaCliente(String nombre, String direccion, String telefono, Integer barrilCod, Date fechaEntrega,
            String remito, Double tiempoPerm, Date fechaDev) {
        if (nombre == null || direccion == null || telefono == null) {
            return false;
        }

        // Si queres cargar un barril para dar de alta tambien la entrega, la fecha es
        // obligatoria
        if (barrilCod != null && barrilCod > 0 && fechaEntrega == null) {
            return false;
        }
        return dao.registrarClientePLSQL(nombre, direccion, telefono, barrilCod, fechaEntrega, remito, tiempoPerm,
                fechaDev);
    }

    public List<cliente> listarTodos() {
        return dao.getAll();
    }

    public cliente busquedaPorID(int id) {
        if (id <= 0)
            return null;
        return dao.getPorID(id);
    }

    public boolean actualizar(cliente cliente) {
        if (cliente == null || cliente.getClCod() <= 0)
            return false;
        if (cliente.getClNombre() == null || cliente.getClDireccion() == null || cliente.getClTelefono() == null)
            return false;
        return dao.modificar(cliente);
    }
}
