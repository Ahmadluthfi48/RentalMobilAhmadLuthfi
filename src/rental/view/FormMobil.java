package rental.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import rental.controller.MobilController;
import rental.model.Mobil;

/**
 * Form CRUD Mobil.
 * Menggantikan MobilPanel.java dan memindahkan query ke Controller.
 */
public class FormMobil extends JPanel {
    private final MenuUtama menuUtama;
    private final MobilController mobilController;
    
    // UI Elements
    private JTextField txtPlat, txtMerk, txtModel, txtTahun, txtWarna, txtTarif, txtSearch;
    private JComboBox<String> cbStatus;
    private JTable tableMobil;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnEdit, btnDelete, btnClear, btnSearch;
    
    // Tracking selected ID
    private int selectedId = -1;
    
    // Theme Colors
    private final Color primaryColor = new Color(30, 58, 138);
    private final Color accentColor = new Color(59, 130, 246);
    private final Color bgPanel = Color.WHITE;
    private final Color bgTableHead = new Color(243, 244, 246);
    
    public FormMobil(MenuUtama menuUtama) {
        this.menuUtama = menuUtama;
        this.mobilController = new MobilController();
        
        setLayout(new BorderLayout(15, 0));
        setBackground(new Color(243, 244, 246));
        
        // 1. Left Side Form Panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(bgPanel);
        formPanel.setPreferredSize(new Dimension(340, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)), 
            "Form Mobil", TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), primaryColor
        ));
        
        // Form Controls layout manually
        int x = 20, y = 30, w = 300, h = 30, dy = 60;
        
        JLabel lblPlat = new JLabel("Plat Nomor");
        lblPlat.setBounds(x, y, w, 20);
        lblPlat.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblPlat);
        
        txtPlat = new JTextField();
        txtPlat.setBounds(x, y + 20, w, h);
        txtPlat.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtPlat);
        
        y += dy;
        JLabel lblMerk = new JLabel("Merk Mobil (contoh: Toyota, Honda)");
        lblMerk.setBounds(x, y, w, 20);
        lblMerk.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblMerk);
        
        txtMerk = new JTextField();
        txtMerk.setBounds(x, y + 20, w, h);
        txtMerk.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtMerk);
        
        y += dy;
        JLabel lblModel = new JLabel("Model (contoh: Avanza, Brio)");
        lblModel.setBounds(x, y, w, 20);
        lblModel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblModel);
        
        txtModel = new JTextField();
        txtModel.setBounds(x, y + 20, w, h);
        txtModel.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtModel);
        
        y += dy;
        JLabel lblTahun = new JLabel("Tahun Pembuatan");
        lblTahun.setBounds(x, y, w, 20);
        lblTahun.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblTahun);
        
        txtTahun = new JTextField();
        txtTahun.setBounds(x, y + 20, w, h);
        txtTahun.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtTahun);
        
        y += dy;
        JLabel lblWarna = new JLabel("Warna");
        lblWarna.setBounds(x, y, w, 20);
        lblWarna.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblWarna);
        
        txtWarna = new JTextField();
        txtWarna.setBounds(x, y + 20, w, h);
        txtWarna.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtWarna);
        
        y += dy;
        JLabel lblTarif = new JLabel("Tarif Harian (Rp)");
        lblTarif.setBounds(x, y, w, 20);
        lblTarif.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblTarif);
        
        txtTarif = new JTextField();
        txtTarif.setBounds(x, y + 20, w, h);
        txtTarif.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtTarif);
        
        y += dy;
        JLabel lblStatus = new JLabel("Status Kendaraan");
        lblStatus.setBounds(x, y, w, 20);
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblStatus);
        
        String[] statuses = {"Tersedia", "Disewa", "Servis"};
        cbStatus = new JComboBox<>(statuses);
        cbStatus.setBounds(x, y + 20, w, h);
        cbStatus.setBackground(Color.WHITE);
        formPanel.add(cbStatus);
        
        // Form CRUD Buttons
        y += dy + 10;
        btnSave = new JButton("Simpan");
        btnSave.setBounds(x, y, 140, 35);
        btnSave.setBackground(primaryColor);
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        formPanel.add(btnSave);
        
        btnEdit = new JButton("Ubah");
        btnEdit.setBounds(x + 160, y, 140, 35);
        btnEdit.setBackground(accentColor);
        btnEdit.setForeground(Color.WHITE);
        btnEdit.setFocusPainted(false);
        btnEdit.setEnabled(false); // Enable only when row selected
        formPanel.add(btnEdit);
        
        y += 45;
        btnDelete = new JButton("Hapus");
        btnDelete.setBounds(x, y, 140, 35);
        btnDelete.setBackground(new Color(220, 38, 38)); // Red
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFocusPainted(false);
        btnDelete.setEnabled(false);
        formPanel.add(btnDelete);
        
        btnClear = new JButton("Batal");
        btnClear.setBounds(x + 160, y, 140, 35);
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
        
        JLabel lblSearch = new JLabel("Cari Mobil:");
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
        String[] columns = {"ID", "Plat Nomor", "Merk", "Model", "Tahun", "Warna", "Tarif Harian", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // read-only cells
            }
        };
        tableMobil = new JTable(tableModel);
        tableMobil.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableMobil.setRowHeight(25);
        tableMobil.setGridColor(new Color(243, 244, 246));
        tableMobil.getTableHeader().setBackground(bgTableHead);
        tableMobil.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableMobil.getTableHeader().setForeground(primaryColor);
        
        JScrollPane scrollPane = new JScrollPane(tableMobil);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);
        
        // Add event handlers
        initActionListeners();
    }
    
    private void initActionListeners() {
        // Save Operation
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahMobil();
            }
        });
        
        // Edit Operation
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahMobil();
            }
        });
        
        // Delete Operation
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusMobil();
            }
        });
        
        // Clear/Cancel
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        
        // Search Button
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(txtSearch.getText().trim());
            }
        });
        
        // Search typing
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                refreshTable(txtSearch.getText().trim());
            }
        });
        
        // Table row click
        tableMobil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableMobil.getSelectedRow();
                if (row != -1) {
                    selectedId = Integer.parseInt(tableMobil.getValueAt(row, 0).toString());
                    txtPlat.setText(tableMobil.getValueAt(row, 1).toString());
                    txtMerk.setText(tableMobil.getValueAt(row, 2).toString());
                    txtModel.setText(tableMobil.getValueAt(row, 3).toString());
                    txtTahun.setText(tableMobil.getValueAt(row, 4).toString());
                    txtWarna.setText(tableMobil.getValueAt(row, 5).toString());
                    
                    // Clean price text
                    String price = tableMobil.getValueAt(row, 6).toString();
                    txtTarif.setText(price.replace(",", "").replace(".00", ""));
                    
                    cbStatus.setSelectedItem(tableMobil.getValueAt(row, 7).toString());
                    
                    // Toggle Buttons
                    btnSave.setEnabled(false);
                    btnEdit.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });
    }
    
    private boolean validateForm() {
        if (txtPlat.getText().trim().isEmpty() ||
            txtMerk.getText().trim().isEmpty() ||
            txtModel.getText().trim().isEmpty() ||
            txtTahun.getText().trim().isEmpty() ||
            txtTarif.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, 
                "Harap isi semua kolom wajib (Plat, Merk, Model, Tahun, Tarif)!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        try {
            Integer.parseInt(txtTahun.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Tahun pembuatan harus berupa angka valid!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        try {
            double price = Double.parseDouble(txtTarif.getText().trim());
            if (price < 0) {
                JOptionPane.showMessageDialog(this, 
                    "Tarif harian tidak boleh negatif!", 
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Tarif harian harus berupa angka valid!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void clearForm() {
        txtPlat.setText("");
        txtMerk.setText("");
        txtModel.setText("");
        txtTahun.setText("");
        txtWarna.setText("");
        txtTarif.setText("");
        cbStatus.setSelectedIndex(0);
        selectedId = -1;
        
        btnSave.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        tableMobil.clearSelection();
    }
    
    public void refreshTable() {
        refreshTable("");
    }
    
    public void refreshTable(String query) {
        tableModel.setRowCount(0);
        try {
            List<Mobil> list;
            if (query.isEmpty()) {
                list = mobilController.dapatkanSemuaMobil();
            } else {
                list = mobilController.cariMobil(query);
            }
            
            for (Mobil m : list) {
                Vector<Object> row = new Vector<>();
                row.add(m.getIdMobil());
                row.add(m.getPlatNomor());
                row.add(m.getMerk());
                row.add(m.getModel());
                row.add(m.getTahun());
                row.add(m.getWarna());
                row.add(m.getTarifHarian());
                row.add(m.getStatusMobil());
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Gagal mengambil data mobil: " + ex.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void tambahMobil() {
        if (!validateForm()) return;
        
        Mobil m = new Mobil();
        m.setPlatNomor(txtPlat.getText().trim().toUpperCase());
        m.setMerk(txtMerk.getText().trim());
        m.setModel(txtModel.getText().trim());
        m.setTahun(Integer.parseInt(txtTahun.getText().trim()));
        m.setWarna(txtWarna.getText().trim());
        m.setTarifHarian(Double.parseDouble(txtTarif.getText().trim()));
        m.setStatusMobil(cbStatus.getSelectedItem().toString());
        
        try {
            mobilController.tambahMobil(m);
            JOptionPane.showMessageDialog(this, "Data mobil berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
            menuUtama.refreshDashboard();
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate")) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan: Plat nomor sudah terdaftar!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void ubahMobil() {
        if (selectedId == -1) return;
        if (!validateForm()) return;
        
        Mobil m = new Mobil();
        m.setIdMobil(selectedId);
        m.setPlatNomor(txtPlat.getText().trim().toUpperCase());
        m.setMerk(txtMerk.getText().trim());
        m.setModel(txtModel.getText().trim());
        m.setTahun(Integer.parseInt(txtTahun.getText().trim()));
        m.setWarna(txtWarna.getText().trim());
        m.setTarifHarian(Double.parseDouble(txtTarif.getText().trim()));
        m.setStatusMobil(cbStatus.getSelectedItem().toString());
        
        try {
            mobilController.ubahMobil(m);
            JOptionPane.showMessageDialog(this, "Data mobil berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
            menuUtama.refreshDashboard();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal diperbarui: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusMobil() {
        if (selectedId == -1) return;
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus mobil ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try {
            mobilController.hapusMobil(selectedId);
            JOptionPane.showMessageDialog(this, "Data mobil berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
            menuUtama.refreshDashboard();
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Cannot delete or update a parent row")) {
                JOptionPane.showMessageDialog(this, 
                    "Tidak dapat menghapus mobil ini karena data transaksi penyewaan untuk mobil ini masih ada.\nUbah status mobil menjadi 'Servis' jika ingin menonaktifkannya.", 
                    "Gagal Menghapus", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
