/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rental.mobil;

/**
 *
 * @author user
 */
import rental.view.LoginForm;

/**
 *
 * @author user
 */
public class RENTALMOBIL {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Set Look and Feel to Nimbus for a cleaner, modern Swing appearance
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            // Fallback to System Look & Feel if Nimbus is unavailable
            try {
                javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                System.err.println("Gagal mengatur Look and Feel: " + e.getMessage());
            }
        }

        // Jalankan UI di Event Dispatch Thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Instansiasi dan tampilkan frame login baru
                new LoginForm().setVisible(true);
            }
        });
    }
    
}
