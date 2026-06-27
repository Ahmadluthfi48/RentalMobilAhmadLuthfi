package rental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rental.model.Mobil;
import rental.util.KoneksiDatabase;

/**
 * Implementasi DAO untuk objek Mobil menggunakan MySQL.
 */
public class MobilDAOImpl implements MobilDAO {

    @Override
    public void insert(Mobil mobil) throws SQLException {
        String sql = "INSERT INTO mobil (plat_nomor, merk, model, tahun, warna, tarif_harian, status_mobil) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobil.getPlatNomor());
            ps.setString(2, mobil.getMerk());
            ps.setString(3, mobil.getModel());
            ps.setInt(4, mobil.getTahun());
            ps.setString(5, mobil.getWarna());
            ps.setDouble(6, mobil.getTarifHarian());
            ps.setString(7, mobil.getStatusMobil());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Mobil mobil) throws SQLException {
        String sql = "UPDATE mobil SET plat_nomor = ?, merk = ?, model = ?, tahun = ?, warna = ?, tarif_harian = ?, status_mobil = ? WHERE id_mobil = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mobil.getPlatNomor());
            ps.setString(2, mobil.getMerk());
            ps.setString(3, mobil.getModel());
            ps.setInt(4, mobil.getTahun());
            ps.setString(5, mobil.getWarna());
            ps.setDouble(6, mobil.getTarifHarian());
            ps.setString(7, mobil.getStatusMobil());
            ps.setInt(8, mobil.getIdMobil());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int idMobil) throws SQLException {
        String sql = "DELETE FROM mobil WHERE id_mobil = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMobil);
            ps.executeUpdate();
        }
    }

    @Override
    public Mobil getById(int idMobil) throws SQLException {
        String sql = "SELECT * FROM mobil WHERE id_mobil = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idMobil);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Mobil(
                        rs.getInt("id_mobil"),
                        rs.getString("plat_nomor"),
                        rs.getString("merk"),
                        rs.getString("model"),
                        rs.getInt("tahun"),
                        rs.getString("warna"),
                        rs.getDouble("tarif_harian"),
                        rs.getString("status_mobil")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Mobil> getAll() throws SQLException {
        String sql = "SELECT * FROM mobil ORDER BY id_mobil DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Mobil> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Mobil(
                    rs.getInt("id_mobil"),
                    rs.getString("plat_nomor"),
                    rs.getString("merk"),
                    rs.getString("model"),
                    rs.getInt("tahun"),
                    rs.getString("warna"),
                    rs.getDouble("tarif_harian"),
                    rs.getString("status_mobil")
                ));
            }
        }
        return list;
    }

    @Override
    public List<Mobil> search(String query) throws SQLException {
        String sql = "SELECT * FROM mobil WHERE plat_nomor LIKE ? OR merk LIKE ? OR model LIKE ? ORDER BY id_mobil DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Mobil> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String filter = "%" + query + "%";
            ps.setString(1, filter);
            ps.setString(2, filter);
            ps.setString(3, filter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Mobil(
                        rs.getInt("id_mobil"),
                        rs.getString("plat_nomor"),
                        rs.getString("merk"),
                        rs.getString("model"),
                        rs.getInt("tahun"),
                        rs.getString("warna"),
                        rs.getDouble("tarif_harian"),
                        rs.getString("status_mobil")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public List<Mobil> getAvailableCars() throws SQLException {
        String sql = "SELECT * FROM mobil WHERE status_mobil = 'Tersedia' ORDER BY merk, model";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Mobil> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Mobil(
                    rs.getInt("id_mobil"),
                    rs.getString("plat_nomor"),
                    rs.getString("merk"),
                    rs.getString("model"),
                    rs.getInt("tahun"),
                    rs.getString("warna"),
                    rs.getDouble("tarif_harian"),
                    rs.getString("status_mobil")
                ));
            }
        }
        return list;
    }

    @Override
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM mobil";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public int countAvailable() throws SQLException {
        String sql = "SELECT COUNT(*) FROM mobil WHERE status_mobil = 'Tersedia'";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
