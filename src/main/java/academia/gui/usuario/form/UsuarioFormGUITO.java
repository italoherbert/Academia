package academia.gui.usuario.form;

import academia.Consts;
import academia.gui.GUI;

public class UsuarioFormGUITO {      
    
    private UsuarioFormGUI gui;
    
    public UsuarioFormGUITO( UsuarioFormGUI gui ) {
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {        
        gui.setVisible( visivel ); 
    }
    
    public void cadConfigVisu() {
        gui.getFormPNL().getSenhaPNL().setVisible( true );
        gui.getFormPNL().getRemoverBT().setEnabled( false ); 
        gui.pack();
    }
    
    public void editConfigVisu() {
        gui.getFormPNL().getSenhaPNL().setVisible( false );
        gui.getFormPNL().getRemoverBT().setEnabled( true ); 
        gui.pack();
    }
    
    public void empacotar() {
        gui.pack();
    }
 
    public void limpar() {
        this.setNome( Consts.TEXTO_VASIO );
        this.setNomeUsuario( Consts.TEXTO_VASIO );
        this.setSenha( Consts.TEXTO_VASIO );
        this.setSenha2( Consts.TEXTO_VASIO );
        this.setFuncRBSelecionado( true ); 
    }        
        
    public String getID() {
        return gui.getFormPNL().getIdTF().getText();
    }
    
    public String getNome() {
        return gui.getFormPNL().getNomeTF().getText();
    }    
    
    public String getNomeUsuario() {
        return gui.getFormPNL().getNomeUsuarioTF().getText();
    }
    
    public char[] getSenha() {
        return gui.getFormPNL().getSenhaTF().getPassword();
    }
    
    public char[] getSenha2() {
        return gui.getFormPNL().getSenha2TF().getPassword();
    }
    
    public boolean isAdminRBSelecionado() {
        return gui.getFormPNL().getAdminRB().isSelected();
    }
    
    public boolean isFuncRBSelecionado() {
        return gui.getFormPNL().getFuncRB().isSelected();
    }
    
    
    
    public void setID( String id ) {
        gui.getFormPNL().getIdTF().setText( id );
    }
    
    public void setNome( String nome ) {
        gui.getFormPNL().getNomeTF().setText( nome );
    }
    
    public void setNomeUsuario( String usuario ) {
        gui.getFormPNL().getNomeUsuarioTF().setText( usuario ); 
    }
    
    public void setSenha( String senha ) {
        gui.getFormPNL().getSenhaTF().setText( senha ); 
    }
    
    public void setSenha2( String senha ) {
        gui.getFormPNL().getSenha2TF().setText( senha ); 
    }
        
    public void setAdminRBSelecionado( boolean selecionado ) {
        gui.getFormPNL().getAdminRB().setSelected( selecionado );
    }
    
    public void setFuncRBSelecionado( boolean selecionado ) {
        gui.getFormPNL().getFuncRB().setSelected( selecionado ); 
    }
    
}
