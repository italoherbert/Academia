package academia.gui.usuario.filtro;

import academia.Consts;
import libs.gui.tabela.TabelaMD;

public class UsuarioFiltroGUITO {
    
    private UsuarioFiltroGUI gui;
    
    public UsuarioFiltroGUITO( UsuarioFiltroGUI gui ) {
        this.gui = gui;
    }
    
    public void limpar() {
        gui.getUsuarioTBL().getTMD().removeTodasAsLinhas();
        
        this.setNome( Consts.TEXTO_VASIO ); 
    }
    
    public TabelaMD getUsuarioTBLMD() {
        return gui.getUsuarioTBL().getTMD();
    }
    
    public String getNome() {
        return gui.getBuscaPNL().getNomeTF().getText();
    }
    
    public void setNome( String nome ) {
        gui.getBuscaPNL().getNomeTF().setText( nome ); 
    }        
    
    public boolean isMostrarUsuariosInativos() {
        return gui.getBuscaPNL().getMostrarUsuariosInativosCB().isSelected();
    }
    
    public void setMostrarUsuariosInativos( boolean mostrar ) {
        gui.getBuscaPNL().getMostrarUsuariosInativosCB().setSelected( mostrar ); 
    }
    
}
