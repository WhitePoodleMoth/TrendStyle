/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.admin;

import communication.communication;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import views.HomeScreen;

/**
 *
 * @author Unknown Account
 */
public class AdminHomeScreen extends javax.swing.JFrame {
    communication dbAccess = new communication();
    
    /**
     * Creates new form AdminHomeScreen
     */
    public AdminHomeScreen() {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../media/TrendStyleIcon.png")));
        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Panel = new javax.swing.JPanel();
        ButtonLogin = new javax.swing.JButton();
        ButtonRegister = new javax.swing.JButton();
        ButtonBack = new javax.swing.JButton();
        FieldUsername = new javax.swing.JTextField();
        FieldPassword = new javax.swing.JPasswordField();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TrendStyle - Admin");
        setResizable(false);

        Panel.setBackground(new java.awt.Color(255, 255, 255));
        Panel.setPreferredSize(new java.awt.Dimension(960, 540));
        Panel.setLayout(null);

        ButtonLogin.setBorderPainted(false);
        ButtonLogin.setContentAreaFilled(false);
        ButtonLogin.setMaximumSize(new java.awt.Dimension(300, 50));
        ButtonLogin.setMinimumSize(new java.awt.Dimension(300, 50));
        ButtonLogin.setPreferredSize(new java.awt.Dimension(300, 50));
        ButtonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonLoginActionPerformed(evt);
            }
        });
        Panel.add(ButtonLogin);
        ButtonLogin.setBounds(550, 330, 320, 60);

        ButtonRegister.setBorderPainted(false);
        ButtonRegister.setContentAreaFilled(false);
        ButtonRegister.setMaximumSize(new java.awt.Dimension(300, 50));
        ButtonRegister.setMinimumSize(new java.awt.Dimension(300, 50));
        ButtonRegister.setPreferredSize(new java.awt.Dimension(300, 50));
        ButtonRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonRegisterActionPerformed(evt);
            }
        });
        Panel.add(ButtonRegister);
        ButtonRegister.setBounds(550, 420, 320, 60);

        ButtonBack.setBorderPainted(false);
        ButtonBack.setContentAreaFilled(false);
        ButtonBack.setMaximumSize(new java.awt.Dimension(50, 50));
        ButtonBack.setMinimumSize(new java.awt.Dimension(50, 50));
        ButtonBack.setPreferredSize(new java.awt.Dimension(50, 50));
        ButtonBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonBackActionPerformed(evt);
            }
        });
        Panel.add(ButtonBack);
        ButtonBack.setBounds(10, 10, 60, 60);

        FieldUsername.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldUsername.setToolTipText("");
        FieldUsername.setBorder(null);
        FieldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldUsernameActionPerformed(evt);
            }
        });
        Panel.add(FieldUsername);
        FieldUsername.setBounds(603, 219, 260, 30);

        FieldPassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldPassword.setBorder(null);
        Panel.add(FieldPassword);
        FieldPassword.setBounds(603, 276, 260, 29);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/adminHome.png"))); // NOI18N
        Panel.add(Background);
        Background.setBounds(0, 0, 960, 540);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBackActionPerformed
        HomeScreen page = new HomeScreen();
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonBackActionPerformed

    private void FieldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_FieldUsernameActionPerformed

    private void ButtonRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonRegisterActionPerformed
        AdminRegisterScreen page = new AdminRegisterScreen();
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonRegisterActionPerformed

    private void ButtonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonLoginActionPerformed
        String username = FieldUsername.getText().replaceAll("\\s", "").toLowerCase();
        String password = FieldPassword.getText().replaceAll("\\s", "");
        if ("".equals(username) || "".equals(password)){
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos de usuário e senha.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            if (dbAccess.checkAdminLogin(username, password)) {
                AdminPanel page = new AdminPanel();
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_ButtonLoginActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminHomeScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminHomeScreen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Background;
    private javax.swing.JButton ButtonBack;
    private javax.swing.JButton ButtonLogin;
    private javax.swing.JButton ButtonRegister;
    private javax.swing.JPasswordField FieldPassword;
    private javax.swing.JTextField FieldUsername;
    private javax.swing.JPanel Panel;
    // End of variables declaration//GEN-END:variables
}
