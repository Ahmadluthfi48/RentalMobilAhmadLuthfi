package rental.controller;

import java.sql.SQLException;
import java.util.List;
import rental.dao.UserDAO;
import rental.dao.UserDAOImpl;
import rental.model.User;

/**
 * Controller untuk mengelola logika data User.
 */
public class UserController {
    private final UserDAO userDAO;

    public UserController() {
        this.userDAO = new UserDAOImpl();
    }

    public void tambahUser(User user) throws SQLException {
        userDAO.insert(user);
    }

    public void ubahUser(User user) throws SQLException {
        userDAO.update(user);
    }

    public void hapusUser(int idUser) throws SQLException {
        userDAO.delete(idUser);
    }

    public User dapatkanUserBerdasarkanId(int idUser) throws SQLException {
        return userDAO.getById(idUser);
    }

    public User login(String username, String password) throws SQLException {
        return userDAO.login(username, password);
    }

    public List<User> dapatkanSemuaUser() throws SQLException {
        return userDAO.getAll();
    }

    public List<User> cariUser(String query) throws SQLException {
        return userDAO.search(query);
    }
}
