package BackEnd.models;

public class lupulo {
    private int luCod;
    private String luVariedad;
    private int luAnioCosecha;
    private String luFormato;
    private double luPorcentajeAlfaAcido;
    private double luCantidad;
    private String luProposito;

    public lupulo(int luCod, String luVariedad, int luAnioCosecha, String luFormato, double luPorcentajeAlfaAcido,
            double luCantidad, String luProposito) {
        this.luCod = luCod;
        this.luVariedad = luVariedad;
        this.luAnioCosecha = luAnioCosecha;
        this.luFormato = luFormato;
        this.luPorcentajeAlfaAcido = luPorcentajeAlfaAcido;
        this.luCantidad = luCantidad;
        this.luProposito = luProposito;
    }

    public lupulo(String luVariedad, int luAnioCosecha, String luFormato, double luPorcentajeAlfaAcido,
            double luCantidad, String luProposito) {
        this.luVariedad = luVariedad;
        this.luAnioCosecha = luAnioCosecha;
        this.luFormato = luFormato;
        this.luPorcentajeAlfaAcido = luPorcentajeAlfaAcido;
        this.luCantidad = luCantidad;
        this.luProposito = luProposito;
    }

    public int getLuCod() {
        return luCod;
    }

    public String getLuVariedad() {
        return luVariedad;
    }

    public int getLuAnioCosecha() {
        return luAnioCosecha;
    }

    public String getLuFormato() {
        return luFormato;
    }

    public double getLuPorcentajeAlfaAcido() {
        return luPorcentajeAlfaAcido;
    }

    public double getLuCantidad() {
        return luCantidad;
    }

    public String getLuProposito() {
        return luProposito;
    }

    public void setLuCod(int luCod) {
        this.luCod = luCod;
    }

    public void setLuVariedad(String luVariedad) {
        this.luVariedad = luVariedad;
    }

    public void setLuAnioCosecha(int luAnioCosecha) {
        this.luAnioCosecha = luAnioCosecha;
    }

    public void setLuFormato(String luFormato) {
        this.luFormato = luFormato;
    }

    public void setLuPorcentajeAlfaAcido(double luPorcentajeAlfaAcido) {
        this.luPorcentajeAlfaAcido = luPorcentajeAlfaAcido;
    }

    public void setLuCantidad(double luCantidad) {
        this.luCantidad = luCantidad;
    }

    public void setLuProposito(String luProposito) {
        this.luProposito = luProposito;
    }
}
