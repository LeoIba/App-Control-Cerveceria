package BackEnd.models;

public class hervido {
    private int hCod;
    private int hTiempoEbullicion;

    public hervido(int hCod, int hTiempoEbullicion) {
        this.hCod = hCod;
        this.hTiempoEbullicion = hTiempoEbullicion;
    }

    public hervido(int hTiempoEbullicion) {
        this.hTiempoEbullicion = hTiempoEbullicion;
    }

    public int gethCod() {
        return hCod;
    }

    public void sethCod(int hCod) {
        this.hCod = hCod;
    }

    public int gethTiempoEbullicion() {
        return hTiempoEbullicion;
    }

    public void sethTiempoEbullicion(int hTiempoEbullicion) {
        this.hTiempoEbullicion = hTiempoEbullicion;
    }
}
