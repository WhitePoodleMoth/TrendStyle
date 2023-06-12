/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.admin;

import communication.communication;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import utils.Validator;

/**
 *
 * @author Unknown Account
 */
public class AdminCategoryManagerScreen extends javax.swing.JFrame {
    communication dbAccess = new communication();
    int ID = 0;
    int categoryID = 0;
    
    public void insertData() {
        ArrayList vendorData = dbAccess.collectCategoryData(this.categoryID);
        FieldName.setText((String) vendorData.get(0));
        FieldDescription.setText((String) vendorData.get(1));
    }
    
    private void showMessageError() {
        JOptionPane.showMessageDialog(null, 
            "<html><body><p style='width: 200px;'>Por favor, preencha todos os campos antes de atualizar a categoria.</p></body></html>",
            "Erro na Atualização", 
            JOptionPane.ERROR_MESSAGE);
    }

    private boolean areFieldsEmpty() {
        try {
            if (FieldName.getText().isEmpty() || FieldDescription.getText().isEmpty()) {
                showMessageError();
                return true;
            } else {
                return false;
            }
        } catch(Exception ex) {}
        return true;
    }
    
    private void deleteCategory() {
        int result = JOptionPane.showConfirmDialog(null, 
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja excluir esta categoria? Esta ação é irreversível.</p></body></html>", 
            "Confirmação de Exclusão", 
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            if (dbAccess.deleteCategory(this.categoryID)) {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>A categoria foi excluída com sucesso.</p></body></html>",
                "Exclusão Concluída", 
                JOptionPane.INFORMATION_MESSAGE);
                AdminCategoryScreen page = new AdminCategoryScreen(this.ID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante a exclusão do fornecedor. Por favor, tente novamente.</p></body></html>",
                "Erro na Exclusão", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
     private void registerNewCategory() {
        int result = JOptionPane.showConfirmDialog(null, 
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja registrar uma nova categoria?</p></body></html>", 
            "Confirmação de Registro", 
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            String name = FieldName.getText();
            String description = FieldDescription.getText();
            if(dbAccess.registerCategory(name, description)) {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>A categoria foi registrado com sucesso!</p></body></html>",
                "Registro Concluído", 
                JOptionPane.INFORMATION_MESSAGE);
                AdminCategoryScreen page = new AdminCategoryScreen(this.ID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante o registro da categoria. Por favor, tente novamente.</p></body></html>",
                "Erro no Registro", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateCategory() {
        int result = JOptionPane.showConfirmDialog(null, 
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja atualizar a categoria?</p></body></html>", 
            "Confirmação de Atualização", 
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            String name = FieldName.getText();
            String description = FieldDescription.getText();
            if(dbAccess.updateCategory(this.categoryID, name, description)) {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>A categoria foi atualizado com sucesso!</p></body></html>",
                "Atualização Concluída", 
                JOptionPane.INFORMATION_MESSAGE);
                AdminCategoryManagerScreen page = new AdminCategoryManagerScreen(this.ID, this.categoryID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante a atualização da categoria. Por favor, tente novamente.</p></body></html>",
                "Erro na Atualização", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Creates new form AdminCategoryManagerScreen
     */
    public AdminCategoryManagerScreen(int _ID, int _categoryID) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../media/TrendStyleIcon.png")));
        setLocationRelativeTo(null);
        this.ID = _ID;
        this.categoryID = _categoryID;
        
        if (this.categoryID>0) {
            BackgroundRegister.setVisible(false);
            BackgroundUpdate.setVisible(true);
            insertData();
        } else {
            BackgroundRegister.setVisible(true);
            BackgroundUpdate.setVisible(false);
        }
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
        ButtonBack = new javax.swing.JButton();
        FieldName = new javax.swing.JTextField();
        FieldDescription = new javax.swing.JTextField();
        ButtonUpdate = new javax.swing.JButton();
        ButtonDelete = new javax.swing.JButton();
        BackgroundUpdate = new javax.swing.JLabel();
        BackgroundRegister = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TrendStyle - Admin Category Manager");

        Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        Panel.add(ButtonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 50, 40));

        FieldName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldName.setBorder(null);
        Panel.add(FieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 169, 846, 33));

        FieldDescription.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldDescription.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldDescription.setBorder(null);
        Panel.add(FieldDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(56, 251, 847, 68));

        ButtonUpdate.setBorderPainted(false);
        ButtonUpdate.setContentAreaFilled(false);
        ButtonUpdate.setMaximumSize(new java.awt.Dimension(50, 50));
        ButtonUpdate.setMinimumSize(new java.awt.Dimension(50, 50));
        ButtonUpdate.setPreferredSize(new java.awt.Dimension(50, 50));
        ButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonUpdateActionPerformed(evt);
            }
        });
        Panel.add(ButtonUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 420, 140, 50));

        ButtonDelete.setBorderPainted(false);
        ButtonDelete.setContentAreaFilled(false);
        ButtonDelete.setMaximumSize(new java.awt.Dimension(50, 50));
        ButtonDelete.setMinimumSize(new java.awt.Dimension(50, 50));
        ButtonDelete.setPreferredSize(new java.awt.Dimension(50, 50));
        ButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDeleteActionPerformed(evt);
            }
        });
        Panel.add(ButtonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 420, 120, 50));

        BackgroundUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/adminCategoryUpdate.png"))); // NOI18N
        Panel.add(BackgroundUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        BackgroundRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/adminCategoryRegister.png"))); // NOI18N
        Panel.add(BackgroundRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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

    private void ButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonUpdateActionPerformed
        if (!areFieldsEmpty()) {
            if (this.categoryID>0) {
                updateCategory();
            } else {
                registerNewCategory();
            }
        }
    }//GEN-LAST:event_ButtonUpdateActionPerformed

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
        if (this.categoryID>0) {
            deleteCategory();
        } else {
            if (!areFieldsEmpty()) {
                registerNewCategory();
            }
        }
    }//GEN-LAST:event_ButtonDeleteActionPerformed

    private void ButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBackActionPerformed
        AdminCategoryScreen page = new AdminCategoryScreen(this.ID);
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonBackActionPerformed

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
            java.util.logging.Logger.getLogger(AdminCategoryManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminCategoryManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminCategoryManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminCategoryManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminCategoryManagerScreen(0,0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundRegister;
    private javax.swing.JLabel BackgroundUpdate;
    private javax.swing.JButton ButtonBack;
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonUpdate;
    private javax.swing.JTextField FieldDescription;
    private javax.swing.JTextField FieldName;
    private javax.swing.JPanel Panel;
    // End of variables declaration//GEN-END:variables
}
