package academia.gui.modalidade.form;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ModalidadeFormGUI extends JDialog 
        implements WindowListener, ActionListener {
        
    private ModalidadeFormPNL formPNL = new ModalidadeFormPNL();
    
    private JLabel tituloL;
    
    private ModalidadeFormGUITO to = new ModalidadeFormGUITO( this );
    private ModalidadeFormGUIListener listener;

    public ModalidadeFormGUI( Container jp ) {                        
        tituloL = new JLabel( "Formulário de Modalidade", JLabel.CENTER );        
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) );
        
        formPNL.getIDTF().setEditable( false );  
        formPNL.getDataRegistroTF().setEnabled( true );
        formPNL.getDataFimTF().setEditable( false );
        
        FlowLayout tituloLayout = new FlowLayout( FlowLayout.CENTER );
        tituloLayout.setVgap( 10 );
        
        JPanel tituloPNL = new JPanel();
        tituloPNL.setLayout( tituloLayout );
        tituloPNL.add( tituloL );
        
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.NORTH, tituloPNL );
        conteudoPNL.add( BorderLayout.CENTER, formPNL );
        
        super.setTitle( "Modalidades" );
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE ); 
        super.setContentPane( conteudoPNL );         
        super.pack();
        super.setLocationRelativeTo( jp );                                          
        super.setAlwaysOnTop( true ); 
        super.setModal( true );
        super.setModalityType( ModalityType.APPLICATION_MODAL );
        
        super.addWindowListener( this );
        
        formPNL.getFecharBT().addActionListener( this );
        formPNL.getLimparBT().addActionListener( this );
        formPNL.getSalvarBT().addActionListener( this );
        formPNL.getRemoverBT().addActionListener( this ); 
        
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == formPNL.getFecharBT() ) {
            super.setVisible( false ); 
        } else if ( e.getSource() == formPNL.getLimparBT() ) {
            listener.limparBTAcionado( to );
        } else if ( e.getSource() == formPNL.getSalvarBT() ) {
            listener.salvarBTAcionado( to );
        } else if ( e.getSource() == formPNL.getRemoverBT() ) {
            listener.removerBTAcionado( to ); 
        }            
    }
    
    public void windowActivated(WindowEvent we) {
        formPNL.getDescTF().requestFocusInWindow();
    }

    public void windowOpened(WindowEvent we) {}
    public void windowClosing(WindowEvent we) {}
    public void windowClosed(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}    
    
    public ModalidadeFormGUITO getTO() {
        return to;
    }

    public void setModalidadeFormListener( ModalidadeFormGUIListener listener ) {
        this.listener = listener;
    }    

    public ModalidadeFormPNL getFormPNL() {
        return formPNL;
    }
    
}
