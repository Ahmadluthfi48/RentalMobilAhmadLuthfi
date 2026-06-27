package rental.dao;

import java.sql.SQLException;
import java.util.List;
import rental.model.Penyewa;

/**
 * Interface DAO untuk data Penyewa (Pelanggan).
 */
public interface PenyewaDAO {
    void insert(Penyewa penyewa) throws SQLException;
    void update(Penyewa penyewa) throws SQLException;
    void delete(int idPenyewa) throws SQLException;
    Penyewa getById(int idPenyewa) throws SQLException;
    List<Penyewa> getAll() throws SQLException;
    List<Penyewa> search(String query) throws SQLException;
    int count() throws SQLException;
}
