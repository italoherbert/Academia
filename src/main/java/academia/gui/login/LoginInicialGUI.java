package academia.gui.login;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class LoginInicialGUI extends JFrame implements WindowListener {

    private LoginGUI loginGUI = new LoginGUI( this );
    private LoginInicialGUIListener listener;    
    
    public LoginInicialGUI() {        
        super.setTitle( "Login" );
        super.setDefaultCloseOperation( JDialog.EXIT_ON_CLOSE );        
        super.setContentPane( loginGUI );
        super.pack();
        super.setLocationRelativeTo( null );
        super.setResizable( false );  
        
        super.addWindowListener( this ); 
    }

    public void windowClosing( WindowEvent e ) {
        if ( listener == null )
            return;
        
        listener.fecharBTAcionado(); 
    }
    
    public void windowClosed(WindowEvent we) {}    
    public void windowOpened(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowActivated(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}            
    
    public void setLoginInicialListener( LoginInicialGUIListener listener ) {
        this.listener = listener;
        
        loginGUI.setLoginListener( listener ); 
    }            
    
    public LoginGUITO getTO() {
        return loginGUI.getTO();
    }
    
}
