package academia.gui.relatorio;

public class RelatorioGUITO {
    
    private RelatorioGUI gui;
    
    public RelatorioGUITO( RelatorioGUI gui ) {
        this.gui = gui;
    }
    
    public String getDataDia() {
        return gui.getRelatorioPNL().getDataRelDiaTF().getText();
    }
    
    public void setDataDia( String data ) {
        gui.getRelatorioPNL().getDataRelDiaTF().setText( data ); 
    }
    
}
