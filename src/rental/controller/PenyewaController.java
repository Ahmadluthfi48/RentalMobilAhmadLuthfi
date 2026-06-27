package rental.controller;

import java.sql.SQLException;
import java.util.List;
import rental.dao.PenyewaDAO;
import rental.dao.PenyewaDAOImpl;
import rental.model.Penyewa;

/**
 * Controller untuk mengelola logika data Penyewa.
 */
public class PenyewaController {
    private final PenyewaDAO penyewaDAO;

    public PenyewaController() {
        this.penyewaDAO = new PenyewaDAOImpl();
    }

    public void tambahPenyewa(Penyewa penyewa) throws SQLException {
        penyewaDAO.insert(penyewa);
    }

    public void ubahPenyewa(Penyewa penyewa) throws SQLException {
        penyewaDAO.update(penyewa);
    }

    public void hapusPenyewa(int idPenyewa) throws SQLException {
        penyewaDAO.delete(idPenyewa);
    }

    public Penyewa dapatkanPenyewaBerdasarkanId(int idPenyewa) throws SQLException {
        return penyewaDAO.getById(idPenyewa);
    }

    public List<Penyewa> dapatkanSemuaPenyewa() throws SQLException {
        return penyewaDAO.getAll();
    }

    public List<Penyewa> cariPenyewa(String query) throws SQLException {
        return penyewaDAO.search(query);
    }

    public int hitungTotalPenyewa() throws SQLException {
        return penyewaDAO.count();
    }
}
