package rental.dao;

import java.sql.SQLException;
import java.util.List;
import rental.model.User;

/**
 * Interface DAO untuk data User.
 */
public interface UserDAO {
    void insert(User user) throws SQLException;
    void update(User user) throws SQLException;
    void delete(int idUser) throws SQLException;
    User getById(int idUser) throws SQLException;
    User login(String username, String password) throws SQLException;
    List<User> getAll() throws SQLException;
    List<User> search(String query) throws SQLException;
}
