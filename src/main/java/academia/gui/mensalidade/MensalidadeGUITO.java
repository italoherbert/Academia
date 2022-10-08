package academia.gui.mensalidade;

import libs.gui.tabela.TabelaMD;

public class MensalidadeGUITO {
    
    private MensalidadeGUI gui;

    public MensalidadeGUITO(MensalidadeGUI gui) {
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
    public TabelaMD getMensTBLMD() {
        return gui.getMensTBL().getTMD();
    }
    
    public String getModDesc() {
        return gui.getAtribsPNL().getModDescLV().getText();
    }
    
    public String getValorAtual() {
        return gui.getAtribsPNL().getValorAtualLV().getText();
    }
    
    public void setModDesc( String desc ) {
        gui.getAtribsPNL().getModDescLV().setText( desc ); 
    }        
    
    public void setValorAtual( String valor ) {
        gui.getAtribsPNL().getValorAtualLV().setText( valor );
    }
    
}
