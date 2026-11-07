package BackEnd.models;

public class grano {
    private int gCod;
    private String gNombre;
    private String gTipo;
    private double gPorcentaje;
    private double gPeso;
    private String gPotencialExtraccion;
    private String gColor;

    public grano(int gCod, String gNombre, String gTipo, double gPorcentaje, double gPeso, String gPotencialExtraccion,
            String gColor) {
        this.gCod = gCod;
        this.gNombre = gNombre;
        this.gTipo = gTipo;
        this.gPorcentaje = gPorcentaje;
        this.gPeso = gPeso;
        this.gPotencialExtraccion = gPotencialExtraccion;
        this.gColor = gColor;
    }

    public grano(String gNombre, String gTipo, double gPorcentaje, double gPeso, String gPotencialExtraccion,
            String gColor) {
        this.gNombre = gNombre;
        this.gTipo = gTipo;
        this.gPorcentaje = gPorcentaje;
        this.gPeso = gPeso;
        this.gPotencialExtraccion = gPotencialExtraccion;
        this.gColor = gColor;
    }

    public int getgCod() {
        return gCod;
    }

    public String getgNombre() {
        return gNombre;
    }

    public String getgTipo() {
        return gTipo;
    }

    public double getgPorcentaje() {
        return gPorcentaje;
    }

    public double getgPeso() {
        return gPeso;
    }

    public String getgPotencialExtraccion() {
        return gPotencialExtraccion;
    }

    public String getgColor() {
        return gColor;
    }

    public void setgCod(int gCod) {
        this.gCod = gCod;
    }

    public void setgNombre(String gNombre) {
        this.gNombre = gNombre;
    }

    public void setgTipo(String gTipo) {
        this.gTipo = gTipo;
    }

    public void setgPorcentaje(double gPorcentaje) {
        this.gPorcentaje = gPorcentaje;
    }

    public void setgPeso(double gPeso) {
        this.gPeso = gPeso;
    }

    public void setgPotencialExtraccion(String gPotencialExtraccion) {
        this.gPotencialExtraccion = gPotencialExtraccion;
    }

    public void setgColor(String gColor) {
        this.gColor = gColor;
    }
}
