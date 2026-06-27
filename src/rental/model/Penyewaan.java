package rental.model;

import java.sql.Date;

/**
 * POJO class representing a Penyewaan (Rental Transaction).
 */
public class Penyewaan {
    private int idPenyewaan;
    private int idMobil;
    private int idPenyewa;
    private Date tanggalSewa;
    private Date tanggalKembali;
    private int durasiHari;
    private double totalBiaya;
    private String status;

    public Penyewaan() {}

    public Penyewaan(int idPenyewaan, int idMobil, int idPenyewa, Date tanggalSewa, Date tanggalKembali, int durasiHari, double totalBiaya, String status) {
        this.idPenyewaan = idPenyewaan;
        this.idMobil = idMobil;
        this.idPenyewa = idPenyewa;
        this.tanggalSewa = tanggalSewa;
        this.tanggalKembali = tanggalKembali;
        this.durasiHari = durasiHari;
        this.totalBiaya = totalBiaya;
        this.status = status;
    }

    public int getIdPenyewaan() {
        return idPenyewaan;
    }

    public void setIdPenyewaan(int idPenyewaan) {
        this.idPenyewaan = idPenyewaan;
    }

    public int getIdMobil() {
        return idMobil;
    }

    public void setIdMobil(int idMobil) {
        this.idMobil = idMobil;
    }

    public int getIdPenyewa() {
        return idPenyewa;
    }

    public void setIdPenyewa(int idPenyewa) {
        this.idPenyewa = idPenyewa;
    }

    public Date getTanggalSewa() {
        return tanggalSewa;
    }

    public void setTanggalSewa(Date tanggalSewa) {
        this.tanggalSewa = tanggalSewa;
    }

    public Date getTanggalKembali() {
        return tanggalKembali;
    }

    public void setTanggalKembali(Date tanggalKembali) {
        this.tanggalKembali = tanggalKembali;
    }

    public int getDurasiHari() {
        return durasiHari;
    }

    public void setDurasiHari(int durasiHari) {
        this.durasiHari = durasiHari;
    }

    public double getTotalBiaya() {
        return totalBiaya;
    }

    public void setTotalBiaya(double totalBiaya) {
        this.totalBiaya = totalBiaya;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
