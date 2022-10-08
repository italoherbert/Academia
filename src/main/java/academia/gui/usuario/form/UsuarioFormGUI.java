package academia.gui.usuario.form;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UsuarioFormGUI extends JDialog implements ActionListener {

    private UsuarioFormPNL formPNL = new UsuarioFormPNL();

    private JLabel tituloL;
    
    private UsuarioFormGUITO to = new UsuarioFormGUITO( this );
    private UsuarioFormGUIListener listener;
    
    public UsuarioFormGUI( Container jp ) {        
        tituloL = new JLabel( "Formulário de Usuário" );        
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) );        
        
        ButtonGroup bg = new ButtonGroup();
        bg.add( formPNL.getAdminRB() );
        bg.add( formPNL.getFuncRB() );
        
        formPNL.getIdTF().setEditable( false ); 
        
        formPNL.getFuncRB().setSelected( true ); 
        
        FlowLayout tituloLayout = new FlowLayout( FlowLayout.CENTER );
        tituloLayout.setVgap( 10 );
        
        JPanel tituloPNL = new JPanel();
        tituloPNL.setLayout( tituloLayout );
        tituloPNL.add( tituloL );
                
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.NORTH, tituloPNL );
        conteudoPNL.add( BorderLayout.CENTER, formPNL );
                
        super.setTitle( "Usuários" );        
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.setContentPane( conteudoPNL );
        super.pack();
        super.setLocationRelativeTo( jp );
        super.setAlwaysOnTop( true );
        super.setModal( true ); 
        super.setModalityType( ModalityType.APPLICATION_MODAL ); 
        
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
            to.limpar();
        } else if ( e.getSource() == formPNL.getSalvarBT() ) {
            listener.salvarBTAcionado( to );
        } else if ( e.getSource() == formPNL.getRemoverBT() ) {
            listener.removerBTAcionado( to );
        }
        
    }    
    
    public void setUsuarioFormListener( UsuarioFormGUIListener listener ) {
        this.listener = listener;
    }
    
    public UsuarioFormGUITO getTO() {
        return to;
    }

    public UsuarioFormPNL getFormPNL() {
        return formPNL;
    }
    
}
