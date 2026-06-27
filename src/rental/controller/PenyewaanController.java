package rental.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import rental.dao.PenyewaanDAO;
import rental.dao.PenyewaanDAOImpl;
import rental.model.Penyewaan;

/**
 * Controller untuk mengelola logika transaksi Penyewaan Mobil dan Laporan.
 */
public class PenyewaanController {
    private final PenyewaanDAO penyewaanDAO;

    public PenyewaanController() {
        this.penyewaanDAO = new PenyewaanDAOImpl();
    }

    public void sewaMobil(Penyewaan penyewaan) throws SQLException {
        penyewaanDAO.sewaMobil(penyewaan);
    }

    public void selesaikanSewa(int idPenyewaan, int idMobil) throws SQLException {
        penyewaanDAO.selesaikanSewa(idPenyewaan, idMobil);
    }

    public void batalkanSewa(int idPenyewaan, int idMobil) throws SQLException {
        penyewaanDAO.batalkanSewa(idPenyewaan, idMobil);
    }

    public List<Map<String, Object>> dapatkanPenyewaanTableData(String keyword) throws SQLException {
        return penyewaanDAO.getPenyewaanTableData(keyword);
    }

    public List<Map<String, Object>> dapatkanLaporanData(String filterStatus, String keyword) throws SQLException {
        return penyewaanDAO.getLaporanData(filterStatus, keyword);
    }

    public int hitungSewaBerjalan() throws SQLException {
        return penyewaanDAO.countActive();
    }
}
