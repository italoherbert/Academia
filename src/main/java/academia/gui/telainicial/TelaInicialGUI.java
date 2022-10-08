package academia.gui.telainicial;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JWindow;

public class TelaInicialGUI extends JWindow {

    private TelaInicialPNL telaInicialPNL = new TelaInicialPNL();
    
    private TelaInicialGUITO to = new TelaInicialGUITO( this );
    
    public TelaInicialGUI() {        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        
        int w = telaInicialPNL.getImagem().getIconWidth();
        int h = telaInicialPNL.getImagem().getIconHeight();
        int x = ( d.width - w ) / 2;
        int y = ( d.height - h ) / 2;
        
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setLayout( new GridLayout() ); 
        conteudoPNL.add( telaInicialPNL );
        
        super.setContentPane( conteudoPNL ); 
        super.setSize( w, h );
        super.setLocation( x, y );         
    }
    
    public TelaInicialGUITO getTO() {
        return to;
    }

    public TelaInicialPNL getTelaInicialPNL() {
        return telaInicialPNL;
    }
        
}
