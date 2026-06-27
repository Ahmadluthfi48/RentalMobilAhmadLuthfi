package rental.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import rental.controller.MobilController;
import rental.controller.PenyewaController;
import rental.controller.PenyewaanController;
import rental.util.KoneksiDatabase;

/**
 * Menu Utama Dashboard untuk Aplikasi Rental Mobil.
 * Menggantikan MainFrame.java dan menggunakan pola MVC.
 */
public class MenuUtama extends JFrame {
    private final int sessionUserId;
    private final String sessionUsername;
    private final String sessionNamaLengkap;
    private final String sessionLevel;
    
    // Controllers for stats loading
    private final MobilController mobilController;
    private final PenyewaController penyewaController;
    private final PenyewaanController penyewaanController;
    
    // UI Layout Components
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel sidebarPanel;
    
    // Sub-forms (Panels)
    private JPanel dashboardPanel;
    private FormMobil mobilPanel;
    private FormPenyewa penyewaPanel;
    private FormPenyewaan penyewaanPanel;
    private FormUser userPanel;
    private FormLaporan laporanPanel;
    
    // Stat labels on dashboard
    private JLabel lblStatTotalMobil;
    private JLabel lblStatMobilTersedia;
    private JLabel lblStatTotalPenyewa;
    private JLabel lblStatSewaBerjalan;
    
    // Theme Colors matching the visual design
    private final Color primaryColor = new Color(30, 58, 138); // Navy Blue
    private final Color sidebarBg = new Color(17, 24, 39); // Slate Dark (#111827)
    private final Color headerBg = Color.WHITE;
    private final Color contentBg = new Color(243, 244, 246); // Light Gray
    private final Color textDark = new Color(17, 24, 39);
    private final Color textMuted = new Color(107, 114, 128);
    private final Color accentColor = new Color(59, 130, 246); // Blue Accent
    
    public MenuUtama(int idUser, String username, String namaLengkap, String level) {
        this.sessionUserId = idUser;
        this.sessionUsername = username;
        this.sessionNamaLengkap = namaLengkap;
        this.sessionLevel = level;
        
        this.mobilController = new MobilController();
        this.penyewaController = new PenyewaController();
        this.penyewaanController = new PenyewaanController();
        
        setTitle("Dashboard - Aplikasi Rental Mobil (" + level.toUpperCase() + ")");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // 1. Sidebar Panel
        createSidebar();
        add(sidebarPanel, BorderLayout.WEST);
        
        // 2. Center Panel (Header + Main Cards Panel)
        JPanel centerContainer = new JPanel(new BorderLayout());
        centerContainer.setBackground(contentBg);
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(headerBg);
        headerPanel.setPreferredSize(new Dimension(0, 70));
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(229, 231, 235)));
        
        JLabel lblWelcome = new JLabel("  Aplikasi Rental Mobil - Dashboard Utama");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblWelcome.setForeground(textDark);
        headerPanel.add(lblWelcome, BorderLayout.WEST);
        
        // Profile Display
        JPanel profilePanel = new JPanel(new GridBagLayout());
        profilePanel.setBackground(headerBg);
        profilePanel.setBorder(new EmptyBorder(0, 0, 0, 20));
        
        JLabel lblUserDetail = new JLabel("Halo, " + sessionNamaLengkap + " (" + sessionLevel.toUpperCase() + ")");
        lblUserDetail.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUserDetail.setForeground(textDark);
        
        JLabel lblRoleDesc = new JLabel("Online");
        lblRoleDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblRoleDesc.setForeground(new Color(16, 185, 129)); // Active green
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        profilePanel.add(lblUserDetail, gbc);
        
        gbc.gridy = 1;
        profilePanel.add(lblRoleDesc, gbc);
        
        headerPanel.add(profilePanel, BorderLayout.EAST);
        centerContainer.add(headerPanel, BorderLayout.NORTH);
        
        // Content Area with CardLayout
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(contentBg);
        cardPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Init Subpanels
        initSubpanels();
        
        centerContainer.add(cardPanel, BorderLayout.CENTER);
        add(centerContainer, BorderLayout.CENTER);
        
        // Default to Dashboard
        showPanel("Dashboard");
    }
    
    private void createSidebar() {
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(sidebarBg);
        sidebarPanel.setPreferredSize(new Dimension(240, 0));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        
        // Brand Title inside Sidebar
        JPanel brandPanel = new JPanel(new GridBagLayout());
        brandPanel.setBackground(sidebarBg);
        brandPanel.setMaximumSize(new Dimension(240, 70));
        brandPanel.setPreferredSize(new Dimension(240, 70));
        
        JLabel lblBrand = new JLabel("CAR RENTAL");
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblBrand.setForeground(Color.WHITE);
        brandPanel.add(lblBrand);
        
        sidebarPanel.add(brandPanel);
        sidebarPanel.add(Box.createVerticalStrut(15));
        
        // Navigation Buttons
        addSidebarButton("Dashboard", "Dashboard");
        addSidebarButton("Data Mobil", "Mobil");
        addSidebarButton("Data Penyewa", "Penyewa");
        addSidebarButton("Transaksi Sewa", "Penyewaan");
        
        // Role check for Admin-only menus
        if ("admin".equalsIgnoreCase(sessionLevel)) {
            addSidebarButton("Kelola User", "User");
        }
        
        addSidebarButton("Laporan", "Laporan");
        
        sidebarPanel.add(Box.createVerticalGlue());
        
        // Logout Button
        JButton btnLogout = new JButton("LOGOUT");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(new Color(220, 38, 38)); // Dark red
        btnLogout.setFocusPainted(false);
        btnLogout.setBorderPainted(false);
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(200, 40));
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MenuUtama.this, 
                    "Apakah Anda yakin ingin logout?", 
                    "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    KoneksiDatabase.tutupKoneksi();
                    new LoginForm().setVisible(true);
                    MenuUtama.this.dispose();
                }
            }
        });
        
        sidebarPanel.add(btnLogout);
        sidebarPanel.add(Box.createVerticalStrut(20));
    }
    
    private void addSidebarButton(String text, final String cardName) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(new Color(156, 163, 175)); // Default muted gray text
        btn.setBackground(sidebarBg);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 25, 10, 10));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(240, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(31, 41, 55)); // Hover dark-gray
                btn.setForeground(Color.WHITE);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(sidebarBg);
                btn.setForeground(new Color(156, 163, 175));
            }
        });
        
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPanel(cardName);
            }
        });
        
        sidebarPanel.add(btn);
        sidebarPanel.add(Box.createVerticalStrut(2));
    }
    
    private void initSubpanels() {
        // Dashboard Panel Creation
        createDashboardPanel();
        cardPanel.add(dashboardPanel, "Dashboard");
        
        // CRUD Panels Creation (Using refactored Form Classes)
        mobilPanel = new FormMobil(this);
        cardPanel.add(mobilPanel, "Mobil");
        
        penyewaPanel = new FormPenyewa();
        cardPanel.add(penyewaPanel, "Penyewa");
        
        penyewaanPanel = new FormPenyewaan(this);
        cardPanel.add(penyewaanPanel, "Penyewaan");
        
        if ("admin".equalsIgnoreCase(sessionLevel)) {
            userPanel = new FormUser();
            cardPanel.add(userPanel, "User");
        }
        
        laporanPanel = new FormLaporan();
        cardPanel.add(laporanPanel, "Laporan");
    }
    
    private void createDashboardPanel() {
        dashboardPanel = new JPanel(new BorderLayout());
        dashboardPanel.setBackground(contentBg);
        
        // Welcome Card
        JPanel bannerCard = new JPanel(new BorderLayout());
        bannerCard.setBackground(primaryColor);
        bannerCard.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        JLabel lblTitle = new JLabel("Sistem Informasi Rental Mobil");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblDesc = new JLabel("Kelola data kendaraan, penyewa, transaksi penyewaan, serta cetak laporan.");
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblDesc.setForeground(new Color(191, 219, 254));
        
        bannerCard.add(lblTitle, BorderLayout.NORTH);
        bannerCard.add(lblDesc, BorderLayout.CENTER);
        
        dashboardPanel.add(bannerCard, BorderLayout.NORTH);
        
        // Stat Cards Container (Grid)
        JPanel statsGrid = new JPanel(new GridLayout(1, 4, 15, 0));
        statsGrid.setBackground(contentBg);
        statsGrid.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        lblStatTotalMobil = new JLabel("0");
        statsGrid.add(createStatCard("TOTAL MOBIL", lblStatTotalMobil, new Color(59, 130, 246))); // Blue
        
        lblStatMobilTersedia = new JLabel("0");
        statsGrid.add(createStatCard("MOBIL TERSEDIA", lblStatMobilTersedia, new Color(16, 185, 129))); // Green
        
        lblStatTotalPenyewa = new JLabel("0");
        statsGrid.add(createStatCard("TOTAL PENYEWA", lblStatTotalPenyewa, new Color(245, 158, 11))); // Orange
        
        lblStatSewaBerjalan = new JLabel("0");
        statsGrid.add(createStatCard("SEWA BERJALAN", lblStatSewaBerjalan, new Color(239, 68, 68))); // Red
        
        dashboardPanel.add(statsGrid, BorderLayout.CENTER);
        
        // Bottom Help Card
        JPanel bottomCard = new JPanel(null);
        bottomCard.setBackground(Color.WHITE);
        bottomCard.setPreferredSize(new Dimension(0, 200));
        bottomCard.setBorder(BorderFactory.createLineBorder(new Color(229, 231, 235)));
        
        JLabel lblHelpTitle = new JLabel("Panduan Cepat Penggunaan:");
        lblHelpTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblHelpTitle.setBounds(20, 20, 400, 20);
        lblHelpTitle.setForeground(textDark);
        bottomCard.add(lblHelpTitle);
        
        String[] instructions = {
            "1. Daftarkan mobil baru atau ubah status mobil di menu 'Data Mobil'.",
            "2. Input data pelanggan/penyewa di menu 'Data Penyewa'.",
            "3. Lakukan transaksi sewa mobil di menu 'Transaksi Sewa' (tarif dihitung otomatis berdasarkan tanggal).",
            "4. Cetak dan filter rekapitulasi data di menu 'Laporan'."
        };
        
        int y = 50;
        for (String inst : instructions) {
            JLabel lblInst = new JLabel(inst);
            lblInst.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            lblInst.setForeground(new Color(55, 65, 81));
            lblInst.setBounds(20, y, 600, 20);
            bottomCard.add(lblInst);
            y += 25;
        }
        
        dashboardPanel.add(bottomCard, BorderLayout.SOUTH);
    }
    
    private JPanel createStatCard(String title, JLabel lblValue, Color accentColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
            new EmptyBorder(15, 15, 15, 15)
        ));
        
        // Left accent bar
        JPanel accentBar = new JPanel();
        accentBar.setBackground(accentColor);
        accentBar.setPreferredSize(new Dimension(5, 0));
        card.add(accentBar, BorderLayout.WEST);
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(new EmptyBorder(0, 10, 0, 0));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitle.setForeground(textMuted);
        textPanel.add(lblTitle, BorderLayout.NORTH);
        
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setForeground(textDark);
        textPanel.add(lblValue, BorderLayout.CENTER);
        
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }
    
    public void refreshDashboard() {
        try {
            lblStatTotalMobil.setText(String.valueOf(mobilController.hitungTotalMobil()));
            lblStatMobilTersedia.setText(String.valueOf(mobilController.hitungMobilTersedia()));
            lblStatTotalPenyewa.setText(String.valueOf(penyewaController.hitungTotalPenyewa()));
            lblStatSewaBerjalan.setText(String.valueOf(penyewaanController.hitungSewaBerjalan()));
        } catch (SQLException e) {
            System.err.println("Gagal memuat statistik dashboard: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Gagal memuat statistik dashboard: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void showPanel(String name) {
        cardLayout.show(cardPanel, name);
        
        // Refresh specific panel logic
        if ("Dashboard".equalsIgnoreCase(name)) {
            refreshDashboard();
        } else if ("Mobil".equalsIgnoreCase(name) && mobilPanel != null) {
            mobilPanel.refreshTable();
        } else if ("Penyewa".equalsIgnoreCase(name) && penyewaPanel != null) {
            penyewaPanel.refreshTable();
        } else if ("Penyewaan".equalsIgnoreCase(name) && penyewaanPanel != null) {
            penyewaanPanel.refreshData();
        } else if ("User".equalsIgnoreCase(name) && userPanel != null) {
            userPanel.refreshTable();
        } else if ("Laporan".equalsIgnoreCase(name) && laporanPanel != null) {
            laporanPanel.refreshTable();
        }
    }
}
