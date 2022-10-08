package academia.gui.telainicial;

import academia.GUIConsts;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class TelaInicialPNL extends JPanel {
    
    private ImageIcon imagem;
    
    public TelaInicialPNL() {         
        imagem = new ImageIcon( GUIConsts.TELA_INICIAL_IMG );
        int w = imagem.getIconWidth();
        int h = imagem.getIconHeight();
        
        super.setPreferredSize( new Dimension( w, h ) );        
    }
    
    public void paintComponent( Graphics g ) {
        super.paintComponent( g ); 
    
        if ( imagem != null )
            g.drawImage( imagem.getImage(), 0, 0, this );        
    }
    
    public ImageIcon getImagem() {
        return imagem;
    }
    
}
