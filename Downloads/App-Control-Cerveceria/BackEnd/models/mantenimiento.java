package BackEnd.models;

import java.sql.Date;

public class mantenimiento {
    private int manCod;
    private Date manFechaLavado;
    private String manCambioDeSello;
    private String manPruebaPresion;
    private int barrilesBCod;

    public mantenimiento(int manCod, Date manFechaLavado, String manCambioDeSello, String manPruebaPresion,
            int barrilesBCod) {
        this.manCod = manCod;
        this.manFechaLavado = manFechaLavado;
        this.manCambioDeSello = manCambioDeSello;
        this.manPruebaPresion = manPruebaPresion;
        this.barrilesBCod = barrilesBCod;
    }

    public mantenimiento(Date manFechaLavado, String manCambioDeSello, String manPruebaPresion, int barrilesBCod) {
        this.manFechaLavado = manFechaLavado;
        this.manCambioDeSello = manCambioDeSello;
        this.manPruebaPresion = manPruebaPresion;
        this.barrilesBCod = barrilesBCod;
    }

    public int getManCod() {
        return manCod;
    }

    public void setManCod(int manCod) {
        this.manCod = manCod;
    }

    public Date getManFechaLavado() {
        return manFechaLavado;
    }

    public void setManFechaLavado(Date manFechaLavado) {
        this.manFechaLavado = manFechaLavado;
    }

    public String getManCambioDeSello() {
        return manCambioDeSello;
    }

    public void setManCambioDeSello(String manCambioDeSello) {
        this.manCambioDeSello = manCambioDeSello;
    }

    public String getManPruebaPresion() {
        return manPruebaPresion;
    }

    public void setManPruebaPresion(String manPruebaPresion) {
        this.manPruebaPresion = manPruebaPresion;
    }

    public int getBarrilesBCod() {
        return barrilesBCod;
    }

    public void setBarrilesBCod(int barrilesBCod) {
        this.barrilesBCod = barrilesBCod;
    }
}
