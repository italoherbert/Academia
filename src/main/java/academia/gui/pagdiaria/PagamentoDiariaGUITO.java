package academia.gui.pagdiaria;

import academia.Consts;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTO;

public class PagamentoDiariaGUITO extends SelecionarModsPNLTO {

    private PagamentoDiariaGUI gui;
    
    public PagamentoDiariaGUITO( PagamentoDiariaGUI gui ) {
        super( gui );
        this.gui = gui;        
    }
    
    public void limpar() {
        super.limpar();
        
        this.setTotal( Consts.TEXTO_VASIO ); 
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
    public String getTotal() {
        return gui.getTotalLV().getText();
    }
    
    public void setTotal( String total ) {
        gui.getTotalLV().setText( total ); 
    }

}
