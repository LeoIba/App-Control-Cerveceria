package BackEnd.models;

public class dryHopping {
    private int fDryCod;
    private double tiempoContacto;
    private double temperatura;

    public dryHopping(int fDryCod, double tiempoContacto, double temperatura) {
        this.fDryCod = fDryCod;
        this.tiempoContacto = tiempoContacto;
        this.temperatura = temperatura;
    }

    public dryHopping(double tiempoContacto, double temperatura) {
        this.tiempoContacto = tiempoContacto;
        this.temperatura = temperatura;
    }

    public int getfDryCod() {
        return fDryCod;
    }

    public void setfDryCod(int fDryCod) {
        this.fDryCod = fDryCod;
    }

    public double getTiempoContacto() {
        return tiempoContacto;
    }

    public void setTiempoContacto(double tiempoContacto) {
        this.tiempoContacto = tiempoContacto;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(double temperatura) {
        this.temperatura = temperatura;
    }
}
