package academia.gui.login;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoginGUI extends JPanel implements ActionListener, KeyListener {
        
    private LoginPNL loginPNL = new LoginPNL();
    private JLabel tituloL;
    private Container parente;
    
    private LoginGUITO to = new LoginGUITO( this );
    private LoginGUIListener listener;

    public LoginGUI( Container parente ) {        
        this.parente = parente;
        
        tituloL = new JLabel( "Formulário de login" );
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) ); 
        
        JPanel tituloPNL = new JPanel();
        tituloPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        tituloPNL.add( tituloL );
        
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.NORTH, tituloPNL );
        super.add( BorderLayout.CENTER, loginPNL );                                 
        
        loginPNL.getLogarBT().addActionListener( this ); 
        loginPNL.getLimparBT().addActionListener( this );
        
        loginPNL.getSenhaTF().addKeyListener( this );         
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == loginPNL.getLimparBT() ) {
            to.limpar();
        } else if ( e.getSource() == loginPNL.getLogarBT() ) {
            listener.logarBTAcionado( to ); 
        }
    }
    
    public void keyTyped( KeyEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == loginPNL.getSenhaTF() ) {
            if ( e.getKeyChar() == KeyEvent.VK_ENTER ) {
                 listener.logarBTAcionado( to ); 
            }
        }
    }
        
    public void keyPressed( KeyEvent e ) {}
    public void keyReleased( KeyEvent e ) {}
        
    public void setLoginListener( LoginGUIListener listener ) {
        this.listener = listener;
    }
    
    public LoginGUITO getTO() {
        return to;
    }
    
    public LoginPNL getLoginPNL() {
        return loginPNL;
    }        

    public Container getJanela() {
        return parente;
    }
    
}
