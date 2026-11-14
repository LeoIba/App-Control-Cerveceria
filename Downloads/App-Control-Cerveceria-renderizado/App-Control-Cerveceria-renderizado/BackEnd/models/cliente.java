package BackEnd.models;

public class cliente {
    private int clCod;
    private String clNombre;
    private String clDireccion;
    private String clTelefono;

    public cliente(int clCod, String clNombre, String clDireccion, String clTelefono) {
        this.clCod = clCod;
        this.clNombre = clNombre;
        this.clDireccion = clDireccion;
        this.clTelefono = clTelefono;
    }

    public cliente(String clNombre, String clDireccion, String clTelefono) {
        this.clNombre = clNombre;
        this.clDireccion = clDireccion;
        this.clTelefono = clTelefono;
    }

    public int getClCod() {
        return clCod;
    }

    public void setClCod(int clCod) {
        this.clCod = clCod;
    }

    public String getClNombre() {
        return clNombre;
    }

    public void setClNombre(String clNombre) {
        this.clNombre = clNombre;
    }

    public String getClDireccion() {
        return clDireccion;
    }

    public void setClDireccion(String clDireccion) {
        this.clDireccion = clDireccion;
    }

    public String getClTelefono() {
        return clTelefono;
    }

    public void setClTelefono(String clTelefono) {
        this.clTelefono = clTelefono;
    }
}
