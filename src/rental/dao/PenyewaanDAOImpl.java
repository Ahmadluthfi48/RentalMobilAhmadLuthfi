package rental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rental.model.Penyewaan;
import rental.util.KoneksiDatabase;

/**
 * Implementasi DAO untuk objek Penyewaan menggunakan MySQL.
 */
public class PenyewaanDAOImpl implements PenyewaanDAO {

    @Override
    public void insert(Penyewaan p) throws SQLException {
        String sql = "INSERT INTO penyewaan (id_mobil, id_penyewa, tanggal_sewa, tanggal_kembali, durasi_hari, total_biaya, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdMobil());
            ps.setInt(2, p.getIdPenyewa());
            ps.setDate(3, p.getTanggalSewa());
            ps.setDate(4, p.getTanggalKembali());
            ps.setInt(5, p.getDurasiHari());
            ps.setDouble(6, p.getTotalBiaya());
            ps.setString(7, p.getStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Penyewaan p) throws SQLException {
        String sql = "UPDATE penyewaan SET id_mobil = ?, id_penyewa = ?, tanggal_sewa = ?, tanggal_kembali = ?, durasi_hari = ?, total_biaya = ?, status = ? WHERE id_penyewaan = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getIdMobil());
            ps.setInt(2, p.getIdPenyewa());
            ps.setDate(3, p.getTanggalSewa());
            ps.setDate(4, p.getTanggalKembali());
            ps.setInt(5, p.getDurasiHari());
            ps.setDouble(6, p.getTotalBiaya());
            ps.setString(7, p.getStatus());
            ps.setInt(8, p.getIdPenyewaan());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int idPenyewaan) throws SQLException {
        String sql = "DELETE FROM penyewaan WHERE id_penyewaan = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewaan);
            ps.executeUpdate();
        }
    }

    @Override
    public Penyewaan getById(int idPenyewaan) throws SQLException {
        String sql = "SELECT * FROM penyewaan WHERE id_penyewaan = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPenyewaan);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Penyewaan(
                        rs.getInt("id_penyewaan"),
                        rs.getInt("id_mobil"),
                        rs.getInt("id_penyewa"),
                        rs.getDate("tanggal_sewa"),
                        rs.getDate("tanggal_kembali"),
                        rs.getInt("durasi_hari"),
                        rs.getDouble("total_biaya"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Penyewaan> getAll() throws SQLException {
        String sql = "SELECT * FROM penyewaan ORDER BY id_penyewaan DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Penyewaan> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Penyewaan(
                    rs.getInt("id_penyewaan"),
                    rs.getInt("id_mobil"),
                    rs.getInt("id_penyewa"),
                    rs.getDate("tanggal_sewa"),
                    rs.getDate("tanggal_kembali"),
                    rs.getInt("durasi_hari"),
                    rs.getDouble("total_biaya"),
                    rs.getString("status")
                ));
            }
        }
        return list;
    }

    @Override
    public List<Penyewaan> search(String query) throws SQLException {
        String sql = "SELECT * FROM penyewaan WHERE status LIKE ? ORDER BY id_penyewaan DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Penyewaan> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + query + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Penyewaan(
                        rs.getInt("id_penyewaan"),
                        rs.getInt("id_mobil"),
                        rs.getInt("id_penyewa"),
                        rs.getDate("tanggal_sewa"),
                        rs.getDate("tanggal_kembali"),
                        rs.getInt("durasi_hari"),
                        rs.getDouble("total_biaya"),
                        rs.getString("status")
                    ));
                }
            }
        }
        return list;
    }

    @Override
    public void sewaMobil(Penyewaan p) throws SQLException {
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            
            // 1. Insert ke tabel penyewaan
            String sqlInsert = "INSERT INTO penyewaan (id_mobil, id_penyewa, tanggal_sewa, tanggal_kembali, durasi_hari, total_biaya, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement psInsert = conn.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, p.getIdMobil());
                psInsert.setInt(2, p.getIdPenyewa());
                psInsert.setDate(3, p.getTanggalSewa());
                psInsert.setDate(4, p.getTanggalKembali());
                psInsert.setInt(5, p.getDurasiHari());
                psInsert.setDouble(6, p.getTotalBiaya());
                psInsert.setString(7, p.getStatus());
                psInsert.executeUpdate();
            }
            
            // 2. Update status mobil menjadi 'Disewa'
            String sqlUpdateMobil = "UPDATE mobil SET status_mobil = 'Disewa' WHERE id_mobil = ?";
            try (PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateMobil)) {
                psUpdate.setInt(1, p.getIdMobil());
                psUpdate.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    @Override
    public void selesaikanSewa(int idPenyewaan, int idMobil) throws SQLException {
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            
            // 1. Update status transaksi sewa menjadi 'Selesai'
            String sqlUpdateSewa = "UPDATE penyewaan SET status = 'Selesai' WHERE id_penyewaan = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateSewa)) {
                ps.setInt(1, idPenyewaan);
                ps.executeUpdate();
            }
            
            // 2. Update status mobil kembali 'Tersedia'
            String sqlUpdateMobil = "UPDATE mobil SET status_mobil = 'Tersedia' WHERE id_mobil = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateMobil)) {
                ps.setInt(1, idMobil);
                ps.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    @Override
    public void batalkanSewa(int idPenyewaan, int idMobil) throws SQLException {
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        boolean originalAutoCommit = conn.getAutoCommit();
        try {
            conn.setAutoCommit(false);
            
            // 1. Update status transaksi sewa menjadi 'Batal'
            String sqlUpdateSewa = "UPDATE penyewaan SET status = 'Batal' WHERE id_penyewaan = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateSewa)) {
                ps.setInt(1, idPenyewaan);
                ps.executeUpdate();
            }
            
            // 2. Update status mobil kembali 'Tersedia'
            String sqlUpdateMobil = "UPDATE mobil SET status_mobil = 'Tersedia' WHERE id_mobil = ?";
            try (PreparedStatement ps = conn.prepareStatement(sqlUpdateMobil)) {
                ps.setInt(1, idMobil);
                ps.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(originalAutoCommit);
        }
    }

    @Override
    public List<Map<String, Object>> getPenyewaanTableData(String keyword) throws SQLException {
        String sql = "SELECT p.*, m.plat_nomor, m.merk, m.model, py.nama_penyewa " +
                     "FROM penyewaan p " +
                     "JOIN mobil m ON p.id_mobil = m.id_mobil " +
                     "JOIN penyewa py ON p.id_penyewa = py.id_penyewa " +
                     "WHERE py.nama_penyewa LIKE ? OR m.plat_nomor LIKE ? " +
                     "ORDER BY p.id_penyewaan DESC";
                     
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<Map<String, Object>> data = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String filter = "%" + keyword + "%";
            ps.setString(1, filter);
            ps.setString(2, filter);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id_penyewaan", rs.getInt("id_penyewaan"));
                    map.put("plat_nomor", rs.getString("plat_nomor"));
                    map.put("merk", rs.getString("merk"));
                    map.put("model", rs.getString("model"));
                    map.put("nama_penyewa", rs.getString("nama_penyewa"));
                    map.put("tanggal_sewa", rs.getDate("tanggal_sewa"));
                    map.put("tanggal_kembali", rs.getDate("tanggal_kembali"));
                    map.put("durasi_hari", rs.getInt("durasi_hari"));
                    map.put("total_biaya", rs.getBigDecimal("total_biaya"));
                    map.put("status", rs.getString("status"));
                    map.put("id_mobil", rs.getInt("id_mobil"));
                    map.put("id_penyewa", rs.getInt("id_penyewa"));
                    data.add(map);
                }
            }
        }
        return data;
    }

    @Override
    public List<Map<String, Object>> getLaporanData(String filterStatus, String keyword) throws SQLException {
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        StringBuilder sbSql = new StringBuilder("SELECT * FROM v_laporan_penyewaan WHERE (nama_penyewa LIKE ? OR plat_nomor LIKE ? OR merk LIKE ?)");
        if (!"Semua".equals(filterStatus)) {
            sbSql.append(" AND status = ?");
        }
        sbSql.append(" ORDER BY tanggal_sewa DESC");
        
        List<Map<String, Object>> data = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sbSql.toString())) {
            String likeFilter = "%" + keyword + "%";
            ps.setString(1, likeFilter);
            ps.setString(2, likeFilter);
            ps.setString(3, likeFilter);
            
            if (!"Semua".equals(filterStatus)) {
                ps.setString(4, filterStatus);
            }
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id_penyewaan", rs.getInt("id_penyewaan"));
                    map.put("plat_nomor", rs.getString("plat_nomor"));
                    map.put("merk", rs.getString("merk"));
                    map.put("model", rs.getString("model"));
                    map.put("nama_penyewa", rs.getString("nama_penyewa"));
                    map.put("no_hp", rs.getString("no_hp"));
                    map.put("tanggal_sewa", rs.getDate("tanggal_sewa"));
                    map.put("tanggal_kembali", rs.getDate("tanggal_kembali"));
                    map.put("durasi_hari", rs.getInt("durasi_hari"));
                    map.put("total_biaya", rs.getBigDecimal("total_biaya"));
                    map.put("status", rs.getString("status"));
                    data.add(map);
                }
            }
        }
        return data;
    }

    @Override
    public int countActive() throws SQLException {
        String sql = "SELECT COUNT(*) FROM penyewaan WHERE status = 'Berjalan'";
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
