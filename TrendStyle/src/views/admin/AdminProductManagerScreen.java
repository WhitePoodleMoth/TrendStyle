/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.admin;

import communication.communication;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Unknown Account
 */
public class AdminProductManagerScreen extends javax.swing.JFrame {
    communication dbAccess = new communication();
    int ID = 0;
    int productID = 0;
    Map<String, Integer> categoriasMap = new HashMap<>();
    Map<String, Integer> fornecedoresMap = new HashMap<>();
    
    public void updateCategory(String categorySystem) {
        DefaultComboBoxModel<String> comboBoxModelCategory = new DefaultComboBoxModel<>();
        
        ArrayList<ArrayList<?>> categories = dbAccess.collectCategories();
        ArrayList<String> c_ids = (ArrayList<String>) categories.get(0);
        ArrayList<String> c_nomes = (ArrayList<String>) categories.get(1);
        
        comboBoxModelCategory.addElement("");
        for (int i = c_ids.size() - 1; i >= 0; i--) {
            int id = Integer.parseInt(c_ids.get(i));
            String nome = c_nomes.get(i);
            comboBoxModelCategory.addElement(nome);
            categoriasMap.put(nome, id);
        }
        //String nomeSelecionado = (String) FieldCategoria.getSelectedItem();
        //int idSelecionado = categoriasMap.get(nomeSelecionado);

        FieldCategory.setModel(comboBoxModelCategory);

        String categorySelected = "";
        if (this.productID>0) {
            try {
                FieldCategory.setSelectedItem(categorySystem);
                categorySelected = categorySystem;
            } catch (Exception ex) {}
        }
        
        FieldCategory.setSelectedItem(categorySelected);
    }
    
    public void updateVendor(String vendorSystem) {
        DefaultComboBoxModel<String> comboBoxModelVendor = new DefaultComboBoxModel<>();
        
        ArrayList<ArrayList<?>> vendors = dbAccess.collectVendors();
        ArrayList<String> v_ids = (ArrayList<String>) vendors.get(0);
        ArrayList<String> v_nomes = (ArrayList<String>) vendors.get(3);
        
        comboBoxModelVendor.addElement("");
        for (int i = v_ids.size() - 1; i >= 0; i--) {
            int id = Integer.parseInt(v_ids.get(i));
            String nome = v_nomes.get(i);
            comboBoxModelVendor.addElement(nome);
            fornecedoresMap.put(nome, id);
        }
        
        FieldVendor.setModel(comboBoxModelVendor);
        
        String vendorSelected = "";
        if (this.productID>0) {
            try {
                FieldVendor.setSelectedItem(vendorSystem);
                vendorSelected = vendorSystem;
            } catch (Exception ex) {}
        }
        
        FieldVendor.setSelectedItem(vendorSelected);
    }
    
    public void insertData() {
        ArrayList productData = dbAccess.collectProductData(this.productID);
        FieldName.setText((String) productData.get(0));
        FieldDescription.setText((String) productData.get(1));
        String url = (String) productData.get(2);
        FieldURL.setText(url);
        loadImage(url);
        FieldValue.setText((String) productData.get(3));
        FieldStock.setText((String) productData.get(4));
        
        updateCategory((String) productData.get(5));
        updateVendor((String) productData.get(6));
        
    }
    
    private void loadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);

            int labelWidth = ProductImg.getWidth();
            int labelHeight = ProductImg.getHeight();
            Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            ProductImg.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void deleteProduct() {
        int result = JOptionPane.showConfirmDialog(null,
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja excluir este produto? Esta ação é irreversível.</p></body></html>",
            "Confirmação de Exclusão",
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            if (dbAccess.deleteProduct(this.productID)) {
                JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>O produto foi excluído com sucesso.</p></body></html>",
                    "Exclusão Concluída",
                    JOptionPane.INFORMATION_MESSAGE);
                AdminProductScreen page = new AdminProductScreen(this.ID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante a exclusão do produto. Por favor, tente novamente.</p></body></html>",
                    "Erro na Exclusão",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void updateProduct() {
        int result = JOptionPane.showConfirmDialog(null, 
            "<html><body><p style='width: 200px;'>Você tem certeza que deseja atualizar o produto?</p></body></html>", 
            "Confirmação de Atualização", 
            JOptionPane.YES_NO_OPTION);

        if(result == JOptionPane.YES_OPTION) {
            String name = FieldName.getText();
            String description = FieldDescription.getText();
            String image = FieldURL.getText();
            String value = FieldValue.getText();
            String stock = FieldStock.getText();
            
//            String vendorText = (String) FieldVendor.getSelectedItem();
//            int vendor = categoriasMap.get(vendorText);
//            
//            String categoryText = (String) FieldCategory.getSelectedItem();
//            int category = categoriasMap.get(categoryText);
            
            if(dbAccess.updateProductDetails(this.productID, name, description, image, value, stock, 0, 0)) {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>O produto foi atualizado com sucesso!</p></body></html>",
                "Atualização Concluída", 
                JOptionPane.INFORMATION_MESSAGE);
                AdminProductManagerScreen page = new AdminProductManagerScreen(this.ID, this.productID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante a atualização do produto. Por favor, tente novamente.</p></body></html>",
                "Erro na Atualização", 
                JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void registerNewProduct() {
        int result = JOptionPane.showConfirmDialog(null,
                "<html><body><p style='width: 200px;'>Você tem certeza que deseja registrar um novo produto?</p></body></html>",
                "Confirmação de Registro",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            String name = FieldName.getText();
            String description = FieldDescription.getText();
            String image = FieldURL.getText();
            String value = FieldValue.getText();
            String stock = FieldStock.getText();

            String vendorText = (String) FieldVendor.getSelectedItem();
            String categoryText = (String) FieldCategory.getSelectedItem();

//            Integer vendor = fornecedoresMap.get(vendorText);
//            Integer category = categoriasMap.get(categoryText);
//
//            int vendorID = vendor != null ? vendor.intValue() : 0;
//            int categoryID = category != null ? category.intValue() : 0;


            if (dbAccess.registerProduct(name, description, image, value, stock, 0, 0)) {
                JOptionPane.showMessageDialog(null,
                        "<html><body><p style='width: 200px;'>O produto foi registrado com sucesso!</p></body></html>",
                        "Registro Concluído",
                        JOptionPane.INFORMATION_MESSAGE);
                AdminProductScreen page = new AdminProductScreen(this.ID);
                page.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null,
                        "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante o registro do produto. Por favor, tente novamente.</p></body></html>",
                        "Erro no Registro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    /**
     * Creates new form AdminProductManagerScreen
     */
    public AdminProductManagerScreen(int _ID, int _productID) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../media/TrendStyleIcon.png")));
        setLocationRelativeTo(null);
        this.ID = _ID;
        this.productID = _productID;
        
        if (this.productID>0) {
            BackgroundUpdate.setVisible(true);
            BackgroundRegister.setVisible(false);
            insertData();
        } else {
            BackgroundUpdate.setVisible(false);
            BackgroundRegister.setVisible(true);
            updateCategory("");
            updateVendor("");
        }
        
        
        FieldURL.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String url = FieldURL.getText();
                if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".png")) {
                    loadImage(url);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                String url = FieldURL.getText();
                if (url.toLowerCase().endsWith(".jpg") || url.toLowerCase().endsWith(".png")) {
                    loadImage(url);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                
            }
        });
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
        ProductImg = new javax.swing.JLabel();
        FieldName = new javax.swing.JTextField();
        FieldDescription = new javax.swing.JTextField();
        FieldCategory = new javax.swing.JComboBox<>();
        FieldVendor = new javax.swing.JComboBox<>();
        FieldURL = new javax.swing.JTextField();
        FieldValue = new javax.swing.JTextField();
        FieldStock = new javax.swing.JTextField();
        ButtonUpdate = new javax.swing.JButton();
        ButtonDelete = new javax.swing.JButton();
        BackgroundUpdate = new javax.swing.JLabel();
        BackgroundRegister = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TrendStyle - Admin Product Manager");
        setResizable(false);

        Panel.setBackground(new java.awt.Color(255, 255, 255));
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
        Panel.add(ProductImg, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 145, 260, 260));

        FieldName.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldName.setBorder(null);
        Panel.add(FieldName, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 152, 542, 31));

        FieldDescription.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        FieldDescription.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        FieldDescription.setBorder(null);
        Panel.add(FieldDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 248, 542, 66));

        FieldCategory.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Panel.add(FieldCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(53, 374, 271, 41));

        FieldVendor.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Panel.add(FieldVendor, new org.netbeans.lib.awtextra.AbsoluteConstraints(333, 374, 271, 41));

        FieldURL.setBorder(null);
        FieldURL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FieldURLActionPerformed(evt);
            }
        });
        Panel.add(FieldURL, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 422, 218, 20));

        FieldValue.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        FieldValue.setBorder(null);
        Panel.add(FieldValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 474, 168, 30));

        FieldStock.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        FieldStock.setBorder(null);
        Panel.add(FieldStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(244, 474, 168, 30));

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
        Panel.add(ButtonUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(653, 460, 130, 52));

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
        Panel.add(ButtonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(786, 460, 120, 50));

        BackgroundUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/adminProductUpdate.png"))); // NOI18N
        Panel.add(BackgroundUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        BackgroundRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/adminProductRegister.png"))); // NOI18N
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

    private void ButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBackActionPerformed
        communication dbAccess = new communication();
        AdminProductScreen page = new AdminProductScreen(this.ID);
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonBackActionPerformed

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
        if (this.productID>0) {
            deleteProduct();
        } else {
            registerNewProduct();
        }
    }//GEN-LAST:event_ButtonDeleteActionPerformed

    private void ButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonUpdateActionPerformed
        if (this.productID>0) {
            updateProduct();
        } else {
            registerNewProduct();
        }
        
    }//GEN-LAST:event_ButtonUpdateActionPerformed

    private void FieldURLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FieldURLActionPerformed
        
    }//GEN-LAST:event_FieldURLActionPerformed

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
            java.util.logging.Logger.getLogger(AdminProductManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AdminProductManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AdminProductManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AdminProductManagerScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AdminProductManagerScreen(0,0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BackgroundRegister;
    private javax.swing.JLabel BackgroundUpdate;
    private javax.swing.JButton ButtonBack;
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonUpdate;
    private javax.swing.JComboBox<String> FieldCategory;
    private javax.swing.JTextField FieldDescription;
    private javax.swing.JTextField FieldName;
    private javax.swing.JTextField FieldStock;
    private javax.swing.JTextField FieldURL;
    private javax.swing.JTextField FieldValue;
    private javax.swing.JComboBox<String> FieldVendor;
    private javax.swing.JPanel Panel;
    private javax.swing.JLabel ProductImg;
    // End of variables declaration//GEN-END:variables
}
