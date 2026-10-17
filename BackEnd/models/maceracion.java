package BackEnd.models;

public class maceracion {
    private int mCod;
    private double mRelacionAguaGrano;

    public maceracion(int mCod, double mRelacionAguaGrano) {
        this.mCod = mCod;
        this.mRelacionAguaGrano = mRelacionAguaGrano;
    }

    public maceracion(double mRelacionAguaGrano) {
        this.mRelacionAguaGrano = mRelacionAguaGrano;
    }

    public int getmCod() {
        return mCod;
    }

    public void setmCod(int mCod) {
        this.mCod = mCod;
    }

    public double getmRelacionAguaGrano() {
        return mRelacionAguaGrano;
    }

    public void setmRelacionAguaGrano(double mRelacionAguaGrano) {
        this.mRelacionAguaGrano = mRelacionAguaGrano;
    }
}
