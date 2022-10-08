package academia.gui.modalidade;

import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;

public class ModalidadeGUITO {

    private ModalidadeGUI gui;
    
    public ModalidadeGUITO( ModalidadeGUI gui ) {
        this.gui = gui;
    }
    
    public void setBotoesPNLVisivel( boolean visivel ) {
        gui.getBotoesPNL().setVisible( visivel ); 
    }
    
    public ModalidadeFiltroGUITO getFiltroTO() {
        return gui.getFiltroGUI().getTO();
    }        
    
}
