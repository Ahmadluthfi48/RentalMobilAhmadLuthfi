package rental.view;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import rental.controller.UserController;
import rental.model.User;

/**
 * Form Login untuk Aplikasi Rental Mobil.
 */
public class LoginForm extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnExit;
    private final UserController userController;
    
    // Theme Colors
    private final Color primaryColor = new Color(30, 58, 138); // Navy Blue (#1E3A8A)
    private final Color secondaryColor = new Color(59, 130, 246); // Accent Blue (#3B82F6)
    private final Color bgColor = new Color(243, 244, 246); // Light Gray (#F3F4F6)
    private final Color cardColor = Color.WHITE;
    private final Color textColor = new Color(31, 41, 55); // Dark Gray (#1F2937)
    
    public LoginForm() {
        this.userController = new UserController();
        
        setTitle("Login - Aplikasi Rental Mobil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true); // Modern borderless look
        
        // Root Panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createLineBorder(primaryColor, 2));
        setContentPane(mainPanel);
        
        // Top Header Panel (Banner)
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(400, 140));
        headerPanel.setLayout(new GridBagLayout());
        
        JLabel lblHeader = new JLabel("RENTAL MOBIL");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblHeader.setForeground(Color.WHITE);
        
        JLabel lblSubHeader = new JLabel("Aplikasi Manajemen & Rental Kendaraan");
        lblSubHeader.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubHeader.setForeground(new Color(191, 219, 254)); // Soft light blue
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 0);
        headerPanel.add(lblHeader, gbc);
        
        gbc.gridy = 1;
        headerPanel.add(lblSubHeader, gbc);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Center Form Panel (Card Layout inside container)
        JPanel centerPanel = new JPanel(null);
        centerPanel.setBackground(bgColor);
        
        JPanel formCard = new JPanel(null);
        formCard.setBounds(30, 20, 340, 310);
        formCard.setBackground(cardColor);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
            new EmptyBorder(20, 20, 20, 20)
        ));
        
        // Form Title
        JLabel lblTitle = new JLabel("Silakan Login");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(textColor);
        lblTitle.setBounds(20, 15, 200, 30);
        formCard.add(lblTitle);
        
        // Username Input
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblUsername.setForeground(new Color(107, 114, 128));
        lblUsername.setBounds(20, 60, 200, 20);
        formCard.add(lblUsername);
        
        txtUsername = new JTextField();
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBounds(20, 85, 300, 35);
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formCard.add(txtUsername);
        
        // Password Input
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPassword.setForeground(new Color(107, 114, 128));
        lblPassword.setBounds(20, 135, 200, 20);
        formCard.add(lblPassword);
        
        txtPassword = new JPasswordField();
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBounds(20, 160, 300, 35);
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formCard.add(txtPassword);
        
        // Buttons
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setBackground(primaryColor);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnLogin.setBounds(20, 220, 300, 40);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effects
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnLogin.setBackground(secondaryColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnLogin.setBackground(primaryColor);
            }
        });
        formCard.add(btnLogin);
        
        btnExit = new JButton("Batal & Keluar");
        btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnExit.setForeground(new Color(156, 163, 175));
        btnExit.setBorderPainted(false);
        btnExit.setContentAreaFilled(false);
        btnExit.setFocusPainted(false);
        btnExit.setBounds(20, 270, 300, 25);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnExit.setForeground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btnExit.setForeground(new Color(156, 163, 175));
            }
        });
        formCard.add(btnExit);
        
        centerPanel.add(formCard);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Action Listeners
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesLogin();
            }
        });
        
        // Trigger login on ENTER key in text fields
        ActionListener tfAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prosesLogin();
            }
        };
        txtUsername.addActionListener(tfAction);
        txtPassword.addActionListener(tfAction);
        
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
    
    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Username dan Password tidak boleh kosong!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            User user = userController.login(username, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, 
                    "Selamat datang, " + user.getNamaLengkap() + " (" + user.getLevel().toUpperCase() + ")!", 
                    "Login Sukses", JOptionPane.INFORMATION_MESSAGE);
                
                // Launch MenuUtama with current user session details
                new MenuUtama(user.getIdUser(), user.getUsername(), user.getNamaLengkap(), user.getLevel()).setVisible(true);
                this.dispose(); // Close login frame
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Username atau Password salah!", 
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
                txtPassword.requestFocus();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Gagal masuk: " + ex.getMessage(), 
                "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }
}
