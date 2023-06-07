/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package views.client;

import communication.communication;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Unknown Account
 */
public class ClientShopScreen extends javax.swing.JFrame {
    communication dbAccess = new communication();
    int ID = 0;
    double clientBalance = 0;
    int productID = 0;
    
    public boolean updateTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Descricao", "Imagem", "Fornecedor", "Categoria", "Estoque", "Valor"}, 0
        ) {
            // Making cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<ArrayList<?>> shoppingProducts = dbAccess.collectShoppingProducts();
        ArrayList<Integer> ids = (ArrayList<Integer>) shoppingProducts.get(0);
        ArrayList<String> nomes = (ArrayList<String>) shoppingProducts.get(1);
        ArrayList<String> descricoes = (ArrayList<String>) shoppingProducts.get(2);
        ArrayList<String> imagens = (ArrayList<String>) shoppingProducts.get(3);
        ArrayList<String> fornecedores = (ArrayList<String>) shoppingProducts.get(4);
        ArrayList<String> categorias = (ArrayList<String>) shoppingProducts.get(5);
        ArrayList<Integer> estoques = (ArrayList<Integer>) shoppingProducts.get(6);
        ArrayList<Double> valores = (ArrayList<Double>) shoppingProducts.get(7);

        // Iterating in reverse order
        for (int i = ids.size() - 1; i >= 0; i--) {
            int id = ids.get(i);
            String nome = nomes.get(i);
            String descricao = descricoes.get(i);
            String imagem = imagens.get(i);
            String fornecedor = fornecedores.get(i);
            String categoria = categorias.get(i);
            int estoque = estoques.get(i);
            Double valor = valores.get(i);

            tableModel.addRow(new Object[]{id, nome, descricao, imagem, fornecedor, categoria, estoque, valor});
        }

        Table = new JTable(tableModel);
        Table.getColumnModel().getColumn(0).setMinWidth(0);
        Table.getColumnModel().getColumn(0).setMaxWidth(0);
        Table.getColumnModel().getColumn(0).setPreferredWidth(0);
        Table.getColumnModel().getColumn(2).setMinWidth(0);
        Table.getColumnModel().getColumn(2).setMaxWidth(0);
        Table.getColumnModel().getColumn(2).setPreferredWidth(0);
        Table.getColumnModel().getColumn(3).setMinWidth(0);
        Table.getColumnModel().getColumn(3).setMaxWidth(0);
        Table.getColumnModel().getColumn(3).setPreferredWidth(0);
        Table.getColumnModel().getColumn(6).setMinWidth(0);
        Table.getColumnModel().getColumn(6).setMaxWidth(0);
        Table.getColumnModel().getColumn(6).setPreferredWidth(0);
        Table.getColumnModel().getColumn(7).setMinWidth(0);
        Table.getColumnModel().getColumn(7).setMaxWidth(0);
        Table.getColumnModel().getColumn(7).setPreferredWidth(0);
        ScrollPane.setViewportView(Table);
        
        Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int linhaSelecionada = Table.getSelectedRow();
                    productID = (int) Table.getValueAt(linhaSelecionada,0);
                    String name = Table.getValueAt(linhaSelecionada, 1).toString();
                    String description = Table.getValueAt(linhaSelecionada, 2).toString();
                    String img = Table.getValueAt(linhaSelecionada, 3).toString();
                    String vendor = Table.getValueAt(linhaSelecionada, 4).toString();
                    String category = Table.getValueAt(linhaSelecionada, 5).toString();
                    String stock = Table.getValueAt(linhaSelecionada, 6).toString();
                    String value = Table.getValueAt(linhaSelecionada, 7).toString();
                    loadImage(img);
                    Name.setText(name);
                    Description.setText(description);
                    Vendor.setText(vendor);
                    Category.setText(category);
                    Stock.setText(stock);
                    Value.setText(value);
                    Amount.setText("1");
                }
            }
        });
        
        return true;
    }
    
    public void fixDesign() {
        Amount.setOpaque(false);
        Amount.setBackground(new Color(0, 0, 0, 0));
    }
    
    private void loadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);

            int labelWidth = ProductImage.getWidth();
            int labelHeight = ProductImage.getHeight();
            Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);

            ImageIcon imageIcon = new ImageIcon(scaledImage);
            ProductImage.setIcon(imageIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updatePage() {
        ArrayList clientData = dbAccess.collectClientData(this.ID);
        this.clientBalance = (double) clientData.get(3);
        Balance.setText(String.valueOf(this.clientBalance));
        ArrayList cartSummary = dbAccess.collectCartSummary(this.ID);
        updateTable();
    }
    
    /**
     * Creates new form ClientShopScreen
     */
    public ClientShopScreen(int _ID) {
        initComponents();
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("../../media/TrendStyleIcon.png")));
        setLocationRelativeTo(null);
        this.ID = _ID;
        fixDesign();
        updatePage();
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
        Balance = new javax.swing.JLabel();
        ScrollPane = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        ProductImage = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        Description = new javax.swing.JLabel();
        Vendor = new javax.swing.JLabel();
        Category = new javax.swing.JLabel();
        Value = new javax.swing.JLabel();
        Stock = new javax.swing.JLabel();
        Amount = new javax.swing.JTextField();
        ButtonAddProduct = new javax.swing.JButton();
        ButtonCart = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TrendStyle - Client Shop");
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
        Panel.add(ButtonBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 60, 50));

        Balance.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Balance.setText("0");
        Panel.add(Balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 50, 220, 40));

        ScrollPane.setBorder(null);

        Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        ScrollPane.setViewportView(Table);

        Panel.add(ScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(67, 118, 310, 330));
        Panel.add(ProductImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(703, 118, 188, 188));

        Name.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        Panel.add(Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 120, 290, 52));

        Description.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Description.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Panel.add(Description, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 195, 290, 105));
        Panel.add(Vendor, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 320, 290, 26));
        Panel.add(Category, new org.netbeans.lib.awtextra.AbsoluteConstraints(705, 320, 183, 25));
        Panel.add(Value, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 375, 183, 23));
        Panel.add(Stock, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 417, 83, 24));

        Amount.setBorder(null);
        Amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AmountActionPerformed(evt);
            }
        });
        Panel.add(Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(783, 373, 103, 26));

        ButtonAddProduct.setBorderPainted(false);
        ButtonAddProduct.setContentAreaFilled(false);
        ButtonAddProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonAddProductActionPerformed(evt);
            }
        });
        Panel.add(ButtonAddProduct, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 410, 300, 40));

        ButtonCart.setBorderPainted(false);
        ButtonCart.setContentAreaFilled(false);
        ButtonCart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCartActionPerformed(evt);
            }
        });
        Panel.add(ButtonCart, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 450, 170, 40));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/clientShop.png"))); // NOI18N
        Panel.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

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
        ClientPanel page = new ClientPanel(this.ID);
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonBackActionPerformed

    private void AmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AmountActionPerformed

    private void ButtonAddProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonAddProductActionPerformed
        if (this.productID==0) {
            JOptionPane.showMessageDialog(null, "Selecione um produto antes de prosseguir.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            int amount = Integer.parseInt(Amount.getText());

            if (dbAccess.addCartProduct(this.ID, this.productID, amount)) {
                ClientShopScreen page = new ClientShopScreen(this.ID);
                page.setVisible(true);
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida. Por favor, digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ButtonAddProductActionPerformed

    private void ButtonCartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCartActionPerformed
        ClientCartScreen page = new ClientCartScreen(this.ID);
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonCartActionPerformed

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
            java.util.logging.Logger.getLogger(ClientShopScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientShopScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientShopScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientShopScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientShopScreen(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Amount;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel Balance;
    private javax.swing.JButton ButtonAddProduct;
    private javax.swing.JButton ButtonBack;
    private javax.swing.JButton ButtonCart;
    private javax.swing.JLabel Category;
    private javax.swing.JLabel Description;
    private javax.swing.JLabel Name;
    private javax.swing.JPanel Panel;
    private javax.swing.JLabel ProductImage;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JLabel Stock;
    private javax.swing.JTable Table;
    private javax.swing.JLabel Value;
    private javax.swing.JLabel Vendor;
    // End of variables declaration//GEN-END:variables
}
