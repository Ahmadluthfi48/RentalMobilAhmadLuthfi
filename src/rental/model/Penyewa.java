package rental.model;

/**
 * POJO class representing a Penyewa (Customer).
 */
public class Penyewa {
    private int idPenyewa;
    private String namaPenyewa;
    private String noKtp;
    private String noHp;
    private String alamat;

    public Penyewa() {}

    public Penyewa(int idPenyewa, String namaPenyewa, String noKtp, String noHp, String alamat) {
        this.idPenyewa = idPenyewa;
        this.namaPenyewa = namaPenyewa;
        this.noKtp = noKtp;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    public int getIdPenyewa() {
        return idPenyewa;
    }

    public void setIdPenyewa(int idPenyewa) {
        this.idPenyewa = idPenyewa;
    }

    public String getNamaPenyewa() {
        return namaPenyewa;
    }

    public void setNamaPenyewa(String namaPenyewa) {
        this.namaPenyewa = namaPenyewa;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
