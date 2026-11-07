package BackEnd.models;

import java.sql.Date;

public class lote {
    private int lotCod;
    private double lotVolumenTotal;
    private Date lotFechaVecimiento;
    private Date fechaMaceracion;
    private double cantidadMaceracion;
    private Date fechaHervido;
    private double cantidadHervido;
    private Date fechaWhirpool;
    private double cantidadWhirpool;
    private Date fechaFermentacion;
    private double cantidadFermentacion;
    private Date fechaDryhopping;
    private double cantidadDryhopping;
    private Date fechaCarbonatacion;
    private double cantidadCarbonatacion;
    private Date lotFechaFinPreparado;
    private int recetasRCod;

    public lote(int lotCod, double lotVolumenTotal, Date lotFechaVecimiento, Date fechaMaceracion,
            double cantidadMaceracion, Date fechaHervido, double cantidadHervido, Date fechaWhirpool,
            double cantidadWhirpool, Date fechaFermentacion, double cantidadFermentacion, Date fechaDryhopping,
            double cantidadDryhopping, Date fechaCarbonatacion, double cantidadCarbonatacion, Date lotFechaFinPreparado,
            int recetasRCod) {
        this.lotCod = lotCod;
        this.lotVolumenTotal = lotVolumenTotal;
        this.lotFechaVecimiento = lotFechaVecimiento;
        this.fechaMaceracion = fechaMaceracion;
        this.cantidadMaceracion = cantidadMaceracion;
        this.fechaHervido = fechaHervido;
        this.cantidadHervido = cantidadHervido;
        this.fechaWhirpool = fechaWhirpool;
        this.cantidadWhirpool = cantidadWhirpool;
        this.fechaFermentacion = fechaFermentacion;
        this.cantidadFermentacion = cantidadFermentacion;
        this.fechaDryhopping = fechaDryhopping;
        this.cantidadDryhopping = cantidadDryhopping;
        this.fechaCarbonatacion = fechaCarbonatacion;
        this.cantidadCarbonatacion = cantidadCarbonatacion;
        this.lotFechaFinPreparado = lotFechaFinPreparado;
        this.recetasRCod = recetasRCod;
    }

    public lote(double lotVolumenTotal, Date lotFechaVecimiento, Date fechaMaceracion, double cantidadMaceracion,
            Date fechaHervido, double cantidadHervido, Date fechaWhirpool, double cantidadWhirpool,
            Date fechaFermentacion, double cantidadFermentacion, Date fechaDryhopping, double cantidadDryhopping,
            Date fechaCarbonatacion, double cantidadCarbonatacion, Date lotFechaFinPreparado, int recetasRCod) {
        this.lotVolumenTotal = lotVolumenTotal;
        this.lotFechaVecimiento = lotFechaVecimiento;
        this.fechaMaceracion = fechaMaceracion;
        this.cantidadMaceracion = cantidadMaceracion;
        this.fechaHervido = fechaHervido;
        this.cantidadHervido = cantidadHervido;
        this.fechaWhirpool = fechaWhirpool;
        this.cantidadWhirpool = cantidadWhirpool;
        this.fechaFermentacion = fechaFermentacion;
        this.cantidadFermentacion = cantidadFermentacion;
        this.fechaDryhopping = fechaDryhopping;
        this.cantidadDryhopping = cantidadDryhopping;
        this.fechaCarbonatacion = fechaCarbonatacion;
        this.cantidadCarbonatacion = cantidadCarbonatacion;
        this.lotFechaFinPreparado = lotFechaFinPreparado;
        this.recetasRCod = recetasRCod;
    }

    public int getLotCod() {
        return lotCod;
    }

    public void setLotCod(int lotCod) {
        this.lotCod = lotCod;
    }

    public double getLotVolumenTotal() {
        return lotVolumenTotal;
    }

    public void setLotVolumenTotal(double lotVolumenTotal) {
        this.lotVolumenTotal = lotVolumenTotal;
    }

    public Date getLotFechaVecimiento() {
        return lotFechaVecimiento;
    }

    public void setLotFechaVecimiento(Date lotFechaVecimiento) {
        this.lotFechaVecimiento = lotFechaVecimiento;
    }

    public Date getFechaMaceracion() {
        return fechaMaceracion;
    }

    public void setFechaMaceracion(Date fechaMaceracion) {
        this.fechaMaceracion = fechaMaceracion;
    }

    public double getCantidadMaceracion() {
        return cantidadMaceracion;
    }

    public void setCantidadMaceracion(double cantidadMaceracion) {
        this.cantidadMaceracion = cantidadMaceracion;
    }

    public Date getFechaHervido() {
        return fechaHervido;
    }

    public void setFechaHervido(Date fechaHervido) {
        this.fechaHervido = fechaHervido;
    }

    public double getCantidadHervido() {
        return cantidadHervido;
    }

    public void setCantidadHervido(double cantidadHervido) {
        this.cantidadHervido = cantidadHervido;
    }

    public Date getFechaWhirpool() {
        return fechaWhirpool;
    }

    public void setFechaWhirpool(Date fechaWhirpool) {
        this.fechaWhirpool = fechaWhirpool;
    }

    public double getCantidadWhirpool() {
        return cantidadWhirpool;
    }

    public void setCantidadWhirpool(double cantidadWhirpool) {
        this.cantidadWhirpool = cantidadWhirpool;
    }

    public Date getFechaFermentacion() {
        return fechaFermentacion;
    }

    public void setFechaFermentacion(Date fechaFermentacion) {
        this.fechaFermentacion = fechaFermentacion;
    }

    public double getCantidadFermentacion() {
        return cantidadFermentacion;
    }

    public void setCantidadFermentacion(double cantidadFermentacion) {
        this.cantidadFermentacion = cantidadFermentacion;
    }

    public Date getFechaDryhopping() {
        return fechaDryhopping;
    }

    public void setFechaDryhopping(Date fechaDryhopping) {
        this.fechaDryhopping = fechaDryhopping;
    }

    public double getCantidadDryhopping() {
        return cantidadDryhopping;
    }

    public void setCantidadDryhopping(double cantidadDryhopping) {
        this.cantidadDryhopping = cantidadDryhopping;
    }

    public Date getFechaCarbonatacion() {
        return fechaCarbonatacion;
    }

    public void setFechaCarbonatacion(Date fechaCarbonatacion) {
        this.fechaCarbonatacion = fechaCarbonatacion;
    }

    public double getCantidadCarbonatacion() {
        return cantidadCarbonatacion;
    }

    public void setCantidadCarbonatacion(double cantidadCarbonatacion) {
        this.cantidadCarbonatacion = cantidadCarbonatacion;
    }

    public Date getLotFechaFinPreparado() {
        return lotFechaFinPreparado;
    }

    public void setLotFechaFinPreparado(Date lotFechaFinPreparado) {
        this.lotFechaFinPreparado = lotFechaFinPreparado;
    }

    public int getRecetasRCod() {
        return recetasRCod;
    }

    public void setRecetasRCod(int recetasRCod) {
        this.recetasRCod = recetasRCod;
    }
}
