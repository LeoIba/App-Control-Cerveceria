package BackEnd.models;

import java.sql.Date;

public class receta {
    private int rCod;
    private String rNombre;
    private String rCodigo;
    private int rVersion;
    private double rBatchSize;
    private double rEficienciaSize;
    private String rAutor;
    private Date rFechaC;
    private int estiloPtCod;
    private int hervidoHCod;
    private int whirpoolWCod;
    private int fermentacionFCod;
    private Integer dryHoppingFDryCod;
    private int carbonatacionCarCod;
    private int maceracionMCod;

    public receta(int rCod, String rNombre, String rCodigo, int rVersion, double rBatchSize, double rEficienciaSize,
            String rAutor, Date rFechaC, int estiloPtCod, int hervidoHCod, int whirpoolWCod, int fermentacionFCod,
            Integer dryHoppingFDryCod, int carbonatacionCarCod, int maceracionMCod) {
        this.rCod = rCod;
        this.rNombre = rNombre;
        this.rCodigo = rCodigo;
        this.rVersion = rVersion;
        this.rBatchSize = rBatchSize;
        this.rEficienciaSize = rEficienciaSize;
        this.rAutor = rAutor;
        this.rFechaC = rFechaC;
        this.estiloPtCod = estiloPtCod;
        this.hervidoHCod = hervidoHCod;
        this.whirpoolWCod = whirpoolWCod;
        this.fermentacionFCod = fermentacionFCod;
        this.dryHoppingFDryCod = dryHoppingFDryCod;
        this.carbonatacionCarCod = carbonatacionCarCod;
        this.maceracionMCod = maceracionMCod;
    }

    public receta(String rNombre, String rCodigo, int rVersion, double rBatchSize, double rEficienciaSize,
            String rAutor, Date rFechaC, int estiloPtCod, int hervidoHCod, int whirpoolWCod, int fermentacionFCod,
            Integer dryHoppingFDryCod, int carbonatacionCarCod, int maceracionMCod) {
        this.rNombre = rNombre;
        this.rCodigo = rCodigo;
        this.rVersion = rVersion;
        this.rBatchSize = rBatchSize;
        this.rEficienciaSize = rEficienciaSize;
        this.rAutor = rAutor;
        this.rFechaC = rFechaC;
        this.estiloPtCod = estiloPtCod;
        this.hervidoHCod = hervidoHCod;
        this.whirpoolWCod = whirpoolWCod;
        this.fermentacionFCod = fermentacionFCod;
        this.dryHoppingFDryCod = dryHoppingFDryCod;
        this.carbonatacionCarCod = carbonatacionCarCod;
        this.maceracionMCod = maceracionMCod;
    }

    public int getrCod() {
        return rCod;
    }

    public void setrCod(int rCod) {
        this.rCod = rCod;
    }

    public String getrNombre() {
        return rNombre;
    }

    public void setrNombre(String rNombre) {
        this.rNombre = rNombre;
    }

    public String getrCodigo() {
        return rCodigo;
    }

    public void setrCodigo(String rCodigo) {
        this.rCodigo = rCodigo;
    }

    public int getrVersion() {
        return rVersion;
    }

    public void setrVersion(int rVersion) {
        this.rVersion = rVersion;
    }

    public double getrBatchSize() {
        return rBatchSize;
    }

    public void setrBatchSize(double rBatchSize) {
        this.rBatchSize = rBatchSize;
    }

    public double getrEficienciaSize() {
        return rEficienciaSize;
    }

    public void setrEficienciaSize(double rEficienciaSize) {
        this.rEficienciaSize = rEficienciaSize;
    }

    public String getrAutor() {
        return rAutor;
    }

    public void setrAutor(String rAutor) {
        this.rAutor = rAutor;
    }

    public Date getrFechaC() {
        return rFechaC;
    }

    public void setrFechaC(Date rFechaC) {
        this.rFechaC = rFechaC;
    }

    public int getEstiloPtCod() {
        return estiloPtCod;
    }

    public void setEstiloPtCod(int estiloPtCod) {
        this.estiloPtCod = estiloPtCod;
    }

    public int getHervidoHCod() {
        return hervidoHCod;
    }

    public void setHervidoHCod(int hervidoHCod) {
        this.hervidoHCod = hervidoHCod;
    }

    public int getWhirpoolWCod() {
        return whirpoolWCod;
    }

    public void setWhirpoolWCod(int whirpoolWCod) {
        this.whirpoolWCod = whirpoolWCod;
    }

    public int getFermentacionFCod() {
        return fermentacionFCod;
    }

    public void setFermentacionFCod(int fermentacionFCod) {
        this.fermentacionFCod = fermentacionFCod;
    }

    public Integer getDryHoppingFDryCod() {
        return dryHoppingFDryCod;
    }

    public void setDryHoppingFDryCod(Integer dryHoppingFDryCod) {
        this.dryHoppingFDryCod = dryHoppingFDryCod;
    }

    public int getCarbonatacionCarCod() {
        return carbonatacionCarCod;
    }

    public void setCarbonatacionCarCod(int carbonatacionCarCod) {
        this.carbonatacionCarCod = carbonatacionCarCod;
    }

    public int getMaceracionMCod() {
        return maceracionMCod;
    }

    public void setMaceracionMCod(int maceracionMCod) {
        this.maceracionMCod = maceracionMCod;
    }
}
