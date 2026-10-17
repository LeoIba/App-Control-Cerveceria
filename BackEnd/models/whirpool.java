package BackEnd.models;

public class whirpool {
    private int wCod;
    private double wTiempoInicio;
    private double wTiempoCentrifugado;
    private double wTiempoReposo;

    public whirpool(int wCod, double wTiempoInicio, double wTiempoCentrifugado, double wTiempoReposo) {
        this.wCod = wCod;
        this.wTiempoInicio = wTiempoInicio;
        this.wTiempoCentrifugado = wTiempoCentrifugado;
        this.wTiempoReposo = wTiempoReposo;
    }

    public whirpool(double wTiempoInicio, double wTiempoCentrifugado, double wTiempoReposo) {
        this.wTiempoInicio = wTiempoInicio;
        this.wTiempoCentrifugado = wTiempoCentrifugado;
        this.wTiempoReposo = wTiempoReposo;
    }

    public int getwCod() {
        return wCod;
    }

    public void setwCod(int wCod) {
        this.wCod = wCod;
    }

    public double getwTiempoInicio() {
        return wTiempoInicio;
    }

    public void setwTiempoInicio(double wTiempoInicio) {
        this.wTiempoInicio = wTiempoInicio;
    }

    public double getwTiempoCentrifugado() {
        return wTiempoCentrifugado;
    }

    public void setwTiempoCentrifugado(double wTiempoCentrifugado) {
        this.wTiempoCentrifugado = wTiempoCentrifugado;
    }

    public double getwTiempoReposo() {
        return wTiempoReposo;
    }

    public void setwTiempoReposo(double wTiempoReposo) {
        this.wTiempoReposo = wTiempoReposo;
    }
}
