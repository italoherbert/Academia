package academia.gui.mensalidade;

import academia.GUIConsts;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import libs.gui.tabela.TabelaM1;

public class MensalidadeGUI extends JDialog implements ActionListener {
    
    private TabelaM1 mensTBL = new TabelaM1();
    private MensalidadeAtribsPNL atribsPNL = new MensalidadeAtribsPNL();
    
    private JButton alterarValorAtualBT;
    private JButton fecharBT;
    
    private MensalidadeGUITO to = new MensalidadeGUITO( this );
    private MensalidadeGUIListener listener;
    
    public MensalidadeGUI( Container jp ) {       
        alterarValorAtualBT = new JButton( "Alterar valor atual" );
        fecharBT = new JButton( "Fechar" );
        
        atribsPNL.getModDescLV().setForeground( GUIConsts.COR_VALOR ); 
        atribsPNL.getValorAtualLV().setForeground( GUIConsts.COR_VALOR ); 
        
        mensTBL.getTMD().addColuna( "ID", 50 );
        mensTBL.getTMD().addColuna( "Data de alteração", 150 );
        mensTBL.getTMD().addColuna( "Valor", 120 );
        mensTBL.getTMD().redimensionaColunas();
        
        mensTBL.setBorder( new TitledBorder( "Tabela de mensalidades" ) );                    
                
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesPNL.add( alterarValorAtualBT );
        botoesPNL.add( fecharBT );
        
        JPanel sulPNL = new JPanel();
        sulPNL.setLayout( new BoxLayout( sulPNL, BoxLayout.Y_AXIS ) );
        sulPNL.add( atribsPNL );
        sulPNL.add( botoesPNL );
        
        JPanel painel = new JPanel();
        painel.setLayout( new BorderLayout() );
        painel.add( BorderLayout.CENTER, mensTBL );
        painel.add( BorderLayout.SOUTH , sulPNL );
        
        super.setTitle( "Mensalidades" ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.setContentPane( painel );
        super.setSize( 500, 400 );
        super.setLocationRelativeTo( jp );                 
        super.setAlwaysOnTop( true ); 
        super.setModal( false );
        super.setModalityType( ModalityType.APPLICATION_MODAL );                  
        
        alterarValorAtualBT.addActionListener( this ); 
        fecharBT.addActionListener( this ); 
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        if ( e.getSource() == fecharBT ) {
            super.setVisible( false );
        } else if ( e.getSource() == alterarValorAtualBT ) {
            listener.alterarValorBTAcionado( to ); 
        }
    }
    
    public void setMensalidadeListener( MensalidadeGUIListener listener ) {
        this.listener = listener;
    }
    
    public MensalidadeGUITO getTO() {
        return to;
    }    
    
    public TabelaM1 getMensTBL() {
        return mensTBL;
    }

    public MensalidadeAtribsPNL getAtribsPNL() {
        return atribsPNL;
    }   
    
}
