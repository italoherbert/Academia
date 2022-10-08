package academia.gui.pag;

import academia.GUIConsts;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import libs.gui.tabela.TabelaM1;

public class PagamentoGUI extends JFrame 
        implements ActionListener, ItemListener, MouseListener, CaretListener {
        
    private TabelaM1 pgsTBL = new TabelaM1();            
    private JCheckBox esconderParcelasPagasCB;    
    
    private QuitarDebPNL quitarDebPNL = new QuitarDebPNL();

    private JLabel pagoAteL;
    private JLabel pagoAteLV;
    
    private JLabel diaPagL;
    private JLabel diaPagLV;
    
    private JButton removerUltPagBT;
    private JButton editarParcelaBT;
    private JButton fecharBT;
        
    private PagamentoGUITO to = new PagamentoGUITO( this );
    private PagamentoGUIListener listener;
        
    public PagamentoGUI( Container jp ) {                    
        esconderParcelasPagasCB = new JCheckBox( "Esconder parcelas pagas" );
        
        removerUltPagBT = new JButton( "Remover ultimo pagamento" );
        editarParcelaBT = new JButton( "Editar" );
        fecharBT = new JButton( "Fechar" );
                
        pagoAteL = new JLabel( "Pago ate: " );
        pagoAteLV = new JLabel();
        
        diaPagL = new JLabel( "Dia de pagamento: " );
        diaPagLV = new JLabel();
                
        quitarDebPNL.getProxDebTF().setEditable( false );
        
        pagoAteLV.setForeground( GUIConsts.COR_VALOR ); 
        diaPagLV.setForeground( GUIConsts.COR_VALOR );
        
        quitarDebPNL.getProxDebTF().setForeground( GUIConsts.COR_VALOR ); 
        quitarDebPNL.getTotalDebsLV().setForeground( GUIConsts.COR_VALOR ); 
        quitarDebPNL.getTotalProxDebTF().setForeground( GUIConsts.COR_VALOR ); 
                        
        pgsTBL.getTMD().addColuna( "ID", 50 );
        pgsTBL.getTMD().addColuna( "Funcionario", 150 ); 
        pgsTBL.getTMD().addColuna( "Data de parcela", 150 ); 
        pgsTBL.getTMD().addColuna( "Data de vencimento", 160 ); 
        pgsTBL.getTMD().addColuna( "Data de pagamento", 160 ); 
        pgsTBL.getTMD().addColuna( "Total", 100 );
        pgsTBL.getTMD().redimensionaColunas();
       
        JPanel pagoAtePNL = new JPanel();
        pagoAtePNL.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        pagoAtePNL.add( pagoAteL );
        pagoAtePNL.add( pagoAteLV );                        
        
        JPanel diaPagPNL = new JPanel();
        diaPagPNL.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        diaPagPNL.add( diaPagL );
        diaPagPNL.add( diaPagLV );                

        FlowLayout hgab20 = new FlowLayout( FlowLayout.LEFT );
        hgab20.setHgap( 20 );
        
        JPanel dataPagsPNL = new JPanel( hgab20 );
        dataPagsPNL.add( pagoAtePNL );
        dataPagsPNL.add( diaPagPNL );
        
        JPanel esconderParcPGsPNL = new JPanel( hgab20 );
        esconderParcPGsPNL.add( esconderParcelasPagasCB );
        
        JPanel tabelaSulPNL = new JPanel();
        tabelaSulPNL.setLayout( new BoxLayout( tabelaSulPNL, BoxLayout.Y_AXIS ) ); 
        tabelaSulPNL.add( dataPagsPNL );
        tabelaSulPNL.add( esconderParcPGsPNL );
        
        JPanel tabelaPNL = new JPanel();
        tabelaPNL.setBorder( new TitledBorder( "Tabela de parcelas" ) );
        tabelaPNL.setLayout( new BorderLayout() );
        tabelaPNL.add( BorderLayout.CENTER, pgsTBL );        
        tabelaPNL.add( BorderLayout.SOUTH, tabelaSulPNL );
                
        JPanel psul1 = new JPanel();
        psul1.setLayout( new FlowLayout( FlowLayout.LEFT ) ); 
        psul1.add( removerUltPagBT );
        
        JPanel psul2 = new JPanel();
        psul2.setBorder( BorderFactory.createTitledBorder( "" ) );
        psul2.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        psul2.add( editarParcelaBT );
        psul2.add( fecharBT );
        
        JPanel c = new JPanel();
        c.setLayout( new BoxLayout( c, BoxLayout.Y_AXIS ) ); 
        c.add( tabelaPNL );                        
        c.add( quitarDebPNL );
        c.add( psul1 );
        c.add( psul2 );
        
        super.setTitle( "Parcelas do aluno" ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE ); 
        super.setContentPane( c ); 
        super.setSize( 700, 700 );
        super.setLocationRelativeTo( jp );                                   
        super.setAlwaysOnTop( true );
        
        esconderParcelasPagasCB.addItemListener( this ); 
        
        quitarDebPNL.getQuantParcelasDebsTF().addCaretListener( this );
        quitarDebPNL.getDescontoProxDebTF().addCaretListener( this );
        quitarDebPNL.getDescontoDebsTF().addCaretListener( this ); 
        
        quitarDebPNL.getQuitarProxDebBT().addActionListener( this ); 
        quitarDebPNL.getQuitarDebsBT().addActionListener( this );                  
        
        removerUltPagBT.addActionListener( this );       
        editarParcelaBT.addActionListener( this ); 
        fecharBT.addActionListener( this );
        
        pgsTBL.getJTable().addMouseListener( this ); 
    }
            
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
                
        if ( e.getSource() == quitarDebPNL.getQuitarProxDebBT() ) {
            listener.quitarProxDebitoBTAcionado( to );
        } else if ( e.getSource() == quitarDebPNL.getQuitarDebsBT() ) {
            listener.quitarDebitosBTAcionado( to ); 
        } else if ( e.getSource() == removerUltPagBT ) {
            listener.removerUltPagBTAcionado( to ); 
        } else if ( e.getSource() == editarParcelaBT ) {
            listener.editarParcelaFuncAcionada( to ); 
        } else if ( e.getSource() == fecharBT ) {
            super.setVisible( false ); 
        }
    }
    
    public void itemStateChanged( ItemEvent e ) {
        if ( listener == null )
            return;

        listener.esconderParcelasPagasCBAlterado( to ); 
    }

    public void caretUpdate( CaretEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == quitarDebPNL.getDescontoProxDebTF() ) {
            listener.atualizaQuitProxDeb( to ); 
        } else if ( e.getSource() == quitarDebPNL.getQuantParcelasDebsTF() ) {
            listener.atualizaQuitDebs( to ); 
        } else if ( e.getSource() == quitarDebPNL.getDescontoDebsTF() ) {
            listener.atualizaQuitDebs( to ); 
        }
    }
    
    public void mouseClicked( MouseEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == pgsTBL.getJTable() ) {
            if ( e.getClickCount() == 2 )
                listener.editarParcelaFuncAcionada( to ); 
        }
    }
    
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
    
    
    public PagamentoGUITO getTO() {
        return to;
    }
    
    public void setPagListener(PagamentoGUIListener listener) {
        this.listener = listener;
    }
        
    public TabelaM1 getPGsTBL() {
        return pgsTBL;
    }

    public JCheckBox getEsconderParcelasPagasCB() {
        return esconderParcelasPagasCB;
    }

    public QuitarDebPNL getQuitarDebPNL() {
        return quitarDebPNL;
    }

    public JLabel getPagoAteLV() {
        return pagoAteLV;
    }
    
    public JLabel getDiaPagLV() {
        return diaPagLV;
    }
    
}