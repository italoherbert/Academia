package academia.gui.usuario.alterarsenha;

import academia.Consts;

public class AlterarSenhaGUITO {
    
    private AlterarSenhaGUI gui;
    
    public AlterarSenhaGUITO( AlterarSenhaGUI gui ) {
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
    public void limpar() {
        this.setSenha( Consts.TEXTO_VASIO );
        this.setSenha2( Consts.TEXTO_VASIO ); 
    }
    
    public String getNomeUsuario() {
        return gui.getAlterarSenhaPNL().getNomeUsuarioLV().getText();
    }
    
    public char[] getSenha() {
        return gui.getAlterarSenhaPNL().getNovaSenhaTF().getPassword();
    }
    
    public char[] getSenha2() {
        return gui.getAlterarSenhaPNL().getNovaSenha2TF().getPassword();
    }
    
    public void setNomeUsuario( String nomeUsuario ) {
        gui.getAlterarSenhaPNL().getNomeUsuarioLV().setText( nomeUsuario ); 
    }
    
    public void setSenha( String senha ) {
        gui.getAlterarSenhaPNL().getNovaSenhaTF().setText( senha ); 
    }
    
    public void setSenha2( String senha ) {
        gui.getAlterarSenhaPNL().getNovaSenha2TF().setText( senha ); 
    }
    
}
