package academia.gui.medidas;

import academia.gui.painels.medidas.MedidasFormPNL;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MedidasFormGUI extends JDialog implements ActionListener {
    
    private MedidasFormPNL formPNL = new MedidasFormPNL();
    
    private JLabel tituloL;
    private JButton salvarBT;
    private JButton removerBT;
    private JButton limparBT;
    private JButton fecharBT;
    
    private MedidasFormGUITO to = new MedidasFormGUITO( this );
    private MedidasFormGUIListener listener;
    
    public MedidasFormGUI( Container jp ) {                
        tituloL = new JLabel( "Formulário de medidas" ) ;
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) ); 
                
        salvarBT = new JButton( "Salvar" );
        removerBT = new JButton( "Remover" ); 
        limparBT = new JButton( "Limpar" );
        fecharBT = new JButton( "Fechar" );

        JPanel tituloPNL = new JPanel();
        tituloPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        tituloPNL.add( tituloL );
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesPNL.add( removerBT );
        botoesPNL.add( salvarBT );
        botoesPNL.add( limparBT );
        botoesPNL.add( fecharBT );        
        
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.NORTH, tituloPNL );
        conteudoPNL.add( BorderLayout.CENTER, formPNL );
        conteudoPNL.add( BorderLayout.SOUTH, botoesPNL );
        
        super.setTitle( "Formulário de medidas" );
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.setContentPane( conteudoPNL );                
        super.pack();
        super.setLocationRelativeTo( jp );                 
        super.setAlwaysOnTop( true ); 
        super.setModal( false );
        super.setModalityType( ModalityType.APPLICATION_MODAL );
        
        formPNL.getIDTF().setEditable( false ); 
        
        fecharBT.addActionListener( this );
        salvarBT.addActionListener( this );
        limparBT.addActionListener( this ); 
        removerBT.addActionListener( this ); 
    }

    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == fecharBT ) {
            super.setVisible( false );
        } else if ( e.getSource() == limparBT ) {
            listener.limparBTAcionado( to ); 
        } else if ( e.getSource() == salvarBT ) {
            listener.salvarBTAcionado( to ); 
        } else if ( e.getSource() == removerBT ) {
            listener.removerBTAcionado( to ); 
        }
    }
    
    public void setMedidasFormListener( MedidasFormGUIListener listener ) {
        this.listener = listener;
    }

    public MedidasFormGUITO getTO() {
        return to;
    }
    
    public MedidasFormPNL getFormPNL() {
        return formPNL;
    }

    public JButton getRemoverBT() {
        return removerBT;
    }
    
}
