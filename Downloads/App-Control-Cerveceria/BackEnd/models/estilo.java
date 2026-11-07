package BackEnd.models;

public class estilo {
    private int ptCod;
    private String ptEstiloNombre;
    private double ptDensidadIniMinima;
    private double ptDensidadIniMaxima;
    private double ptDensidadFinMinima;
    private double ptDensidadFinMaxima;
    private double ptAlcoholMinimo;
    private double ptAlcoholMaximo;
    private double ptAmargorMinimo;
    private double ptAmargorMaximo;
    private double ptColorMinimo;
    private double ptColorMaximo;

    public estilo(int ptCod, String ptEstiloNombre, double ptDensidadIniMinima, double ptDensidadIniMaxima,
            double ptDensidadFinMinima, double ptDensidadFinMaxima, double ptAlcoholMinimo, double ptAlcoholMaximo,
            double ptAmargorMinimo, double ptAmargorMaximo, double ptColorMinimo, double ptColorMaximo) {
        this.ptCod = ptCod;
        this.ptEstiloNombre = ptEstiloNombre;
        this.ptDensidadIniMinima = ptDensidadIniMinima;
        this.ptDensidadIniMaxima = ptDensidadIniMaxima;
        this.ptDensidadFinMinima = ptDensidadFinMinima;
        this.ptDensidadFinMaxima = ptDensidadFinMaxima;
        this.ptAlcoholMinimo = ptAlcoholMinimo;
        this.ptAlcoholMaximo = ptAlcoholMaximo;
        this.ptAmargorMinimo = ptAmargorMinimo;
        this.ptAmargorMaximo = ptAmargorMaximo;
        this.ptColorMinimo = ptColorMinimo;
        this.ptColorMaximo = ptColorMaximo;
    }

    public estilo(String ptEstiloNombre, double ptDensidadIniMinima, double ptDensidadIniMaxima,
            double ptDensidadFinMinima, double ptDensidadFinMaxima, double ptAlcoholMinimo, double ptAlcoholMaximo,
            double ptAmargorMinimo, double ptAmargorMaximo, double ptColorMinimo, double ptColorMaximo) {
        this.ptEstiloNombre = ptEstiloNombre;
        this.ptDensidadIniMinima = ptDensidadIniMinima;
        this.ptDensidadIniMaxima = ptDensidadIniMaxima;
        this.ptDensidadFinMinima = ptDensidadFinMinima;
        this.ptDensidadFinMaxima = ptDensidadFinMaxima;
        this.ptAlcoholMinimo = ptAlcoholMinimo;
        this.ptAlcoholMaximo = ptAlcoholMaximo;
        this.ptAmargorMinimo = ptAmargorMinimo;
        this.ptAmargorMaximo = ptAmargorMaximo;
        this.ptColorMinimo = ptColorMinimo;
        this.ptColorMaximo = ptColorMaximo;
    }

    public int getPtCod() {
        return ptCod;
    }

    public void setPtCod(int ptCod) {
        this.ptCod = ptCod;
    }

    public String getPtEstiloNombre() {
        return ptEstiloNombre;
    }

    public void setPtEstiloNombre(String ptEstiloNombre) {
        this.ptEstiloNombre = ptEstiloNombre;
    }

    public double getPtDensidadIniMinima() {
        return ptDensidadIniMinima;
    }

    public void setPtDensidadIniMinima(double ptDensidadIniMinima) {
        this.ptDensidadIniMinima = ptDensidadIniMinima;
    }

    public double getPtDensidadIniMaxima() {
        return ptDensidadIniMaxima;
    }

    public void setPtDensidadIniMaxima(double ptDensidadIniMaxima) {
        this.ptDensidadIniMaxima = ptDensidadIniMaxima;
    }

    public double getPtDensidadFinMinima() {
        return ptDensidadFinMinima;
    }

    public void setPtDensidadFinMinima(double ptDensidadFinMinima) {
        this.ptDensidadFinMinima = ptDensidadFinMinima;
    }

    public double getPtDensidadFinMaxima() {
        return ptDensidadFinMaxima;
    }

    public void setPtDensidadFinMaxima(double ptDensidadFinMaxima) {
        this.ptDensidadFinMaxima = ptDensidadFinMaxima;
    }

    public double getPtAlcoholMinimo() {
        return ptAlcoholMinimo;
    }

    public void setPtAlcoholMinimo(double ptAlcoholMinimo) {
        this.ptAlcoholMinimo = ptAlcoholMinimo;
    }

    public double getPtAlcoholMaximo() {
        return ptAlcoholMaximo;
    }

    public void setPtAlcoholMaximo(double ptAlcoholMaximo) {
        this.ptAlcoholMaximo = ptAlcoholMaximo;
    }

    public double getPtAmargorMinimo() {
        return ptAmargorMinimo;
    }

    public void setPtAmargorMinimo(double ptAmargorMinimo) {
        this.ptAmargorMinimo = ptAmargorMinimo;
    }

    public double getPtAmargorMaximo() {
        return ptAmargorMaximo;
    }

    public void setPtAmargorMaximo(double ptAmargorMaximo) {
        this.ptAmargorMaximo = ptAmargorMaximo;
    }

    public double getPtColorMinimo() {
        return ptColorMinimo;
    }

    public void setPtColorMinimo(double ptColorMinimo) {
        this.ptColorMinimo = ptColorMinimo;
    }

    public double getPtColorMaximo() {
        return ptColorMaximo;
    }

    public void setPtColorMaximo(double ptColorMaximo) {
        this.ptColorMaximo = ptColorMaximo;
    }
}
