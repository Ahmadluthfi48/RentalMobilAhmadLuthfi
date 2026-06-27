package rental.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import rental.model.Penyewaan;

/**
 * Interface DAO untuk data transaksi Penyewaan.
 */
public interface PenyewaanDAO {
    void insert(Penyewaan penyewaan) throws SQLException;
    void update(Penyewaan penyewaan) throws SQLException;
    void delete(int idPenyewaan) throws SQLException;
    Penyewaan getById(int idPenyewaan) throws SQLException;
    List<Penyewaan> getAll() throws SQLException;
    List<Penyewaan> search(String query) throws SQLException;
    
    // Alur transaksi penyewaan
    void sewaMobil(Penyewaan penyewaan) throws SQLException;
    void selesaikanSewa(int idPenyewaan, int idMobil) throws SQLException;
    void batalkanSewa(int idPenyewaan, int idMobil) throws SQLException;
    
    // Pengambilan data Custom (JOIN) untuk kebutuhan tabel UI
    List<Map<String, Object>> getPenyewaanTableData(String keyword) throws SQLException;
    List<Map<String, Object>> getLaporanData(String filterStatus, String keyword) throws SQLException;
    int countActive() throws SQLException;
}
