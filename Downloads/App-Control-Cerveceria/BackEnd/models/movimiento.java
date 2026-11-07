package BackEnd.models;

import java.sql.Date;

public class movimiento {
    private int movCod;
    private Date movFecha;
    private int movHora;
    private String movPuntoOrigen;
    private String movPuntoDestino;
    private String movResponsable;
    private int barrilesBCod;

    public movimiento(int movCod, Date movFecha, int movHora, String movPuntoOrigen, String movPuntoDestino,
            String movResponsable, int barrilesBCod) {
        this.movCod = movCod;
        this.movFecha = movFecha;
        this.movHora = movHora;
        this.movPuntoOrigen = movPuntoOrigen;
        this.movPuntoDestino = movPuntoDestino;
        this.movResponsable = movResponsable;
        this.barrilesBCod = barrilesBCod;
    }

    public movimiento(Date movFecha, int movHora, String movPuntoOrigen, String movPuntoDestino, String movResponsable,
            int barrilesBCod) {
        this.movFecha = movFecha;
        this.movHora = movHora;
        this.movPuntoOrigen = movPuntoOrigen;
        this.movPuntoDestino = movPuntoDestino;
        this.movResponsable = movResponsable;
        this.barrilesBCod = barrilesBCod;
    }

    public int getMovCod() {
        return movCod;
    }

    public void setMovCod(int movCod) {
        this.movCod = movCod;
    }

    public Date getMovFecha() {
        return movFecha;
    }

    public void setMovFecha(Date movFecha) {
        this.movFecha = movFecha;
    }

    public int getMovHora() {
        return movHora;
    }

    public void setMovHora(int movHora) {
        this.movHora = movHora;
    }

    public String getMovPuntoOrigen() {
        return movPuntoOrigen;
    }

    public void setMovPuntoOrigen(String movPuntoOrigen) {
        this.movPuntoOrigen = movPuntoOrigen;
    }

    public String getMovPuntoDestino() {
        return movPuntoDestino;
    }

    public void setMovPuntoDestino(String movPuntoDestino) {
        this.movPuntoDestino = movPuntoDestino;
    }

    public String getMovResponsable() {
        return movResponsable;
    }

    public void setMovResponsable(String movResponsable) {
        this.movResponsable = movResponsable;
    }

    public int getBarrilesBCod() {
        return barrilesBCod;
    }

    public void setBarrilesBCod(int barrilesBCod) {
        this.barrilesBCod = barrilesBCod;
    }
}
