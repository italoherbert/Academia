package academia.gui.jp;

import academia.gui.aluno.AlunoGUITO;
import academia.gui.config.ConfigGUITO;
import academia.gui.modalidade.ModalidadeGUITO;

public class JPGUITO {
    
    private JPGUI gui;
    
    public JPGUITO( JPGUI gui ) {
        this.gui = gui;
    }
        
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }   
    
    public void setUsuarioLogado( String tipo, String nome ) {                    
        gui.getUsuarioLogadoTipoLV().setText( tipo ); 
        gui.getUsuarioLogadoLV().setText( nome ); 
    }        
    
    public AlunoGUITO getAlunoTO() {
        return gui.getAlunoGUI().getTO();
    }
    
    public ModalidadeGUITO getModalidadeTO() {
        return gui.getModalidadeGUI().getTO();
    }
    
    public ConfigGUITO getConfigTO() {
        return gui.getConfigGUI().getTO();
    }
    
    public void selecionarEMostrarAlunoPNL() {
        gui.getOpcoesPNL().getAlunosBT().doClick();
    }
    
    public void selecionarEmostrarModalidadePNL() {
        gui.getOpcoesPNL().getModalidadesBT().doClick();
    }
    
    public void selecionarEmostrarConfigPNL() {
        gui.getOpcoesPNL().getConfiguracoesBT().doClick();
    }
    
}
