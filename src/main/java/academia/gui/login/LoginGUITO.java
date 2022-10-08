package academia.gui.login;

import academia.Consts;

public class LoginGUITO {
        
    private LoginGUI gui;
    
    public LoginGUITO( LoginGUI gui ) {
        this.gui = gui;
    }        
        
    public void setVisivel( boolean b ) {
        gui.getJanela().setVisible( b );                
    }
    
    public void limpar() {
        this.setUsuario( Consts.TEXTO_VASIO );
        this.setSenha( Consts.TEXTO_VASIO ); 
        
        gui.getLoginPNL().getNomeUsuarioTF().requestFocusInWindow();
    }
    
    public String getUsuario() {
        return gui.getLoginPNL().getNomeUsuarioTF().getText();
    }
    
    public char[] getSenha() {
        return gui.getLoginPNL().getSenhaTF().getPassword();
    }
    
    public void setUsuario( String usuario ) {
        gui.getLoginPNL().getNomeUsuarioTF().setText( usuario );
    }
    
    public void setSenha( String senha ) {
        gui.getLoginPNL().getSenhaTF().setText( senha ); 
    }
    
}
