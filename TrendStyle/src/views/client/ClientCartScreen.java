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
public class ClientCartScreen extends javax.swing.JFrame {
    communication dbAccess = new communication();
    int ID = 0;
    double clientBalance = 0;
    double cartValue = 0;
    int productID = 0;
    
    public boolean updateTable() {
        DefaultTableModel tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nome", "Valor", "Quantidade", "Total", "Imagem"}, 0
        ) {
            // Making cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ArrayList<ArrayList<?>> cartItems = dbAccess.collectShoppingCartItems(this.ID);
        ArrayList<Integer> idClientes = (ArrayList<Integer>) cartItems.get(0);
        ArrayList<Integer> idProdutos = (ArrayList<Integer>) cartItems.get(1);
        ArrayList<String> nomes = (ArrayList<String>) cartItems.get(2);
        ArrayList<Double> valores = (ArrayList<Double>) cartItems.get(3);
        ArrayList<Integer> quantidades = (ArrayList<Integer>) cartItems.get(4);
        ArrayList<Double> totais = (ArrayList<Double>) cartItems.get(5);
        ArrayList<String> imagens = (ArrayList<String>) cartItems.get(6);

        // Iterating in reverse order
        for (int i = idProdutos.size() - 1; i >= 0; i--) {
            int idProduto = idProdutos.get(i);
            String nome = nomes.get(i);
            double valor = valores.get(i);
            int quantidade = quantidades.get(i);
            double total = totais.get(i);
            total = Double.parseDouble(new DecimalFormat("#.00").format(total));
            String imagem = imagens.get(i);

            tableModel.addRow(new Object[]{idProduto, nome, valor, quantidade, total, imagem});
        }

        Table = new JTable(tableModel);
        Table.getColumnModel().getColumn(5).setMinWidth(0);
        Table.getColumnModel().getColumn(5).setMaxWidth(0);
        Table.getColumnModel().getColumn(5).setPreferredWidth(0);
        ScrollPane.setViewportView(Table);
        
        Table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int linhaSelecionada = Table.getSelectedRow();
                    productID = (int) Table.getValueAt(linhaSelecionada,0);
                    String img = Table.getValueAt(linhaSelecionada, 5).toString();
                    String name = Table.getValueAt(linhaSelecionada, 1).toString();
                    String value = Table.getValueAt(linhaSelecionada, 2).toString();
                    String amount = Table.getValueAt(linhaSelecionada, 3).toString();
                    loadImage(img);
                    Name.setText(name);
                    Value.setText(value);
                    Amount.setText(amount);
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
        if (cartSummary.size() >= 2) {
            this.cartValue = (double) cartSummary.get(1);
            if (clientBalance<cartValue) {
                JOptionPane.showMessageDialog(null, 
                    "<html><body><p style='width: 200px;'>Olá! Parece que seu carrinho está recheado de ótimos itens, somando um total de " 
                    + String.format("%.2f", this.cartValue) 
                    + " reais. <br><br>Para finalizar a compra, você precisará adicionar mais "
                    + String.format("%.2f", this.cartValue - this.clientBalance)
                    + " reais à sua conta, ou talvez remover alguns itens do carrinho. Obrigado!</p></body></html>", 
                    "Atualização do Carrinho", 
                    JOptionPane.WARNING_MESSAGE);
            }
            CartValue.setText(String.valueOf(cartValue));
        }
        updateTable();
    }

    /**
     * Creates new form ClientCartScreen
     */
    public ClientCartScreen(int _ID) {
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
        CartValue = new javax.swing.JLabel();
        ScrollPane = new javax.swing.JScrollPane();
        Table = new javax.swing.JTable();
        ButtonDelete = new javax.swing.JButton();
        ButtonUpdate = new javax.swing.JButton();
        ButtonConfirm = new javax.swing.JButton();
        ButtonReset = new javax.swing.JButton();
        ProductImage = new javax.swing.JLabel();
        Name = new javax.swing.JLabel();
        Value = new javax.swing.JLabel();
        Amount = new javax.swing.JTextField();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TrendStyle - Client Cart");

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
        Panel.add(Balance, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 360, 40));

        CartValue.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        CartValue.setText("0");
        Panel.add(CartValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 420, 270, 40));

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

        Panel.add(ScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 137, 560, 270));

        ButtonDelete.setBorderPainted(false);
        ButtonDelete.setContentAreaFilled(false);
        ButtonDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonDeleteActionPerformed(evt);
            }
        });
        Panel.add(ButtonDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(685, 370, 98, 35));

        ButtonUpdate.setBorderPainted(false);
        ButtonUpdate.setContentAreaFilled(false);
        ButtonUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonUpdateActionPerformed(evt);
            }
        });
        Panel.add(ButtonUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(787, 370, 95, 35));

        ButtonConfirm.setBorderPainted(false);
        ButtonConfirm.setContentAreaFilled(false);
        ButtonConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonConfirmActionPerformed(evt);
            }
        });
        Panel.add(ButtonConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 470, 300, 50));

        ButtonReset.setBorderPainted(false);
        ButtonReset.setContentAreaFilled(false);
        ButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonResetActionPerformed(evt);
            }
        });
        Panel.add(ButtonReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 460, 290, 60));
        Panel.add(ProductImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 170, 170));
        Panel.add(Name, new org.netbeans.lib.awtextra.AbsoluteConstraints(744, 283, 150, 21));
        Panel.add(Value, new org.netbeans.lib.awtextra.AbsoluteConstraints(749, 311, 147, 20));

        Amount.setBorder(null);
        Amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AmountActionPerformed(evt);
            }
        });
        Panel.add(Amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(809, 339, 87, 20));

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/media/clientCart.png"))); // NOI18N
        Panel.add(Background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 960, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Panel, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonBackActionPerformed
        ClientPanel page = new ClientPanel(this.ID);
        page.setVisible(true);
        dispose();
    }//GEN-LAST:event_ButtonBackActionPerformed

    private void ButtonConfirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonConfirmActionPerformed
        if (this.clientBalance < this.cartValue) {
            JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Olá! Detectamos que o total de seus itens selecionados é de " 
                + String.format("%.2f", this.cartValue) 
                + " reais. <br><br>No entanto, seu saldo atual é insuficiente para concluir a compra. Você precisa adicionar "
                + String.format("%.2f", this.cartValue - this.clientBalance)
                + " reais à sua conta, ou remover alguns itens do seu carrinho. Obrigado por sua compreensão.</p></body></html>", 
                "Atualização do Carrinho", 
                JOptionPane.WARNING_MESSAGE);
        } else {
            if (this.cartValue > 0) {
                if (dbAccess.makeOrder(this.ID)) {
                    JOptionPane.showMessageDialog(null, 
                    "<html><body><p style='width: 200px;'>Parabéns! Seu pedido foi concluído com sucesso. Agora é só aguardar. Agradecemos pela preferência!</p></body></html>",
                    "Pedido Concluído", 
                    JOptionPane.INFORMATION_MESSAGE);
                    ClientOrderScreen page = new ClientOrderScreen(this.ID);
                    page.setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, 
                    "<html><body><p style='width: 200px;'>Oops! Parece que algo deu errado enquanto tentávamos processar o seu pedido. <br><br>Por favor, verifique se as informações inseridas estão corretas e tente novamente. Se o problema persistir, não hesite em contatar nosso suporte ao cliente. Agradecemos pela compreensão!</p></body></html>",
                    "Erro no Pedido", 
                    JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, 
                "<html><body><p style='width: 200px;'>Oi! Notamos que seu carrinho está vazio. Por que não aproveita e adiciona alguns itens para finalizar a compra? Estamos à disposição.</p></body></html>",
                "Carrinho Vazio", 
                JOptionPane.WARNING_MESSAGE);
            }
        }
    }//GEN-LAST:event_ButtonConfirmActionPerformed

    private void ButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonResetActionPerformed
        int result = JOptionPane.showConfirmDialog(null,
                "<html><body><p style='width: 200px;'>Você tem certeza que deseja excluir o carrinho? Todos os produtos serão removidos, zerando completamente.</p></body></html>",
                "Confirmação de Exclusão do Carrinho",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            // Código para remover todos os produtos do carrinho e zerar completamente
            if (dbAccess.deleteCart(this.ID)) {
                JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>O carrinho foi excluído com sucesso. Todos os produtos foram removidos.</p></body></html>",
                    "Exclusão Concluída",
                    JOptionPane.INFORMATION_MESSAGE);

                ClientCartScreen page = new ClientCartScreen(this.ID);
                page.setVisible(true);
                dispose();
            }
        }
    }//GEN-LAST:event_ButtonResetActionPerformed

    private void ButtonUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonUpdateActionPerformed
        try {
            int amount = Integer.parseInt(Amount.getText());

            if (dbAccess.updateCartProduct(this.ID, this.productID, amount)) {
                ClientCartScreen page = new ClientCartScreen(this.ID);
                page.setVisible(true);
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Quantidade inválida. Por favor, digite um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ButtonUpdateActionPerformed

    private void ButtonDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonDeleteActionPerformed
        if (dbAccess.updateCartProduct(this.ID, this.productID, 0)) {
            JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>O produto foi excluído do carrinho com sucesso.</p></body></html>",
                    "Exclusão Concluída",
                    JOptionPane.INFORMATION_MESSAGE);
            ClientCartScreen page = new ClientCartScreen(this.ID);
            page.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(null,
                    "<html><body><p style='width: 200px;'>Oops! Algo deu errado durante a exclusão do produto do carrinho. Por favor, tente novamente.</p></body></html>",
                    "Erro na Exclusão",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ButtonDeleteActionPerformed

    private void AmountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AmountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AmountActionPerformed

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
            java.util.logging.Logger.getLogger(ClientCartScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientCartScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientCartScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientCartScreen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientCartScreen(0).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Amount;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel Balance;
    private javax.swing.JButton ButtonBack;
    private javax.swing.JButton ButtonConfirm;
    private javax.swing.JButton ButtonDelete;
    private javax.swing.JButton ButtonReset;
    private javax.swing.JButton ButtonUpdate;
    private javax.swing.JLabel CartValue;
    private javax.swing.JLabel Name;
    private javax.swing.JPanel Panel;
    private javax.swing.JLabel ProductImage;
    private javax.swing.JScrollPane ScrollPane;
    private javax.swing.JTable Table;
    private javax.swing.JLabel Value;
    // End of variables declaration//GEN-END:variables
}
