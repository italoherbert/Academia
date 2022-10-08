package academia.gui.historicomods;

import libs.gui.tabela.TabelaMD;

public class HistoricoModsGUITO {
    
    private HistoricoModsGUI gui;
    
    public HistoricoModsGUITO( HistoricoModsGUI gui ) {
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
    public void limpar() {        
        gui.getModsTBL().getTMD().removeTodasAsLinhas();
    }
    
    public TabelaMD getModsTBLMD() {
        return gui.getModsTBL().getTMD();
    }
    
}
