/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academia.gui.usuario.filtro;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 *
 * @author italo
 */
public class UsuarioBuscaPNL extends javax.swing.JPanel {

    /**
     * Creates new form UsuarioBuscaPNL
     */
    public UsuarioBuscaPNL() {
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

        mostrarUsuariosInativosCB = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        nomeTF = new javax.swing.JTextField();
        filtrarBT = new javax.swing.JButton();
        limparBT = new javax.swing.JButton();

        mostrarUsuariosInativosCB.setText("Mostrar usuários inativos");

        jLabel1.setText("Nome:");

        filtrarBT.setText("Filtrar");

        limparBT.setText("Limpar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mostrarUsuariosInativosCB)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(filtrarBT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(limparBT))
                            .addComponent(nomeTF, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mostrarUsuariosInativosCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nomeTF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filtrarBT)
                    .addComponent(limparBT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public JButton getFiltrarBT() {
        return filtrarBT;
    }

    public JButton getLimparBT() {
        return limparBT;
    }

    public JCheckBox getMostrarUsuariosInativosCB() {
        return mostrarUsuariosInativosCB;
    }

    public JTextField getNomeTF() {
        return nomeTF;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton filtrarBT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton limparBT;
    private javax.swing.JCheckBox mostrarUsuariosInativosCB;
    private javax.swing.JTextField nomeTF;
    // End of variables declaration//GEN-END:variables
}
