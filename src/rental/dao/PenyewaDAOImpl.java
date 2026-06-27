package rental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rental.model.Penyewa;
import rental.util.KoneksiDatabase;

/**
 * Implementasi DAO untuk objek Penyewa menggunakan MySQL.
 */
public class PenyewaDAOImpl implements PenyewaDAO {

    @Override
    public void insert(Penyewa penyewa) throws SQLException {
        String sql = "INSERT INTO penyewa (nama_penyewa, no_ktp, no_hp, alamat) VALUES (?, ?, ?, ?)";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, penyewa.getNamaPenyewa());
            ps.setString(2, penyewa.getNoKtp());
            ps.setString(3, penyewa.getNoHp());
            ps.setString(4, penyewa.getAlamat());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Penyewa penyewa) throws SQLException {
        String sql = "UPDATE penyewa SET nama_penyewa = ?, no_ktp = ?, no_hp = ?, alamat = ? WHERE id_penyewa = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, penyewa.getNamaPenyewa());
            ps.setString(2, penyewa.getNoKtp());
            ps.setString(3, penyewa.getNoHp());
            ps.setString(4, penyewa.getAlamat());
            ps.setInt(5, penyewa.getIdPenyewa());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int idPenyewa) throws SQLException {
        String sql = "DELETE FROM penyewa WHERE id_penyewa = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewa);
            ps.executeUpdate();
        }
    }

    @Override
    public Penyewa getById(int idPenyewa) throws SQLException {
        String sql = "SELECT * FROM penyewa WHERE id_penyewa = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Penyewa(
                        rs.getInt("id_penyewa"),
                        rs.getString("nama_penyewa"),
                        rs.getString("no_ktp"),
                        rs.getString("no_hp"),
                        rs.getString("alamat")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Penyewa> getAll() throws SQLException {
        String sql = "SELECT * FROM penyewa ORDER BY id_penyewa DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Penyewa> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Penyewa(
                    rs.getInt("id_penyewa"),
                    rs.getString("nama_penyewa"),
                    rs.getString("no_ktp"),
                    rs.getString("no_hp"),
                    rs.getString("alamat")
                ));
            }
        }
        return list;
    }

    @Override
    public List<Penyewa> search(String query) throws SQLException {
        String sql = "SELECT * FROM penyewa WHERE nama_penyewa LIKE ? OR no_ktp LIKE ? ORDER BY id_penyewa DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Penyewa> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String filter = "%" + query + "%";
            ps.setString(1, filter);
            ps.setString(2, filter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Penyewa(
                        rs.getInt("id_penyewa"),
                        rs.getString("nama_penyewa"),
                        rs.getString("no_ktp"),
                        rs.getString("no_hp"),
                        rs.getString("alamat")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM penyewa";
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
