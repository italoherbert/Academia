package libs.gui.listaadd;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

public class ListaAddPNL extends JPanel {
   
    public ListaAddPNL() {
        initComponents();                
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        listasPNL = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        alunoModsLST = new javax.swing.JList<>();
        botoesAddRemPNL = new javax.swing.JPanel();
        addBT = new javax.swing.JButton();
        removeBT = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        outrasModsLST = new javax.swing.JList<>();

        jScrollPane1.setViewportView(alunoModsLST);

        addBT.setText("<");

        removeBT.setText(">");

        javax.swing.GroupLayout botoesAddRemPNLLayout = new javax.swing.GroupLayout(botoesAddRemPNL);
        botoesAddRemPNL.setLayout(botoesAddRemPNLLayout);
        botoesAddRemPNLLayout.setHorizontalGroup(
            botoesAddRemPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoesAddRemPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(botoesAddRemPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addBT)
                    .addComponent(removeBT))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        botoesAddRemPNLLayout.setVerticalGroup(
            botoesAddRemPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoesAddRemPNLLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(addBT)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(removeBT)
                .addGap(40, 40, 40))
        );

        jScrollPane2.setViewportView(outrasModsLST);

        javax.swing.GroupLayout listasPNLLayout = new javax.swing.GroupLayout(listasPNL);
        listasPNL.setLayout(listasPNLLayout);
        listasPNLLayout.setHorizontalGroup(
            listasPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, listasPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botoesAddRemPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        listasPNLLayout.setVerticalGroup(
            listasPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listasPNLLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(listasPNLLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(listasPNLLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(botoesAddRemPNL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listasPNL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(listasPNL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public JButton getAddBT() {
        return addBT;
    }
        
    public JButton getRemoveBT() {
        return removeBT;
    }
       
    public JList<String> getItens1LST() {
        return alunoModsLST;
    }

    public JList<String> getItens2LST() {
        return outrasModsLST;
    }
    
    public JPanel getBotoesAddRemPNL() {
        return botoesAddRemPNL;
    }

    public JPanel getListasPNL() {
        return listasPNL;
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBT;
    private javax.swing.JList<String> alunoModsLST;
    private javax.swing.JPanel botoesAddRemPNL;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel listasPNL;
    private javax.swing.JList<String> outrasModsLST;
    private javax.swing.JButton removeBT;
    // End of variables declaration//GEN-END:variables
}
