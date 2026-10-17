package BackEnd.models;

import java.sql.Date;

public class entrega {
    private int enCod;
    private String enRemitoFacutra;
    private Date enFechaEntrega;
    private double enTiempoPermanencia;
    private Date enFechaDevolucion; // Puede ser nula hasta que devuelvan el barril
    private int barrilesBCod;
    private int clientesClCod;

    public entrega(int enCod, String enRemitoFacutra, Date enFechaEntrega, double enTiempoPermanencia,
            Date enFechaDevolucion, int barrilesBCod, int clientesClCod) {
        this.enCod = enCod;
        this.enRemitoFacutra = enRemitoFacutra;
        this.enFechaEntrega = enFechaEntrega;
        this.enTiempoPermanencia = enTiempoPermanencia;
        this.enFechaDevolucion = enFechaDevolucion;
        this.barrilesBCod = barrilesBCod;
        this.clientesClCod = clientesClCod;
    }

    public entrega(String enRemitoFacutra, Date enFechaEntrega, double enTiempoPermanencia, Date enFechaDevolucion,
            int barrilesBCod, int clientesClCod) {
        this.enRemitoFacutra = enRemitoFacutra;
        this.enFechaEntrega = enFechaEntrega;
        this.enTiempoPermanencia = enTiempoPermanencia;
        this.enFechaDevolucion = enFechaDevolucion;
        this.barrilesBCod = barrilesBCod;
        this.clientesClCod = clientesClCod;
    }

    public int getEnCod() {
        return enCod;
    }

    public void setEnCod(int enCod) {
        this.enCod = enCod;
    }

    public String getEnRemitoFacutra() {
        return enRemitoFacutra;
    }

    public void setEnRemitoFacutra(String enRemitoFacutra) {
        this.enRemitoFacutra = enRemitoFacutra;
    }

    public Date getEnFechaEntrega() {
        return enFechaEntrega;
    }

    public void setEnFechaEntrega(Date enFechaEntrega) {
        this.enFechaEntrega = enFechaEntrega;
    }

    public double getEnTiempoPermanencia() {
        return enTiempoPermanencia;
    }

    public void setEnTiempoPermanencia(double enTiempoPermanencia) {
        this.enTiempoPermanencia = enTiempoPermanencia;
    }

    public Date getEnFechaDevolucion() {
        return enFechaDevolucion;
    }

    public void setEnFechaDevolucion(Date enFechaDevolucion) {
        this.enFechaDevolucion = enFechaDevolucion;
    }

    public int getBarrilesBCod() {
        return barrilesBCod;
    }

    public void setBarrilesBCod(int barrilesBCod) {
        this.barrilesBCod = barrilesBCod;
    }

    public int getClientesClCod() {
        return clientesClCod;
    }

    public void setClientesClCod(int clientesClCod) {
        this.clientesClCod = clientesClCod;
    }
}
