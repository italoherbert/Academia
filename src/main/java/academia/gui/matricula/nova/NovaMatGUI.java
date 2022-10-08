package academia.gui.matricula.nova;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NovaMatGUI extends JDialog implements ActionListener {

    private JLabel tituloL;
    
    private NovaMatPNL formPNL = new NovaMatPNL();
    
    private NovaMatTO to = new NovaMatTO( this );
    private NovaMatGUIListener listener;
    
    public NovaMatGUI( Container jp ) {
        tituloL = new JLabel( "Nova matrícula" );
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) ); 
        
        formPNL.getCopiarModsCB().setSelected( true ); 
        
        JPanel tP = new JPanel();
        tP.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        tP.add( tituloL );
        
        JPanel pnl = new JPanel();
        pnl.setLayout( new BorderLayout() ); 
        pnl.add( BorderLayout.NORTH, tP );
        pnl.add( BorderLayout.CENTER, formPNL );

        super.setTitle( "Criação de matrícula" );
        super.setContentPane( pnl );
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.pack();
        super.setLocationRelativeTo( jp );
        
        formPNL.getCriarMatBT().addActionListener( this );
        formPNL.getCancelarBT().addActionListener( this );                 
    }
    
    public void actionPerformed(ActionEvent e) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == formPNL.getCriarMatBT() ) {
            listener.criarBTAcionado( to ); 
        } else if ( e.getSource() == formPNL.getCancelarBT() ) {
            super.setVisible( false ); 
        }
    }
    
    public void setNovaMatListener( NovaMatGUIListener listener ) {
        this.listener = listener;
    }
    
    public NovaMatTO getTO() {
        return to;
    }
    
    public NovaMatPNL getFormPNL() {
        return formPNL;
    }               
    
}
