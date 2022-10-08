/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academia.gui.modalidade.filtro;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 *
 * @author italo
 */
public class ModalidadeFiltroPNL extends javax.swing.JPanel {

    /**
     * Creates new form ProdutoFiltroPNL
     */
    public ModalidadeFiltroPNL() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        descricaoTF = new javax.swing.JTextField();
        filtrarBT = new javax.swing.JButton();
        limparBT = new javax.swing.JButton();
        mostrarModsInativasCB = new javax.swing.JCheckBox();

        jLabel1.setText("Descrição:");

        filtrarBT.setText("Filtrar");

        limparBT.setText("Limpar");

        mostrarModsInativasCB.setText("Mostrar modalidades inativas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(filtrarBT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(limparBT))
                            .addComponent(descricaoTF, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(mostrarModsInativasCB))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mostrarModsInativasCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(descricaoTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filtrarBT)
                    .addComponent(limparBT))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    public JTextField getDescricaoTF() {
        return descricaoTF;
    }

    public JButton getFiltrarBT() {
        return filtrarBT;
    }

    public JButton getLimparBT() {
        return limparBT;
    }

    public JCheckBox getMostrarModsInativasCB() {
        return mostrarModsInativasCB;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField descricaoTF;
    private javax.swing.JButton filtrarBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton limparBT;
    private javax.swing.JCheckBox mostrarModsInativasCB;
    // End of variables declaration//GEN-END:variables
}
