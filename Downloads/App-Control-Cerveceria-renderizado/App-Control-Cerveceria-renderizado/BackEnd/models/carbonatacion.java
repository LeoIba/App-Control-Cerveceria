package BackEnd.models;

public class carbonatacion {
    private int carCod;
    private double carVolObjCO2;
    private String carMetodoEmpleado;

    public carbonatacion(int carCod, double carVolObjCO2, String carMetodoEmpleado) {
        this.carCod = carCod;
        this.carVolObjCO2 = carVolObjCO2;
        this.carMetodoEmpleado = carMetodoEmpleado;
    }

    public carbonatacion(double carVolObjCO2, String carMetodoEmpleado) {
        this.carVolObjCO2 = carVolObjCO2;
        this.carMetodoEmpleado = carMetodoEmpleado;
    }

    public int getCarCod() {
        return carCod;
    }

    public void setCarCod(int carCod) {
        this.carCod = carCod;
    }

    public double getCarVolObjCO2() {
        return carVolObjCO2;
    }

    public void setCarVolObjCO2(double carVolObjCO2) {
        this.carVolObjCO2 = carVolObjCO2;
    }

    public String getCarMetodoEmpleado() {
        return carMetodoEmpleado;
    }

    public void setCarMetodoEmpleado(String carMetodoEmpleado) {
        this.carMetodoEmpleado = carMetodoEmpleado;
    }
}
