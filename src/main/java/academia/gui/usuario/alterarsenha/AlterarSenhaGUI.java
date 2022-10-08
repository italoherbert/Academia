package academia.gui.usuario.alterarsenha;

import academia.GUIConsts;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AlterarSenhaGUI extends JDialog implements ActionListener {

    private AlterarSenhaPNL alterarSenhaPNL = new AlterarSenhaPNL();
    private JLabel tituloL;
    
    private AlterarSenhaGUITO to = new AlterarSenhaGUITO( this );
    private AlterarSenhaGUIListener listener;
    
    public AlterarSenhaGUI( Container jp ) {
        tituloL = new JLabel( "Alteração de Senha" );
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) );
        
        alterarSenhaPNL.getNomeUsuarioLV().setForeground( GUIConsts.COR_VALOR ); 
        
        JPanel tituloPNL = new JPanel();
        tituloPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        tituloPNL.add( tituloL );
        
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.NORTH, tituloPNL );
        conteudoPNL.add( BorderLayout.CENTER, alterarSenhaPNL );
        
        super.setTitle( "Alteração de senha do usuário" );
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.setContentPane( conteudoPNL );
        super.pack();
        super.setLocationRelativeTo( jp );
        
        alterarSenhaPNL.getAlterarBT().addActionListener( this ); 
        alterarSenhaPNL.getLimparBT().addActionListener( this ); 
        alterarSenhaPNL.getFecharBT().addActionListener( this ); 
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        if ( e.getSource() == alterarSenhaPNL.getFecharBT() ) {
            super.setVisible( false ); 
        } else if ( e.getSource() == alterarSenhaPNL.getLimparBT() ) {
            to.limpar();
        } else if ( e.getSource() == alterarSenhaPNL.getAlterarBT() ) {
            listener.alterarBTAcionado( to ); 
        }
    }    
    
    public void setAlterarSenhaListener( AlterarSenhaGUIListener listener ) {
        this.listener = listener;
    }
    
    public AlterarSenhaGUITO getTO() {
        return to;
    }

    public AlterarSenhaPNL getAlterarSenhaPNL() {
        return alterarSenhaPNL;
    }
    
}
