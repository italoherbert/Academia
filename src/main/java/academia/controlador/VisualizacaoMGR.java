package academia.controlador;

import academia.gui.GUI;

public class VisualizacaoMGR {
    
    private ControladorTO cTO;

    public VisualizacaoMGR(ControladorTO cTO) {
        this.cTO = cTO;
    }
    
    public void configVisuJMod( int uTipo ) {
        GUI gui = cTO.getGUI();
        int admin = cTO.getBDConfig().getAdminUsuarioTipoID();
        int func = cTO.getBDConfig().getFuncUsuarioTipoID();
        
        if( uTipo == admin ) {
            gui.getJP().getModalidadeGUI().getTO().setBotoesPNLVisivel( true );
            gui.getJP().getModalidadeGUI().getFiltroGUI().getTO().setCarregarModalidade( true );
        } else if ( uTipo == func ) {
            gui.getJP().getModalidadeGUI().getTO().setBotoesPNLVisivel( false ); 
            gui.getJP().getModalidadeGUI().getFiltroGUI().getTO().setCarregarModalidade( false );
        }
    }
             
    public void configVisuJP( int uTipo ) {
        GUI gui = cTO.getGUI();
        int admin = cTO.getBDConfig().getAdminUsuarioTipoID();
        int func = cTO.getBDConfig().getFuncUsuarioTipoID();
                    
        if( uTipo == admin ) {
            gui.getJP().getOpcoesPNL().getUsuariosBT().setVisible( true ); 
            gui.getJP().getOpcoesPNL().getRelatoriosBT().setVisible( true ); 
            gui.getJP().getOpcoesPNL().getConfiguracoesBT().setVisible( true ); 
        } else if ( uTipo == func ) {
            gui.getJP().getOpcoesPNL().getUsuariosBT().setVisible( false ); 
            gui.getJP().getOpcoesPNL().getRelatoriosBT().setVisible( false );  
            gui.getJP().getOpcoesPNL().getConfiguracoesBT().setVisible( false );
        }        
    }
    
    public void configVisuEditAluno( int uTipo ) {        
        GUI gui = cTO.getGUI();
        int admin = cTO.getBDConfig().getAdminUsuarioTipoID();
        int func = cTO.getBDConfig().getFuncUsuarioTipoID();
        
        if( uTipo == admin ) {
            gui.getEditAlunoGUI().getBotoesPNL().getRemoverAlunoBT().setVisible( true );
        } else if ( uTipo == func ) {
            gui.getEditAlunoGUI().getBotoesPNL().getRemoverAlunoBT().setVisible( false );
        }        
    }        
    
}
