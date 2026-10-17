package BackEnd.models;

public class barril {
    private int bCod;
    private double bCapacidad;
    private String bTipoConector;
    private String bEstadoOperativo;
    private String bUbicacionActual;
    private int inventarioICod;

    public barril(int bCod, double bCapacidad, String bTipoConector, String bEstadoOperativo, String bUbicacionActual,
            int inventarioICod) {
        this.bCod = bCod;
        this.bCapacidad = bCapacidad;
        this.bTipoConector = bTipoConector;
        this.bEstadoOperativo = bEstadoOperativo;
        this.bUbicacionActual = bUbicacionActual;
        this.inventarioICod = inventarioICod;
    }

    public barril(double bCapacidad, String bTipoConector, String bEstadoOperativo, String bUbicacionActual,
            int inventarioICod) {
        this.bCapacidad = bCapacidad;
        this.bTipoConector = bTipoConector;
        this.bEstadoOperativo = bEstadoOperativo;
        this.bUbicacionActual = bUbicacionActual;
        this.inventarioICod = inventarioICod;
    }

    public int getbCod() {
        return bCod;
    }

    public void setbCod(int bCod) {
        this.bCod = bCod;
    }

    public double getbCapacidad() {
        return bCapacidad;
    }

    public void setbCapacidad(double bCapacidad) {
        this.bCapacidad = bCapacidad;
    }

    public String getbTipoConector() {
        return bTipoConector;
    }

    public void setbTipoConector(String bTipoConector) {
        this.bTipoConector = bTipoConector;
    }

    public String getbEstadoOperativo() {
        return bEstadoOperativo;
    }

    public void setbEstadoOperativo(String bEstadoOperativo) {
        this.bEstadoOperativo = bEstadoOperativo;
    }

    public String getbUbicacionActual() {
        return bUbicacionActual;
    }

    public void setbUbicacionActual(String bUbicacionActual) {
        this.bUbicacionActual = bUbicacionActual;
    }

    public int getInventarioICod() {
        return inventarioICod;
    }

    public void setInventarioICod(int inventarioICod) {
        this.inventarioICod = inventarioICod;
    }
}
