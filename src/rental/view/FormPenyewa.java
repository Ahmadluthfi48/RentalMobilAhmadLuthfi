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
import rental.controller.PenyewaController;
import rental.model.Penyewa;

/**
 * Form CRUD Penyewa (Pelanggan).
 * Menggantikan PenyewaPanel.java dan menggunakan Controller.
 */
public class FormPenyewa extends JPanel {
    private final PenyewaController penyewaController;
    
    // UI Elements
    private JTextField txtNama, txtKtp, txtHp, txtSearch;
    private JTextArea txtAlamat;
    private JTable tablePenyewa;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnEdit, btnDelete, btnClear, btnSearch;
    
    // Tracking selected ID
    private int selectedId = -1;
    
    // Theme Colors
    private final Color primaryColor = new Color(30, 58, 138);
    private final Color accentColor = new Color(59, 130, 246);
    private final Color bgPanel = Color.WHITE;
    private final Color bgTableHead = new Color(243, 244, 246);
    
    public FormPenyewa() {
        this.penyewaController = new PenyewaController();
        
        setLayout(new BorderLayout(15, 0));
        setBackground(new Color(243, 244, 246));
        
        // 1. Left Side Form Panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(bgPanel);
        formPanel.setPreferredSize(new Dimension(340, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)), 
            "Form Penyewa / Pelanggan", TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), primaryColor
        ));
        
        int x = 20, y = 30, w = 300, h = 30, dy = 60;
        
        JLabel lblNama = new JLabel("Nama Lengkap Penyewa");
        lblNama.setBounds(x, y, w, 20);
        lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblNama);
        
        txtNama = new JTextField();
        txtNama.setBounds(x, y + 20, w, h);
        txtNama.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtNama);
        
        y += dy;
        JLabel lblKtp = new JLabel("Nomor KTP (16 Digit)");
        lblKtp.setBounds(x, y, w, 20);
        lblKtp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblKtp);
        
        txtKtp = new JTextField();
        txtKtp.setBounds(x, y + 20, w, h);
        txtKtp.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtKtp);
        
        y += dy;
        JLabel lblHp = new JLabel("Nomor HP / Whatsapp");
        lblHp.setBounds(x, y, w, 20);
        lblHp.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblHp);
        
        txtHp = new JTextField();
        txtHp.setBounds(x, y + 20, w, h);
        txtHp.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtHp);
        
        y += dy;
        JLabel lblAlamat = new JLabel("Alamat Rumah");
        lblAlamat.setBounds(x, y, w, 20);
        lblAlamat.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblAlamat);
        
        txtAlamat = new JTextArea();
        txtAlamat.setLineWrap(true);
        txtAlamat.setWrapStyleWord(true);
        txtAlamat.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtAlamat.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        
        JScrollPane scrollAlamat = new JScrollPane(txtAlamat);
        scrollAlamat.setBounds(x, y + 20, w, 90);
        formPanel.add(scrollAlamat);
        
        // CRUD Buttons
        y += 130;
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
        btnEdit.setEnabled(false);
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
        
        JLabel lblSearch = new JLabel("Cari Penyewa:");
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
        String[] columns = {"ID", "Nama Penyewa", "Nomor KTP", "Nomor HP", "Alamat"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePenyewa = new JTable(tableModel);
        tablePenyewa.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tablePenyewa.setRowHeight(25);
        tablePenyewa.setGridColor(new Color(243, 244, 246));
        tablePenyewa.getTableHeader().setBackground(bgTableHead);
        tablePenyewa.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tablePenyewa.getTableHeader().setForeground(primaryColor);
        
        JScrollPane scrollPane = new JScrollPane(tablePenyewa);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);
        
        // Event Listeners
        initActionListeners();
    }
    
    private void initActionListeners() {
        // Save
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahPenyewa();
            }
        });
        
        // Edit
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahPenyewa();
            }
        });
        
        // Delete
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusPenyewa();
            }
        });
        
        // Cancel / Clear
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
        tablePenyewa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablePenyewa.getSelectedRow();
                if (row != -1) {
                    selectedId = Integer.parseInt(tablePenyewa.getValueAt(row, 0).toString());
                    txtNama.setText(tablePenyewa.getValueAt(row, 1).toString());
                    txtKtp.setText(tablePenyewa.getValueAt(row, 2).toString());
                    txtHp.setText(tablePenyewa.getValueAt(row, 3).toString());
                    
                    Object alamatObj = tablePenyewa.getValueAt(row, 4);
                    txtAlamat.setText(alamatObj != null ? alamatObj.toString() : "");
                    
                    // Toggle buttons
                    btnSave.setEnabled(false);
                    btnEdit.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });
    }
    
    private boolean validateForm() {
        String nama = txtNama.getText().trim();
        String ktp = txtKtp.getText().trim();
        String hp = txtHp.getText().trim();
        
        if (nama.isEmpty() || ktp.isEmpty() || hp.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nama, KTP, dan Nomor HP tidak boleh kosong!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Regex validations for digits only
        if (!ktp.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, 
                "Nomor KTP harus terdiri dari angka saja!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (!hp.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, 
                "Nomor HP harus terdiri dari angka saja!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        txtNama.setText("");
        txtKtp.setText("");
        txtHp.setText("");
        txtAlamat.setText("");
        selectedId = -1;
        
        btnSave.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        tablePenyewa.clearSelection();
    }
    
    public void refreshTable() {
        refreshTable("");
    }
    
    public void refreshTable(String query) {
        tableModel.setRowCount(0);
        try {
            List<Penyewa> list;
            if (query.isEmpty()) {
                list = penyewaController.dapatkanSemuaPenyewa();
            } else {
                list = penyewaController.cariPenyewa(query);
            }
            
            for (Penyewa p : list) {
                Vector<Object> row = new Vector<>();
                row.add(p.getIdPenyewa());
                row.add(p.getNamaPenyewa());
                row.add(p.getNoKtp());
                row.add(p.getNoHp());
                row.add(p.getAlamat());
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Gagal mengambil data penyewa: " + ex.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void tambahPenyewa() {
        if (!validateForm()) return;
        
        Penyewa p = new Penyewa();
        p.setNamaPenyewa(txtNama.getText().trim());
        p.setNoKtp(txtKtp.getText().trim());
        p.setNoHp(txtHp.getText().trim());
        p.setAlamat(txtAlamat.getText().trim());
        
        try {
            penyewaController.tambahPenyewa(p);
            JOptionPane.showMessageDialog(this, "Data penyewa berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void ubahPenyewa() {
        if (selectedId == -1) return;
        if (!validateForm()) return;
        
        Penyewa p = new Penyewa();
        p.setIdPenyewa(selectedId);
        p.setNamaPenyewa(txtNama.getText().trim());
        p.setNoKtp(txtKtp.getText().trim());
        p.setNoHp(txtHp.getText().trim());
        p.setAlamat(txtAlamat.getText().trim());
        
        try {
            penyewaController.ubahPenyewa(p);
            JOptionPane.showMessageDialog(this, "Data penyewa berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusPenyewa() {
        if (selectedId == -1) return;
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus penyewa ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try {
            penyewaController.hapusPenyewa(selectedId);
            JOptionPane.showMessageDialog(this, "Data penyewa berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Cannot delete or update a parent row")) {
                JOptionPane.showMessageDialog(this, 
                    "Tidak dapat menghapus penyewa ini karena penyewa ini memiliki riwayat transaksi penyewaan mobil.", 
                    "Gagal Menghapus", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
