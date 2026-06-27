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
import rental.controller.UserController;
import rental.model.User;

/**
 * Form CRUD User (Pengelola / Staff / Admin).
 * Menggantikan UserPanel.java dan menggunakan Controller.
 */
public class FormUser extends JPanel {
    private final UserController userController;
    
    // UI Elements
    private JTextField txtUsername, txtNamaLengkap, txtSearch;
    private JPasswordField txtPassword;
    private JComboBox<String> cbLevel;
    private JTable tableUser;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnEdit, btnDelete, btnClear, btnSearch;
    
    // Tracking selected ID
    private int selectedId = -1;
    
    // Theme Colors
    private final Color primaryColor = new Color(30, 58, 138);
    private final Color accentColor = new Color(59, 130, 246);
    private final Color bgPanel = Color.WHITE;
    private final Color bgTableHead = new Color(243, 244, 246);
    
    public FormUser() {
        this.userController = new UserController();
        
        setLayout(new BorderLayout(15, 0));
        setBackground(new Color(243, 244, 246));
        
        // 1. Left Side Form Panel
        JPanel formPanel = new JPanel(null);
        formPanel.setBackground(bgPanel);
        formPanel.setPreferredSize(new Dimension(340, 0));
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)), 
            "Form Kelola User", TitledBorder.LEFT, TitledBorder.TOP, 
            new Font("Segoe UI", Font.BOLD, 14), primaryColor
        ));
        
        int x = 20, y = 30, w = 300, h = 30, dy = 65;
        
        JLabel lblUsername = new JLabel("Username Login");
        lblUsername.setBounds(x, y, w, 20);
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setBounds(x, y + 20, w, h);
        txtUsername.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtUsername);
        
        y += dy;
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(x, y, w, 20);
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setBounds(x, y + 20, w, h);
        txtPassword.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtPassword);
        
        y += dy;
        JLabel lblNama = new JLabel("Nama Lengkap");
        lblNama.setBounds(x, y, w, 20);
        lblNama.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblNama);
        
        txtNamaLengkap = new JTextField();
        txtNamaLengkap.setBounds(x, y + 20, w, h);
        txtNamaLengkap.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        formPanel.add(txtNamaLengkap);
        
        y += dy;
        JLabel lblLevel = new JLabel("Level Akses");
        lblLevel.setBounds(x, y, w, 20);
        lblLevel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        formPanel.add(lblLevel);
        
        String[] levels = {"admin", "staff"};
        cbLevel = new JComboBox<>(levels);
        cbLevel.setBounds(x, y + 20, w, h);
        cbLevel.setBackground(Color.WHITE);
        formPanel.add(cbLevel);
        
        // CRUD Buttons
        y += dy + 20;
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
        
        JLabel lblSearch = new JLabel("Cari User:");
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
        String[] columns = {"ID", "Username", "Nama Lengkap", "Level Akses"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableUser = new JTable(tableModel);
        tableUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableUser.setRowHeight(25);
        tableUser.setGridColor(new Color(243, 244, 246));
        tableUser.getTableHeader().setBackground(bgTableHead);
        tableUser.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableUser.getTableHeader().setForeground(primaryColor);
        
        JScrollPane scrollPane = new JScrollPane(tableUser);
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.CENTER);
        
        // Action Listeners
        initActionListeners();
    }
    
    private void initActionListeners() {
        // Save
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahUser();
            }
        });
        
        // Edit
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ubahUser();
            }
        });
        
        // Delete
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hapusUser();
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
        tableUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableUser.getSelectedRow();
                if (row != -1) {
                    selectedId = Integer.parseInt(tableUser.getValueAt(row, 0).toString());
                    txtUsername.setText(tableUser.getValueAt(row, 1).toString());
                    txtNamaLengkap.setText(tableUser.getValueAt(row, 2).toString());
                    cbLevel.setSelectedItem(tableUser.getValueAt(row, 3).toString());
                    
                    // On editing, clear password field or leave it for optional update
                    txtPassword.setText("");
                    
                    // Toggle buttons
                    btnSave.setEnabled(false);
                    btnEdit.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });
    }
    
    private boolean validateForm(boolean isEdit) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        String nama = txtNamaLengkap.getText().trim();
        
        if (username.isEmpty() || nama.isEmpty() || (!isEdit && password.isEmpty())) {
            JOptionPane.showMessageDialog(this, 
                "Username, Password, dan Nama Lengkap tidak boleh kosong!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        txtUsername.setText("");
        txtPassword.setText("");
        txtNamaLengkap.setText("");
        cbLevel.setSelectedIndex(0);
        selectedId = -1;
        
        btnSave.setEnabled(true);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        tableUser.clearSelection();
    }
    
    public void refreshTable() {
        refreshTable("");
    }
    
    public void refreshTable(String query) {
        tableModel.setRowCount(0);
        try {
            List<User> list;
            if (query.isEmpty()) {
                list = userController.dapatkanSemuaUser();
            } else {
                list = userController.cariUser(query);
            }
            
            for (User u : list) {
                Vector<Object> row = new Vector<>();
                row.add(u.getIdUser());
                row.add(u.getUsername());
                row.add(u.getNamaLengkap());
                row.add(u.getLevel());
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Gagal mengambil data user: " + ex.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void tambahUser() {
        if (!validateForm(false)) return;
        
        User u = new User();
        u.setUsername(txtUsername.getText().trim());
        u.setPassword(new String(txtPassword.getPassword()).trim());
        u.setNamaLengkap(txtNamaLengkap.getText().trim());
        u.setLevel(cbLevel.getSelectedItem().toString());
        
        try {
            userController.tambahUser(u);
            JOptionPane.showMessageDialog(this, "User berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } catch (SQLException ex) {
            if (ex.getMessage().contains("Duplicate")) {
                JOptionPane.showMessageDialog(this, "Username sudah digunakan!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void ubahUser() {
        if (selectedId == -1) return;
        if (!validateForm(true)) return;
        
        try {
            // Retrieve current user first to preserve password if not updated
            User existing = userController.dapatkanUserBerdasarkanId(selectedId);
            if (existing == null) {
                JOptionPane.showMessageDialog(this, "User tidak ditemukan!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            User u = new User();
            u.setIdUser(selectedId);
            u.setUsername(txtUsername.getText().trim());
            
            String passInput = new String(txtPassword.getPassword()).trim();
            if (passInput.isEmpty()) {
                u.setPassword(existing.getPassword()); // keep old password
            } else {
                u.setPassword(passInput); // set new password
            }
            
            u.setNamaLengkap(txtNamaLengkap.getText().trim());
            u.setLevel(cbLevel.getSelectedItem().toString());
            
            userController.ubahUser(u);
            JOptionPane.showMessageDialog(this, "User berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void hapusUser() {
        if (selectedId == -1) return;
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Apakah Anda yakin ingin menghapus user ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            
        if (confirm != JOptionPane.YES_OPTION) return;
        
        try {
            userController.hapusUser(selectedId);
            JOptionPane.showMessageDialog(this, "User berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            refreshTable();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
