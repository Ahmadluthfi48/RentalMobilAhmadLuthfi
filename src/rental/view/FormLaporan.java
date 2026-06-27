package rental.view;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import rental.controller.PenyewaanController;

/**
 * Form Laporan Penyewaan Mobil.
 * Menggantikan LaporanPanel.java dan menggunakan Controller.
 */
public class FormLaporan extends JPanel {
    private final PenyewaanController penyewaanController;
    
    // UI Elements
    private JTable tableLaporan;
    private DefaultTableModel tableModel;
    private JComboBox<String> cbFilterStatus;
    private JTextField txtSearch;
    private JButton btnSearch, btnPrint, btnExportTxt;
    
    // Theme Colors
    private final Color primaryColor = new Color(30, 58, 138);
    private final Color accentColor = new Color(59, 130, 246);
    private final Color bgPanel = Color.WHITE;
    private final Color bgTableHead = new Color(243, 244, 246);
    
    public FormLaporan() {
        this.penyewaanController = new PenyewaanController();
        
        setLayout(new BorderLayout(0, 15));
        setBackground(new Color(243, 244, 246));
        setBorder(new EmptyBorder(5, 5, 5, 5));
        
        // Top Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        filterPanel.setBackground(bgPanel);
        filterPanel.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        
        // Status Filter
        filterPanel.add(new JLabel("Filter Status:"));
        String[] statuses = {"Semua", "Berjalan", "Selesai", "Batal"};
        cbFilterStatus = new JComboBox<>(statuses);
        cbFilterStatus.setPreferredSize(new Dimension(130, 30));
        cbFilterStatus.setBackground(Color.WHITE);
        filterPanel.add(cbFilterStatus);
        
        // Keyword Search
        filterPanel.add(new JLabel("Cari Penyewa/Mobil:"));
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(180, 30));
        txtSearch.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219)));
        filterPanel.add(txtSearch);
        
        btnSearch = new JButton("Cari");
        btnSearch.setBackground(primaryColor);
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setPreferredSize(new Dimension(80, 30));
        btnSearch.setFocusPainted(false);
        filterPanel.add(btnSearch);
        
        // Print and Export Buttons (aligned right)
        JPanel btnRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnRightPanel.setBackground(bgPanel);
        
        btnPrint = new JButton("Cetak Laporan");
        btnPrint.setBackground(new Color(16, 185, 129)); // Green
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setPreferredSize(new Dimension(140, 30));
        btnPrint.setFocusPainted(false);
        btnRightPanel.add(btnPrint);
        
        btnExportTxt = new JButton("Ekspor ke TXT");
        btnExportTxt.setBackground(accentColor);
        btnExportTxt.setForeground(Color.WHITE);
        btnExportTxt.setPreferredSize(new Dimension(140, 30));
        btnExportTxt.setFocusPainted(false);
        btnRightPanel.add(btnExportTxt);
        
        // Container for all top controls
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(filterPanel, BorderLayout.WEST);
        topContainer.add(btnRightPanel, BorderLayout.EAST);
        topContainer.setBackground(bgPanel);
        
        add(topContainer, BorderLayout.NORTH);
        
        // Table Panel (Center)
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(bgPanel);
        tableContainer.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235)),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        String[] columns = {"ID Transaksi", "Plat Nomor", "Merk", "Model", "Nama Penyewa", "No. HP", "Tgl Sewa", "Tgl Kembali", "Durasi (Hari)", "Total Biaya", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableLaporan = new JTable(tableModel);
        tableLaporan.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tableLaporan.setRowHeight(25);
        tableLaporan.setGridColor(new Color(243, 244, 246));
        tableLaporan.getTableHeader().setBackground(bgTableHead);
        tableLaporan.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tableLaporan.getTableHeader().setForeground(primaryColor);
        
        JScrollPane scrollPane = new JScrollPane(tableLaporan);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        
        add(tableContainer, BorderLayout.CENTER);
        
        // Event Handlers
        initActionListeners();
    }
    
    public void refreshTable() {
        String filterStatus = cbFilterStatus.getSelectedItem().toString();
        String keyword = txtSearch.getText().trim();
        
        tableModel.setRowCount(0);
        try {
            List<Map<String, Object>> list = penyewaanController.dapatkanLaporanData(filterStatus, keyword);
            
            for (Map<String, Object> map : list) {
                Vector<Object> row = new Vector<>();
                row.add(map.get("id_penyewaan"));
                row.add(map.get("plat_nomor"));
                row.add(map.get("merk"));
                row.add(map.get("model"));
                row.add(map.get("nama_penyewa"));
                row.add(map.get("no_hp"));
                row.add(map.get("tanggal_sewa"));
                row.add(map.get("tanggal_kembali"));
                row.add(map.get("durasi_hari"));
                row.add(map.get("total_biaya"));
                row.add(map.get("status"));
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Gagal mengambil laporan penyewaan: " + ex.getMessage(), 
                "Error Database", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void initActionListeners() {
        // Search Click
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        
        // Status filter combobox changed
        cbFilterStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        
        // Search bar typing enter key
        txtSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable();
            }
        });
        
        // Print Report
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cetakLaporan();
            }
        });
        
        // Export to TXT
        btnExportTxt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eksporKeTxt();
            }
        });
    }
    
    private void cetakLaporan() {
        try {
            MessageFormat header = new MessageFormat("LAPORAN PENYEWAAN MOBIL");
            MessageFormat footer = new MessageFormat("Halaman - {0}");
            
            boolean complete = tableLaporan.print(JTable.PrintMode.FIT_WIDTH, header, footer);
            if (complete) {
                JOptionPane.showMessageDialog(this, "Proses pencetakan selesai.", "Info Cetak", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception pe) {
            JOptionPane.showMessageDialog(this, "Gagal mencetak: " + pe.getMessage(), "Error Cetak", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eksporKeTxt() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Simpan Laporan sebagai TXT");
        fileChooser.setSelectedFile(new File("Laporan_Penyewaan_Mobil.txt"));
        
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            try (FileWriter writer = new FileWriter(fileToSave)) {
                writer.write("=======================================================================\n");
                writer.write("                     LAPORAN TRANSAKSI RENTAL MOBIL                    \n");
                writer.write("=======================================================================\n\n");
                
                // Table header
                writer.write(String.format("%-8s | %-12s | %-15s | %-20s | %-11s | %-11s | %-12s | %-9s\n", 
                    "ID", "Plat", "Mobil", "Penyewa", "Tgl Sewa", "Tgl Kembali", "Total Biaya", "Status"));
                writer.write("--------------------------------------------------------------------------------------------------------------------\n");
                
                for (int i = 0; i < tableLaporan.getRowCount(); i++) {
                    String id = tableLaporan.getValueAt(i, 0).toString();
                    String plat = tableLaporan.getValueAt(i, 1).toString();
                    String mobil = tableLaporan.getValueAt(i, 2).toString() + " " + tableLaporan.getValueAt(i, 3).toString();
                    String penyewa = tableLaporan.getValueAt(i, 4).toString();
                    String tglSewa = tableLaporan.getValueAt(i, 6).toString();
                    String tglKmb = tableLaporan.getValueAt(i, 7).toString();
                    String total = tableLaporan.getValueAt(i, 9).toString();
                    String status = tableLaporan.getValueAt(i, 10).toString();
                    
                    writer.write(String.format("%-8s | %-12s | %-15s | %-20s | %-11s | %-11s | Rp%-10s | %-9s\n", 
                        id, plat, 
                        mobil.length() > 15 ? mobil.substring(0, 15) : mobil, 
                        penyewa.length() > 20 ? penyewa.substring(0, 20) : penyewa, 
                        tglSewa, tglKmb, total.replace(".00", ""), status));
                }
                
                writer.write("=======================================================================\n");
                writer.write("Generated on: " + new java.util.Date().toString() + "\n");
                
                JOptionPane.showMessageDialog(this, "Laporan berhasil diekspor ke: " + fileToSave.getAbsolutePath(), "Ekspor Sukses", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Gagal menulis file: " + e.getMessage(), "Error Ekspor", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
