package rental.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Kelas KoneksiDatabase untuk menghubungkan Java Swing ke MySQL Database.
 * Menggunakan Driver MySQL Connector/J versi 8.x (com.mysql.cj.jdbc.Driver).
 */
public class KoneksiDatabase {
    private static Connection koneksi;

    // Konfigurasi koneksi database
    private static final String DB_NAME = "db_rentalmobil_231011401058";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME
                                    + "?useSSL=false"
                                    + "&allowPublicKeyRetrieval=true"
                                    + "&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Isi jika MySQL Anda memiliki password

    public static Connection getKoneksi() {
        if (koneksi == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                koneksi = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Koneksi Database Berhasil terhubung ke " + DB_NAME + "!");
            } catch (ClassNotFoundException e) {
                System.err.println("Driver JDBC MySQL tidak ditemukan!");
                JOptionPane.showMessageDialog(null,
                    "Driver JDBC MySQL tidak ditemukan!\n"
                    + "Pastikan library mysql-connector-j sudah ada di folder lib.",
                    "Error Driver", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException e) {
                System.err.println("Koneksi ke database gagal: " + e.getMessage());
                JOptionPane.showMessageDialog(null,
                    "Gagal menghubungkan ke database " + DB_NAME + "!\n"
                    + "Pastikan MySQL Server sudah berjalan, port 3306 terbuka, dan database telah dibuat.\n"
                    + "Detail Error: " + e.getMessage(),
                    "Koneksi Gagal", JOptionPane.ERROR_MESSAGE);
            }
        }
        return koneksi;
    }

    public static void tutupKoneksi() {
        if (koneksi != null) {
            try {
                koneksi.close();
                koneksi = null;
                System.out.println("Koneksi database ditutup.");
            } catch (SQLException e) {
                System.err.println("Gagal menutup koneksi database: " + e.getMessage());
            }
        }
    }
}
