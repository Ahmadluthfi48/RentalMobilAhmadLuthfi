package rental.dao;

import java.sql.SQLException;
import java.util.List;
import rental.model.Mobil;

/**
 * Interface DAO untuk data Mobil.
 */
public interface MobilDAO {
    void insert(Mobil mobil) throws SQLException;
    void update(Mobil mobil) throws SQLException;
    void delete(int idMobil) throws SQLException;
    Mobil getById(int idMobil) throws SQLException;
    List<Mobil> getAll() throws SQLException;
    List<Mobil> search(String query) throws SQLException;
    List<Mobil> getAvailableCars() throws SQLException;
    int count() throws SQLException;
    int countAvailable() throws SQLException;
}
