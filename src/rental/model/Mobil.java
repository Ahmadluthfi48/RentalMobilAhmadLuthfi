package rental.model;

/**
 * POJO class representing a Mobil (Car).
 */
public class Mobil {
    private int idMobil;
    private String platNomor;
    private String merk;
    private String model;
    private int tahun;
    private String warna;
    private double tarifHarian;
    private String statusMobil;

    public Mobil() {}

    public Mobil(int idMobil, String platNomor, String merk, String model, int tahun, String warna, double tarifHarian, String statusMobil) {
        this.idMobil = idMobil;
        this.platNomor = platNomor;
        this.merk = merk;
        this.model = model;
        this.tahun = tahun;
        this.warna = warna;
        this.tarifHarian = tarifHarian;
        this.statusMobil = statusMobil;
    }

    public int getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(int idMobil) {
        this.idMobil = idMobil;
    }

    public String getPlatNomor() {
        return platNomor;
    }

    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    public String getWarna() {
        return warna;
    }

    public void setWarna(String warna) {
        this.warna = warna;
    }

    public double getTarifHarian() {
        return tarifHarian;
    }

    public void setTarifHarian(double tarifHarian) {
        this.tarifHarian = tarifHarian;
    }

    public String getStatusMobil() {
        return statusMobil;
    }

    public void setStatusMobil(String statusMobil) {
        this.statusMobil = statusMobil;
    }
}
