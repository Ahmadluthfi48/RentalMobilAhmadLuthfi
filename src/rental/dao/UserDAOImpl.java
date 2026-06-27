package rental.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import rental.model.User;
import rental.util.KoneksiDatabase;

/**
 * Implementasi DAO untuk objek User menggunakan MySQL.
 */
public class UserDAOImpl implements UserDAO {

    @Override
    public void insert(User user) throws SQLException {
        String sql = "INSERT INTO user (username, password, nama_lengkap, level) VALUES (?, ?, ?, ?)";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNamaLengkap());
            ps.setString(4, user.getLevel());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String sql = "UPDATE user SET username = ?, password = ?, nama_lengkap = ?, level = ? WHERE id_user = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNamaLengkap());
            ps.setString(4, user.getLevel());
            ps.setInt(5, user.getIdUser());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int idUser) throws SQLException {
        String sql = "DELETE FROM user WHERE id_user = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            ps.executeUpdate();
        }
    }

    @Override
    public User getById(int idUser) throws SQLException {
        String sql = "SELECT * FROM user WHERE id_user = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUser);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id_user"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nama_lengkap"),
                        rs.getString("level")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public User login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id_user"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nama_lengkap"),
                        rs.getString("level")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() throws SQLException {
        String sql = "SELECT * FROM user ORDER BY id_user DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<User> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(
                    rs.getInt("id_user"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nama_lengkap"),
                    rs.getString("level")
                ));
            }
        }
        return list;
    }

    @Override
    public List<User> search(String query) throws SQLException {
        String sql = "SELECT * FROM user WHERE username LIKE ? OR nama_lengkap LIKE ? ORDER BY id_user DESC";
        Connection conn = KoneksiDatabase.getKoneksi();
        if (conn == null) throw new SQLException("Koneksi database gagal!");
        
        List<User> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String filter = "%" + query + "%";
            ps.setString(1, filter);
            ps.setString(2, filter);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new User(
                        rs.getInt("id_user"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("nama_lengkap"),
                        rs.getString("level")
                    ));
                }
            }
        }
        return list;
    }
}
