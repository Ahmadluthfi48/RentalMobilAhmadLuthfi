package rental.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import rental.controller.MobilController;
import rental.controller.PenyewaController;
import rental.controller.PenyewaanController;
import rental.model.Mobil;
import rental.model.Penyewa;
import rental.model.Penyewaan;

/**
 * Form Transaksi Penyewaan Mobil.
 * Menggantikan PenyewaanPanel.java dan menggunakan Controller.
 */
public class FormPenyewaan extends JPanel {
    private final MenuUtama menuUtama;
    private final PenyewaanController penyewaanController;
    private final MobilController mobilController;
    private final PenyewaController penyewaController;
    
    // UI Elements
    private JComboBox<CarComboItem> cbMobil;
    private JComboBox<CustomerComboItem> cbPenyewa;
    private JTextField txtTglSewa, txtTglKembali, txtDurasi, txtTotalBiaya, txtSearch;
    private JComboBox<String> cbStatus;
    private JTable tablePenyewaan;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnFinish, btnCancelTrans, btnClear, btnSearch;
    
    // Tracking selected IDs
    private int selectedId = -1;
    private int activeCarId = -1;
    
    // Theme Colors
    private final Color primaryColor = new Color(30, 58, 138);
    private final Color accentColor = new Color(59, 130, 246);
    private final Color bgPanel = Color.WHITE;
    private final Color bgTableHead = new Color(243, 244, 246);
    
    public FormPenyewaan(MenuUtama menuUtama) {
        this.menuUtama = menuUtama;
        this.penyewaanController = new PenyewaanController();
        this.mobilController = new MobilController();
        this.penyewaController = new PenyewaController();
        
        setLayout(new BorderLayout(15, 0));
        setBackground(new Color(243, 244, 246));
        
        // 1. Left Side Form Panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(bgPanel);
        formPanel.setPreferredSize(new Dimension(350, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)), 
            "Form Transaksi Sewa", TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), primaryColor
        ));
        
        int x = 20, y = 25, w = 310, h = 30, dy = 55;
        
        // Mobil Combo
        JLabel lblMobil = new JLabel("Pilih Mobil (Tersedia)");
        lblMobil.setBounds(x, y, w, 20);
        lblMobil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblMobil);
        
        cbMobil = new JComboBox<>();
        cbMobil.setBounds(x, y + 18, w, h);
        cbMobil.setBackground(Color.WHITE);
        formPanel.add(cbMobil);
        
        // Penyewa Combo
        y += dy;
        JLabel lblPenyewa = new JLabel("Pilih Pelanggan / Penyewa");
        lblPenyewa.setBounds(x, y, w, 20);
        lblPenyewa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblPenyewa);
        
        cbPenyewa = new JComboBox<>();
        cbPenyewa.setBounds(x, y + 18, w, h);
        cbPenyewa.setBackground(Color.WHITE);
        formPanel.add(cbPenyewa);
        
        // Tanggal Sewa
        y += dy;
        JLabel lblTglSewa = new JLabel("Tanggal Sewa (YYYY-MM-DD)");
        lblTglSewa.setBounds(x, y, w, 20);
        lblTglSewa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblTglSewa);
        
        txtTglSewa = new JTextField();
        txtTglSewa.setBounds(x, y + 18, w, h);
        txtTglSewa.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtTglSewa);
        
        // Tanggal Kembali
        y += dy;
        JLabel lblTglKembali = new JLabel("Tanggal Kembali (YYYY-MM-DD)");
        lblTglKembali.setBounds(x, y, w, 20);
        lblTglKembali.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblTglKembali);
        
        txtTglKembali = new JTextField();
        txtTglKembali.setBounds(x, y + 18, w, h);
        txtTglKembali.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtTglKembali);
        
        // Durasi (Auto calculated)
        y += dy;
        JLabel lblDurasi = new JLabel("Durasi Sewa (Hari) - Auto");
        lblDurasi.setBounds(x, y, w, 20);
        lblDurasi.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblDurasi);
        
        txtDurasi = new JTextField();
        txtDurasi.setBounds(x, y + 18, w, h);
        txtDurasi.setEditable(false);
        txtDurasi.setBackground(new Color(243, 244, 246));
        txtDurasi.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtDurasi);
        
        // Total Biaya (Auto calculated)
        y += dy;
        JLabel lblTotalBiaya = new JLabel("Total Biaya Sewa (Rp) - Auto");
        lblTotalBiaya.setBounds(x, y, w, 20);
        lblTotalBiaya.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblTotalBiaya);
        
        txtTotalBiaya = new JTextField();
        txtTotalBiaya.setBounds(x, y + 18, w, h);
        txtTotalBiaya.setEditable(false);
        txtTotalBiaya.setBackground(new Color(243, 244, 246));
        txtTotalBiaya.setFont(new Font("Segoe UI", Font.BOLD, 13));
        txtTotalBiaya.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtTotalBiaya);
        
        // Status
        y += dy;
        JLabel lblStatus = new JLabel("Status Transaksi");
        lblStatus.setBounds(x, y, w, 20);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblStatus);
        
        String[] statuses = {"Berjalan", "Selesai", "Batal"};
        cbStatus = new JComboBox<>(statuses);
        cbStatus.setBounds(x, y + 18, w, h);
        cbStatus.setBackground(Color.WHITE);
        cbStatus.setEnabled(false); // Controlled by buttons
        formPanel.add(cbStatus);
        
        // CRUD Buttons
        y += dy + 10;
        btnSave = new JButton("Sewa Mobil");
        btnSave.setBounds(x, y, 150, 35);
        btnSave.setBackground(primaryColor);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        formPanel.add(btnSave);
        
        btnFinish = new JButton("Selesai Sewa");
        btnFinish.setBounds(x + 160, y, 150, 35);
        btnFinish.setBackground(new Color(16, 185, 129)); // Green
        btnFinish.setForeground(Color.WHITE);
        btnFinish.setFocusPainted(false);
        btnFinish.setEnabled(false);
        formPanel.add(btnFinish);
        
        y += 42;
        btnCancelTrans = new JButton("Batalkan Transaksi");
        btnCancelTrans.setBounds(x, y, 150, 35);
        btnCancelTrans.setBackground(new Color(220, 38, 38)); // Red
        btnCancelTrans.setForeground(Color.WHITE);
        btnCancelTrans.setFocusPainted(false);
        btnCancelTrans.setEnabled(false);
        formPanel.add(btnCancelTrans);
        
        btnClear = new JButton("Batal / Reset");
        btnClear.setBounds(x + 160, y, 150, 35);
        btnClear.setBackground(new Color(156, 163, 175)); // Grey
        btnClear.setForeground(Color.WHITE);
        btnClear.setFocusPainted(false);
        formPanel.add(btnClear);
        
        add(formPanel, BorderLayout.WEST);
        
        // 2. Right Side Table Panel
        JPanel rightPanel = new JPanel(new BorderLayout(0, 10));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Search bar
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(Color.WHITE);
        
        JLabel lblSearch = new JLabel("Cari Transaksi (Pelanggan/Plat):");
        lblSearch.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblSearch.setForeground(primaryColor);
        searchPanel.add(lblSearch, BorderLayout.WEST);
        
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(300, 30));
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        
        btnSearch = new JButton("Cari");
        btnSearch.setBackground(primaryColor);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFocusPainted(false);
        searchPanel.add(btnSearch, BorderLayout.EAST);
        
        rightPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Table initialization
        String[] columns = {"ID", "Mobil", "Pelanggan", "Tgl Sewa", "Tgl Kembali", "Durasi (Hari)", "Total Biaya", "Status", "id_mobil", "id_penyewa"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePenyewaan = new JTable(tableModel);
        tablePenyewaan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablePenyewaan.setRowHeight(25);
        tablePenyewaan.setGridColor(new Color(243, 244, 246));
        tablePenyewaan.getTableHeader().setBackground(bgTableHead);
        tablePenyewaan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablePenyewaan.getTableHeader().setForeground(primaryColor);
        
        // Hide internal IDs (id_mobil and id_penyewa column)
        tablePenyewaan.getColumnModel().getColumn(8).setMinWidth(0);
        tablePenyewaan.getColumnModel().getColumn(8).setMaxWidth(0);
        tablePenyewaan.getColumnModel().getColumn(8).setWidth(0);
        tablePenyewaan.getColumnModel().getColumn(9).setMinWidth(0);
        tablePenyewaan.getColumnModel().getColumn(9).setMaxWidth(0);
        tablePenyewaan.getColumnModel().getColumn(9).setWidth(0);
        
        JScrollPane scrollPane = new JScrollPane(tablePenyewaan);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);
        
        // Register events
        initActionListeners();
    }
    
    public void refreshData() {
        populateCombos();
        refreshTable();
        clearForm();
    }
    
    private void populateCombos() {
        cbMobil.removeAllItems();
        cbPenyewa.removeAllItems();
        
        try {
            // 1. Populate Cars (Show available, but if editing we might show the selected car too)
            List<Mobil> cars = mobilController.dapatkanMobilTersedia();
            if (selectedId != -1 && activeCarId != -1) {
                boolean activeCarFound = false;
                for (Mobil m : cars) {
                    if (m.getIdMobil() == activeCarId) {
                        activeCarFound = true;
                        break;
                    }
                }
                if (!activeCarFound) {
                    Mobil activeCar = mobilController.dapatkanMobilBerdasarkanId(activeCarId);
                    if (activeCar != null) {
                        cars.add(activeCar);
                    }
                }
            }
            
            for (Mobil m : cars) {
                String label = m.getPlatNomor() + " - " + m.getMerk() + " " + m.getModel() + " (Rp" + (int)m.getTarifHarian() + "/Hari)";
                cbMobil.addItem(new CarComboItem(m.getIdMobil(), m.getPlatNomor(), label, m.getTarifHarian()));
            }
            
            // 2. Populate Customers
            List<Penyewa> customers = penyewaController.dapatkanSemuaPenyewa();
            for (Penyewa p : customers) {
                String label = p.getNamaPenyewa() + " (KTP: " + p.getNoKtp() + ")";
                cbPenyewa.addItem(new CustomerComboItem(p.getIdPenyewa(), p.getNamaPenyewa(), label));
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat dropdown: " + e.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshTable() {
        refreshTable("");
    }
    
    private void refreshTable(String query) {
        tableModel.setRowCount(0);
        try {
            List<Map<String, Object>> data = penyewaanController.dapatkanPenyewaanTableData(query);
            for (Map<String, Object> map : data) {
                Vector<Object> row = new Vector<>();
                row.add(map.get("id_penyewaan"));
                row.add(map.get("plat_nomor") + " (" + map.get("merk") + " " + map.get("model") + ")");
                row.add(map.get("nama_penyewa"));
                row.add(map.get("tanggal_sewa"));
                row.add(map.get("tanggal_kembali"));
                row.add(map.get("durasi_hari"));
                row.add(map.get("total_biaya"));
                row.add(map.get("status"));
                row.add(map.get("id_mobil"));
                row.add(map.get("id_penyewa"));
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Gagal mengambil data transaksi: " + ex.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hitungBiaya() {
        CarComboItem selectedCar = (CarComboItem) cbMobil.getSelectedItem();
        if (selectedCar == null) {
            txtDurasi.setText("");
            txtTotalBiaya.setText("");
            return;
        }
        
        String tglSewaStr = txtTglSewa.getText().trim();
        String tglKembaliStr = txtTglKembali.getText().trim();
        
        if (tglSewaStr.isEmpty() || tglKembaliStr.isEmpty()) {
            txtDurasi.setText("");
            txtTotalBiaya.setText("");
            return;
        }
        
        try {
            // Validate date format using LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dateSewa = LocalDate.parse(tglSewaStr, formatter);
            LocalDate dateKembali = LocalDate.parse(tglKembaliStr, formatter);
            
            long days = ChronoUnit.DAYS.between(dateSewa, dateKembali);
            
            if (days < 0) {
                txtDurasi.setText("0");
                txtTotalBiaya.setText("0");
                return;
            }
            
            // Standard minimum rental duration is 1 day
            if (days == 0) days = 1;
            
            double tariff = selectedCar.tarif;
            double total = days * tariff;
            
            txtDurasi.setText(String.valueOf(days));
            txtTotalBiaya.setText(String.format("%.0f", total));
            
        } catch (DateTimeParseException ex) {
            txtDurasi.setText("");
            txtTotalBiaya.setText("");
        }
    }
    
    private void clearForm() {
        txtTglSewa.setText(LocalDate.now().toString()); // Default to today's date
        txtTglKembali.setText(LocalDate.now().plusDays(1).toString()); // Default to tomorrow
        txtDurasi.setText("");
        txtTotalBiaya.setText("");
        cbStatus.setSelectedIndex(0);
        cbStatus.setEnabled(false);
        selectedId = -1;
        activeCarId = -1;
        
        btnSave.setEnabled(true);
        btnFinish.setEnabled(false);
        btnCancelTrans.setEnabled(false);
        cbMobil.setEnabled(true);
        cbPenyewa.setEnabled(true);
        txtTglSewa.setEditable(true);
        txtTglKembali.setEditable(true);
        tablePenyewaan.clearSelection();
        
        // Recalculate default
        hitungBiaya();
    }
    
    private boolean validateDates() {
        String tglSewaStr = txtTglSewa.getText().trim();
        String tglKembaliStr = txtTglKembali.getText().trim();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date sewa = sdf.parse(tglSewaStr);
            Date kembali = sdf.parse(tglKembaliStr);
            if (kembali.before(sewa)) {
                JOptionPane.showMessageDialog(this, 
                    "Tanggal kembali tidak boleh sebelum tanggal sewa!", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, 
                "Format tanggal salah! Gunakan format YYYY-MM-DD (Contoh: 2026-06-26)", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void initActionListeners() {
        // Dynamic calculations
        FocusAdapter calcFocus = new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                hitungBiaya();
            }
        };
        txtTglSewa.addFocusListener(calcFocus);
        txtTglKembali.addFocusListener(calcFocus);
        
        cbMobil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitungBiaya();
            }
        });
        
        // Save (Rent Car)
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sewaMobil();
            }
        });
        
        // Finish (Selesai Sewa)
        btnFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selesaikanSewa();
            }
        });
        
        // Cancel (Batalkan Transaksi)
        btnCancelTrans.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                batalkanSewa();
            }
        });
        
        // Reset
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
        // Search Click
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(txtSearch.getText().trim());
            }
        });
        
        // Search Type
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                refreshTable(txtSearch.getText().trim());
            }
        });
        
        // Table row click
        tablePenyewaan.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablePenyewaan.getSelectedRow();
                if (row != -1) {
                    selectedId = Integer.parseInt(tablePenyewaan.getValueAt(row, 0).toString());
                    String status = tablePenyewaan.getValueAt(row, 7).toString();
                    activeCarId = Integer.parseInt(tablePenyewaan.getValueAt(row, 8).toString());
                    int customerId = Integer.parseInt(tablePenyewaan.getValueAt(row, 9).toString());
                    
                    // Reload dropdown containing this specific active car
                    populateCombos();
                    
                    // Match dropdown selectors
                    for (int i = 0; i < cbMobil.getItemCount(); i++) {
                        if (cbMobil.getItemAt(i).id == activeCarId) {
                            cbMobil.setSelectedIndex(i);
                            break;
                        }
                    }
                    for (int i = 0; i < cbPenyewa.getItemCount(); i++) {
                        if (cbPenyewa.getItemAt(i).id == customerId) {
                            cbPenyewa.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    txtTglSewa.setText(tablePenyewaan.getValueAt(row, 3).toString());
                    txtTglKembali.setText(tablePenyewaan.getValueAt(row, 4).toString());
                    txtDurasi.setText(tablePenyewaan.getValueAt(row, 5).toString());
                    txtTotalBiaya.setText(tablePenyewaan.getValueAt(row, 6).toString().replace(".00", ""));
                    cbStatus.setSelectedItem(status);
                    
                    // Mode locks
                    btnSave.setEnabled(false);
                    cbMobil.setEnabled(false);
                    cbPenyewa.setEnabled(false);
                    txtTglSewa.setEditable(false);
                    txtTglKembali.setEditable(false);
                    
                    if ("Berjalan".equals(status)) {
                        btnFinish.setEnabled(true);
                        btnCancelTrans.setEnabled(true);
                    } else {
                        btnFinish.setEnabled(false);
                        btnCancelTrans.setEnabled(false);
                    }
                }
            }
        });
    }
    
    private void sewaMobil() {
        CarComboItem selectedCar = (CarComboItem) cbMobil.getSelectedItem();
        CustomerComboItem selectedCust = (CustomerComboItem) cbPenyewa.getSelectedItem();
        
        if (selectedCar == null || selectedCust == null) {
            JOptionPane.showMessageDialog(this, "Harap pilih mobil dan pelanggan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!validateDates()) return;
        hitungBiaya(); // Ensure calculation is done
        
        if (txtDurasi.getText().isEmpty() || "0".equals(txtDurasi.getText())) {
            JOptionPane.showMessageDialog(this, "Tanggal rental tidak valid!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            Penyewaan p = new Penyewaan();
            p.setIdMobil(selectedCar.id);
            p.setIdPenyewa(selectedCust.id);
            p.setTanggalSewa(java.sql.Date.valueOf(txtTglSewa.getText().trim()));
            p.setTanggalKembali(java.sql.Date.valueOf(txtTglKembali.getText().trim()));
            p.setDurasiHari(Integer.parseInt(txtDurasi.getText()));
            p.setTotalBiaya(Double.parseDouble(txtTotalBiaya.getText()));
            p.setStatus("Berjalan");
            
            penyewaanController.sewaMobil(p);
            JOptionPane.showMessageDialog(this, "Mobil berhasil disewakan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refreshData();
            menuUtama.refreshDashboard();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Transaksi gagal disimpan: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void selesaikanSewa() {
        if (selectedId == -1 || activeCarId == -1) return;
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin penyewaan ini sudah selesai dan mobil telah dikembalikan?", 
            "Konfirmasi Pengembalian", JOptionPane.YES_NO_OPTION);
            
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try {
            penyewaanController.selesaikanSewa(selectedId, activeCarId);
            JOptionPane.showMessageDialog(this, "Pengembalian mobil sukses! Transaksi Selesai.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refreshData();
            menuUtama.refreshDashboard();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memproses pengembalian: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void batalkanSewa() {
        if (selectedId == -1 || activeCarId == -1) return;
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin membatalkan transaksi penyewaan ini?", 
            "Konfirmasi Pembatalan", JOptionPane.YES_NO_OPTION);
            
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try {
            penyewaanController.batalkanSewa(selectedId, activeCarId);
            JOptionPane.showMessageDialog(this, "Transaksi berhasil dibatalkan! Mobil kembali tersedia.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refreshData();
            menuUtama.refreshDashboard();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal membatalkan transaksi: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Combobox helper classes
    private static class CarComboItem {
        int id;
        String plat;
        String label;
        double tarif;
        
        CarComboItem(int id, String plat, String label, double tarif) {
            this.id = id;
            this.plat = plat;
            this.label = label;
            this.tarif = tarif;
        }
        
        @Override
        public String toString() {
            return label;
        }
    }
    
    private static class CustomerComboItem {
        int id;
        String nama;
        String label;
        
        CustomerComboItem(int id, String nama, String label) {
            this.id = id;
            this.nama = nama;
            this.label = label;
        }
        
        @Override
        public String toString() {
            return label;
        }
    }
}
