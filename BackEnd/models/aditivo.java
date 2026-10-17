package BackEnd.models;

public class aditivo {
    private int aCod;
    private String nombreAditivo;
    private String tipoAditivo;
    private double cantidadAditivo;

    public aditivo(int aCod, String nombreAditivo, String tipoAditivo, double cantidadAditivo) {
        this.aCod = aCod;
        this.nombreAditivo = nombreAditivo;
        this.tipoAditivo = tipoAditivo;
        this.cantidadAditivo = cantidadAditivo;
    }

    public aditivo(String nombreAditivo, String tipoAditivo, double cantidadAditivo) {
        this.nombreAditivo = nombreAditivo;
        this.tipoAditivo = tipoAditivo;
        this.cantidadAditivo = cantidadAditivo;
    }

    public int getaCod() {
        return aCod;
    }

    public String getNombreAditivo() {
        return nombreAditivo;
    }

    public String getTipoAditivo() {
        return tipoAditivo;
    }

    public double getCantidadAditivo() {
        return cantidadAditivo;
    }

    public void setaCod(int aCod) {
        this.aCod = aCod;
    }

    public void setNombreAditivo(String nombreAditivo) {
        this.nombreAditivo = nombreAditivo;
    }

    public void setTipoAditivo(String tipoAditivo) {
        this.tipoAditivo = tipoAditivo;
    }

    public void setCantidadAditivo(double cantidadAditivo) {
        this.cantidadAditivo = cantidadAditivo;
    }
}
