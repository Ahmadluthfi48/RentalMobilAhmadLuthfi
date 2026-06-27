package rental.controller;

import java.sql.SQLException;
import java.util.List;
import rental.dao.MobilDAO;
import rental.dao.MobilDAOImpl;
import rental.model.Mobil;

/**
 * Controller untuk mengelola logika data Mobil.
 */
public class MobilController {
    private final MobilDAO mobilDAO;

    public MobilController() {
        this.mobilDAO = new MobilDAOImpl();
    }

    public void tambahMobil(Mobil mobil) throws SQLException {
        mobilDAO.insert(mobil);
    }

    public void ubahMobil(Mobil mobil) throws SQLException {
        mobilDAO.update(mobil);
    }

    public void hapusMobil(int idMobil) throws SQLException {
        mobilDAO.delete(idMobil);
    }

    public Mobil dapatkanMobilBerdasarkanId(int idMobil) throws SQLException {
        return mobilDAO.getById(idMobil);
    }

    public List<Mobil> dapatkanSemuaMobil() throws SQLException {
        return mobilDAO.getAll();
    }

    public List<Mobil> cariMobil(String query) throws SQLException {
        return mobilDAO.search(query);
    }

    public List<Mobil> dapatkanMobilTersedia() throws SQLException {
        return mobilDAO.getAvailableCars();
    }

    public int hitungTotalMobil() throws SQLException {
        return mobilDAO.count();
    }

    public int hitungMobilTersedia() throws SQLException {
        return mobilDAO.countAvailable();
    }
}
