package BackEnd.models;

public class inventario {
    private int iCod;
    private double iLitrosTotales;
    private String iEstadoEtapa;

    public inventario(int iCod, double iLitrosTotales, String iEstadoEtapa) {
        this.iCod = iCod;
        this.iLitrosTotales = iLitrosTotales;
        this.iEstadoEtapa = iEstadoEtapa;
    }

    public inventario(double iLitrosTotales, String iEstadoEtapa) {
        this.iLitrosTotales = iLitrosTotales;
        this.iEstadoEtapa = iEstadoEtapa;
    }

    public int getiCod() {
        return iCod;
    }

    public void setiCod(int iCod) {
        this.iCod = iCod;
    }

    public double getiLitrosTotales() {
        return iLitrosTotales;
    }

    public void setiLitrosTotales(double iLitrosTotales) {
        this.iLitrosTotales = iLitrosTotales;
    }

    public String getiEstadoEtapa() {
        return iEstadoEtapa;
    }

    public void setiEstadoEtapa(String iEstadoEtapa) {
        this.iEstadoEtapa = iEstadoEtapa;
    }
}
