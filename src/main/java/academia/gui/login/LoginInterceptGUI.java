package academia.gui.login;

import javax.swing.JDialog;

public class LoginInterceptGUI extends JDialog {

    private LoginGUI loginGUI = new LoginGUI( this );        
    
    public LoginInterceptGUI() {        
        loginGUI.getLoginPNL().getLogarBT().setText( "Ok" ); 
        
        super.setTitle( "Login" );
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );        
        super.setContentPane( loginGUI );
        super.pack();
        super.setLocationRelativeTo( null );
        super.setAlwaysOnTop( true );                
        super.setModal( true );
        super.setModalityType( ModalityType.APPLICATION_MODAL ); 
    }

    public void setLoginInterceptListener( LoginGUIListener listener ) {
        loginGUI.setLoginListener( listener ); 
    }    
    
    public LoginGUITO getTO() {
        return loginGUI.getTO();
    }
    
}
