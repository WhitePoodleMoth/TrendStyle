/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.admin;

import communication.communication;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import utils.Validator;

/**
 *
 * @author Unknown Account
 */
public class AdminVendorManagerScreen extends javax.swing.JFrame {
    communication dbAccess = new communication();
    Validator validator = new Validator();
    int ID = 0;
    int vendorID = 0;
    
    /**
     * Creates new form AdminVendorManagerScreen
     */
    public void insertData() {
        ArrayList vendorData = dbAccess.collectVendorData(this.vendorID);
        FieldNomeFantasia.setText((String) vendorData.get(0));
        FieldRazaoSocial.setText((String) vendorData.get(1));
        String CNPJ = (String) vendorData.get(2);
        while (CNPJ.length() < 14) {
            CNPJ =  CNPJ + "0";
        }
        if (CNPJ != null && CNPJ.length() == 14) {
            CNPJ = CNPJ.substring(0, 2) + "." + CNPJ.substring(2, 5) + "." + CNPJ.substring(5, 8) + "/" + CNPJ.substring(8, 12) + "-" + CNPJ.substring(12);
        }
        FieldCNPJ.setValue(CNPJ);

        FieldEmail.setText((String) vendorData.get(3));
        String celular = (String) vendorData.get(4);
        if (celular.length() == 11) {
            celular = "(" + celular.substring(0, 2) + ") " + celular.substring(2, 7) + "-" + celular.substring(7);
        }
        FieldCelular.setValue(celular);
        FieldEstate.setSelectedItem((String) vendorData.get(5));
        String CEP = (String) vendorData.get(6);
        if (CEP.length() == 8) {
            CEP = CEP.substring(0, 5) + "-" + CEP.substring(5);
        }
        FieldCEP.setValue(CEP);
        FieldCity.setText((String) vendorData.get(7));
        FieldAddress.setText((String) vendorData.get(8));
        FieldAddressNumber.setText((String) vendorData.get(9));
    }

    private void showMessageError() {
        JOptionPane.showMessageDialog(null, 
            "<html><body><p style='width: 200px;'>Por favor, preencha todos os campos antes de atualizar o fornecedor.</p></body></html>",
            "Erro na Atualização", 
            JOptionPane.ERROR_MESSAGE);
    }

    private boolean areFieldsEmpty() {
        try {
            if (FieldNomeFantasia.getText().isEmpty() || FieldRazaoSocial.getText().isEmpty() ||
                FieldCNPJ.getValue().toString().isEmpty() || FieldEmail.getText().isEmpty() ||
                FieldCelular.getValue().toString().isEmpty() || FieldEstate.getSelectedItem().toString().isEmpty() ||
                FieldCEP.getValue().toString().isEmpty() || FieldCity.getText().isEmpty() ||
                FieldAddress.getText().isEmpty() || FieldAddressNumber.getText().isEmpty()) {
                showMessageError();
                return true;
            } else {
                return false;
            }
        } catch(Exception ex) {}
        return true;
    }
    
    private Map<String, String> extractFields() {
        Map<String, String> data = new HashMap<>();
        data.put("nomeFantasia", FieldNomeFantasia.getText());
        data.put("razaoSocial", FieldRazaoSocial.getText());
        data.put("cnpj", ((String) FieldCNPJ.getValue()).replace(".", "").replace("/", "").replace("-", ""));
        data.put("email", FieldEmail.getText());
        data.put("celular", ((String) FieldCelular.getValue()).replace("(", "").replace(") ", "").replace("-", ""));
        data.put("estado", (String) FieldEstate.getSelectedItem());
        data.put("cep", ((String) FieldCEP.getValue()).replace("-", ""));
        data.put("cidade", FieldCity.getText());
        data.put("rua", FieldAddress.getText());
        data.put("numero", FieldAddressNumber.getText());
        return data;
    }
    
    private void registerNewVendor(String cnpj, String nomeFantasia, String razaoSocial, String email, String celular, String cep, String rua, String numero, String cidade, String estado) {
        int result = JOptionPane.showConfirmDialog(null, 
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja registrar um novo fornecedor?</p></body></html>", 
            "Confirmação de Registro", 
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            if(dbAccess.registerVendor(cnpj, nomeFantasia, razaoSocial, email, celular, cep, rua, numero, cidade, estado)) {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>O fornecedor foi registrado com sucesso!</p></body></html>",
                "Registro Concluído", 
                JOptionPane.INFORMATION_MESSAGE);
                AdminVendorScreen page = new AdminVendorScreen(this.ID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante o registro do fornecedor. Por favor, tente novamente.</p></body></html>",
                "Erro no Registro", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateVendor(String cnpj, String nomeFantasia, String razaoSocial, String email, String celular, String cep, String rua, String numero, String cidade, String estado) {
        int result = JOptionPane.showConfirmDialog(null, 
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja atualizar o fornecedor?</p></body></html>", 
            "Confirmação de Atualização", 
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            if(dbAccess.updateVendor(this.vendorID, cnpj, nomeFantasia, razaoSocial, email, celular, cep, rua, numero, cidade, estado)) {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>O fornecedor foi atualizado com sucesso!</p></body></html>",
                "Atualização Concluída", 
                JOptionPane.INFORMATION_MESSAGE);
                AdminVendorManagerScreen page = new AdminVendorManagerScreen(this.ID, this.vendorID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante a atualização do fornecedor. Por favor, tente novamente.</p></body></html>",
                "Erro na Atualização", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteVendor() {
        int result = JOptionPane.showConfirmDialog(null, 
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja excluir este fornecedor? Esta ação é irreversível.</p></body></html>", 
            "Confirmação de Exclusão", 
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            if (dbAccess.deleteVendor(this.vendorID)) {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>O fornecedor foi excluído com sucesso.</p></body></html>",
                "Exclusão Concluída", 
                JOptionPane.INFORMATION_MESSAGE);
                AdminVendorScreen page = new AdminVendorScreen(this.ID);
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
    
    public AdminVendorManagerScreen(int _ID, int _vendorID) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../media/TrendStyleIcon.png")));
        setLocationRelativeTo(null);
        this.ID = _ID;
        this.vendorID = _vendorID;
        
        if (this.vendorID>0) {
            BackgroundRegister.setVisible(true);
            BackgroundUpdate.setVisible(false);
            insertData();
        } else {
            BackgroundRegister.setVisible(false);
            BackgroundUpdate.setVisible(true);
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
        FieldNomeFantasia = new javax.swing.JTextField();
        FieldRazaoSocial = new javax.swing.JTextField();
        FieldCNPJ = new javax.swing.JFormattedTextField();
        FieldEmail = new javax.swing.JTextField();
        FieldCelular = new javax.swing.JFormattedTextField();
        FieldEstate = new javax.swing.JComboBox<>();
        FieldCEP = new javax.swing.JFormattedTextField();
        FieldCity = new javax.swing.JTextField();
        FieldAddress = new javax.swing.JTextField();
        FieldAddressNumber = new javax.swing.JTextField();
        ButtonUpdate = new javax.swing.JButton();
        ButtonDelete = new javax.swing.JButton();
        ButtonBack = new javax.swing.JButton();
        BackgroundRegister = new javax.swing.JLabel();
        BackgroundUpdate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TrendStyle - Admin Vendor Manager");
        setResizable(false);

        Panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        FieldNomeFantasia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldNomeFantasia.setBorder(null);
        Panel.add(FieldNomeFantasia, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 169, 264, 33));

        FieldRazaoSocial.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldRazaoSocial.setBorder(null);
        Panel.add(FieldRazaoSocial, new org.netbeans.lib.awtextra.AbsoluteConstraints(337, 169, 320, 33));

        FieldCNPJ.setBorder(null);
        try {
            FieldCNPJ.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##.###.###/####-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldCNPJ.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Panel.add(FieldCNPJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(672, 169, 232, 33));

        FieldEmail.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldEmail.setBorder(null);
        Panel.add(FieldEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 264, 355, 32));

        FieldCelular.setBorder(null);
        try {
            FieldCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldCelular.setToolTipText("");
        FieldCelular.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Panel.add(FieldCelular, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 264, 169, 32));

        FieldEstate.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldEstate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SP", "MG", "RJ", "BA", "PR", "RS", "PE", "CE", "PA", "SC", "MA", "GO", "AM", "ES", "PB", "RN", "MT", "AL", "PI", "DF", "MS", "SE", "RO", "TO", "AC", "AP", "RR" }));
        Panel.add(FieldEstate, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 261, 160, 38));

        FieldCEP.setBorder(null);
        try {
            FieldCEP.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("#####-###")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        FieldCEP.setToolTipText("");
        FieldCEP.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Panel.add(FieldCEP, new org.netbeans.lib.awtextra.AbsoluteConstraints(783, 264, 120, 32));

        FieldCity.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldCity.setBorder(null);
        Panel.add(FieldCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 359, 250, 30));

        FieldAddress.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldAddress.setBorder(null);
        Panel.add(FieldAddress, new org.netbeans.lib.awtextra.AbsoluteConstraints(326, 359, 460, 30));

        FieldAddressNumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldAddressNumber.setBorder(null);
        Panel.add(FieldAddressNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(804, 359, 99, 30));

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

        BackgroundRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/adminVendorUpdate.png"))); // NOI18N
        Panel.add(BackgroundRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        BackgroundUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/adminVendorRegister.png"))); // NOI18N
        Panel.add(BackgroundUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        AdminVendorScreen page = new AdminVendorScreen(this.ID);
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonBackActionPerformed

    private void ButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonUpdateActionPerformed
        if (!areFieldsEmpty()) {
            Map<String, String> data = extractFields();
            if (this.vendorID>0) {
                updateVendor(data.get("cnpj"), data.get("razaoSocial"), data.get("nomeFantasia"), data.get("email"), data.get("celular"), data.get("cep"), data.get("rua"), data.get("numero"), data.get("cidade"), data.get("estado"));
            } else {
                registerNewVendor(data.get("cnpj"), data.get("razaoSocial"), data.get("nomeFantasia"), data.get("email"), data.get("celular"), data.get("cep"), data.get("rua"), data.get("numero"), data.get("cidade"), data.get("estado"));
            }
        }
    }//GEN-LAST:event_ButtonUpdateActionPerformed

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
        if (this.vendorID>0) {
            deleteVendor();
        } else {
            if (!areFieldsEmpty()) {
                Map<String, String> data = extractFields();
                registerNewVendor(data.get("cnpj"), data.get("nomeFantasia"), data.get("razaoSocial"), data.get("email"), data.get("celular"), data.get("cep"), data.get("rua"), data.get("numero"), data.get("cidade"), data.get("estado"));
            }
        }
    }//GEN-LAST:event_ButtonDeleteActionPerformed
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
            java.util.logging.Logger.getLogger(AdminVendorManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminVendorManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminVendorManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminVendorManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminVendorManagerScreen(0,0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundRegister;
    private javax.swing.JLabel BackgroundUpdate;
    private javax.swing.JButton ButtonBack;
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonUpdate;
    private javax.swing.JTextField FieldAddress;
    private javax.swing.JTextField FieldAddressNumber;
    private javax.swing.JFormattedTextField FieldCEP;
    private javax.swing.JFormattedTextField FieldCNPJ;
    private javax.swing.JFormattedTextField FieldCelular;
    private javax.swing.JTextField FieldCity;
    private javax.swing.JTextField FieldEmail;
    private javax.swing.JComboBox<String> FieldEstate;
    private javax.swing.JTextField FieldNomeFantasia;
    private javax.swing.JTextField FieldRazaoSocial;
    private javax.swing.JPanel Panel;
    // End of variables declaration//GEN-END:variables
}
