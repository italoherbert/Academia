/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academia.gui.jp;

import javax.swing.JToggleButton;

/**
 *
 * @author Adnaide
 */
public class OpcoesPNL extends javax.swing.JPanel {

    /**
     * Creates new form OpcoesPNL
     */
    public OpcoesPNL() {
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

        jToolBar1 = new javax.swing.JToolBar();
        alunosBT = new javax.swing.JToggleButton();
        modalidadesBT = new javax.swing.JToggleButton();
        usuariosBT = new javax.swing.JToggleButton();
        relatoriosBT = new javax.swing.JToggleButton();
        configuracoesBT = new javax.swing.JToggleButton();
        sairBT = new javax.swing.JToggleButton();

        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setRollover(true);

        alunosBT.setText("Alunos");
        alunosBT.setFocusable(false);
        alunosBT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        alunosBT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(alunosBT);

        modalidadesBT.setText("Modalidades");
        modalidadesBT.setFocusable(false);
        modalidadesBT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        modalidadesBT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(modalidadesBT);

        usuariosBT.setText("Usuarios");
        usuariosBT.setFocusable(false);
        usuariosBT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        usuariosBT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(usuariosBT);

        relatoriosBT.setText("Relatorios");
        relatoriosBT.setFocusable(false);
        relatoriosBT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        relatoriosBT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(relatoriosBT);

        configuracoesBT.setText("Configurações");
        configuracoesBT.setFocusable(false);
        configuracoesBT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        configuracoesBT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(configuracoesBT);

        sairBT.setText("Sair");
        sairBT.setFocusable(false);
        sairBT.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sairBT.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(sairBT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public JToggleButton getAlunosBT() {
        return alunosBT;
    }    

    public JToggleButton getModalidadesBT() {
        return modalidadesBT;
    }

    public JToggleButton getRelatoriosBT() {
        return relatoriosBT;
    }

    public JToggleButton getUsuariosBT() {
        return usuariosBT;
    }
    
    public JToggleButton getConfiguracoesBT() {
        return configuracoesBT;
    }   

    public JToggleButton getSairBT() {
        return sairBT;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton alunosBT;
    private javax.swing.JToggleButton configuracoesBT;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToggleButton modalidadesBT;
    private javax.swing.JToggleButton relatoriosBT;
    private javax.swing.JToggleButton sairBT;
    private javax.swing.JToggleButton usuariosBT;
    // End of variables declaration//GEN-END:variables
}
