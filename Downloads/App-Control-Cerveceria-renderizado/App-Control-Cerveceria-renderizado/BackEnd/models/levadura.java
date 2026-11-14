package BackEnd.models;

public class levadura {
    private int leCod;
    private String leCepa;
    private String leFormatoLev;
    private double leCantidadLev;
    private double leTasaInoculacion;

    public levadura(int leCod, String leCepa, String leFormatoLev, double leCantidadLev, double leTasaInoculacion) {
        this.leCod = leCod;
        this.leCepa = leCepa;
        this.leFormatoLev = leFormatoLev;
        this.leCantidadLev = leCantidadLev;
        this.leTasaInoculacion = leTasaInoculacion;
    }

    public levadura(String leCepa, String leFormatoLev, double leCantidadLev, double leTasaInoculacion) {
        this.leCepa = leCepa;
        this.leFormatoLev = leFormatoLev;
        this.leCantidadLev = leCantidadLev;
        this.leTasaInoculacion = leTasaInoculacion;
    }

    public int getLeCod() {
        return leCod;
    }

    public String getLeCepa() {
        return leCepa;
    }

    public String getLeFormatoLev() {
        return leFormatoLev;
    }

    public double getLeCantidadLev() {
        return leCantidadLev;
    }

    public double getLeTasaInoculacion() {
        return leTasaInoculacion;
    }

    public void setLeCod(int leCod) {
        this.leCod = leCod;
    }

    public void setLeCepa(String leCepa) {
        this.leCepa = leCepa;
    }

    public void setLeFormatoLev(String leFormatoLev) {
        this.leFormatoLev = leFormatoLev;
    }

    public void setLeCantidadLev(double leCantidadLev) {
        this.leCantidadLev = leCantidadLev;
    }

    public void setLeTasaInoculacion(double leTasaInoculacion) {
        this.leTasaInoculacion = leTasaInoculacion;
    }
}
