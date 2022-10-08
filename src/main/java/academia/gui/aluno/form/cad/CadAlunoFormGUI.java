package academia.gui.aluno.form.cad;

import academia.gui.GUIConfig;
import academia.gui.aluno.form.cad.aluno.CadAlunoGUI;
import academia.gui.aluno.form.cad.medidas.CadMedidasGUI;
import academia.gui.aluno.form.cad.modalidade.CadModalidadeGUI;
import academia.gui.aluno.form.cad.op.OpcaoGUIControle;
import academia.gui.aluno.form.cad.op.OpcoesGUIControle;
import academia.gui.aluno.form.cad.op.OpcoesGUIControleDriver;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CadAlunoFormGUI extends JFrame implements ActionListener, OpcoesGUIControleDriver {
     
    public final static String ALUNO_FORM_CARD = "alunos";
    public final static String MEDIDAS_FORM_CARD = "medidas";
    public final static String MODALIDADES_FORM_CARD = "modalidades";
    
    private CadAlunoGUI cadAlunoGUI = new CadAlunoGUI();
    private CadMedidasGUI cadMedidasGUI = new CadMedidasGUI();
    private CadModalidadeGUI cadModsGUI = new CadModalidadeGUI();        
        
    private JButton cancelarBT;
    private JButton pularMedidasBT;
    private JButton voltarBT;
    private JButton avancarBT;
    private JButton finalizarBT;
    
    private JLabel tituloL;
    
    private CadAlunoFormGUITO to = new CadAlunoFormGUITO( this );
    private CadAlunoFormGUIListener listener;
    
    private JPanel cardPNL;
    
    private CardLayout cardLayout = new CardLayout();
    private String cardAtual = ALUNO_FORM_CARD;
    
    private OpcoesGUIControle opcoesGUICtrl = new OpcoesGUIControle( this );
    private boolean pularMedidas = false;
    
    public CadAlunoFormGUI( Container jp, GUIConfig config ) {       
        tituloL = new JLabel( "Formulario de Alunos" );
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) ); 
                
        cancelarBT = new JButton( "Cancelar" );
        voltarBT = new JButton( "Voltar" );
        avancarBT = new JButton( "Avançar" );
        pularMedidasBT = new JButton( "Pular" );
        finalizarBT = new JButton( "Finalizar" );
                            
        cardPNL = new JPanel();
        cardPNL.setLayout( cardLayout ); 
        
        cardPNL.add( ALUNO_FORM_CARD, cadAlunoGUI );
        opcoesGUICtrl.addOpcao( ALUNO_FORM_CARD, cadAlunoGUI );

        if ( config.isCarregarFuncMedidas() ) {
            cardPNL.add( MEDIDAS_FORM_CARD, cadMedidasGUI );
            opcoesGUICtrl.addOpcao( MEDIDAS_FORM_CARD, cadMedidasGUI );
        } else {
            pularMedidasBT.setVisible( false );             
        }
        
        cardPNL.add( MODALIDADES_FORM_CARD, cadModsGUI );                
        opcoesGUICtrl.addOpcao( MODALIDADES_FORM_CARD, cadModsGUI );                                
    
        FlowLayout tituloLayout = new FlowLayout( FlowLayout.CENTER );
        tituloLayout.setVgap( 10 );
        
        JPanel tituloPNL = new JPanel();
        tituloPNL.setLayout( tituloLayout );
        tituloPNL.add( tituloL );
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setBorder( BorderFactory.createEtchedBorder() ); 
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesPNL.add( voltarBT );
        botoesPNL.add( cancelarBT );
        botoesPNL.add( pularMedidasBT );
        botoesPNL.add( finalizarBT );
        botoesPNL.add( avancarBT );
                
        JPanel painel = new JPanel();
        painel.setLayout( new BorderLayout() );
        painel.add( BorderLayout.NORTH , tituloPNL );
        painel.add( BorderLayout.CENTER, cardPNL );
        painel.add( BorderLayout.SOUTH , botoesPNL );
        
        super.setTitle( "Formulario de alunos" ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );                
        super.setContentPane( painel ); 
        super.pack();
        super.setLocationRelativeTo( jp );         
        
        cadAlunoGUI.getAlunoFormGUI().getFormPNL().getIDTF().setEditable( false ); 
        cadMedidasGUI.getFormPNL().getIDTF().setEditable( false );
        cadMedidasGUI.getFormPNL().getDataRegTF().setEditable( false ); 
        
        voltarBT.addActionListener( this );
        avancarBT.addActionListener( this );
        cancelarBT.addActionListener( this );
        pularMedidasBT.addActionListener( this ); 
        finalizarBT.addActionListener( this );         
        
        opcoesGUICtrl.para( ALUNO_FORM_CARD ); 
    }
        
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == cancelarBT ) {
            super.setVisible( false ); 
        } else if ( e.getSource() == voltarBT ) {
            opcoesGUICtrl.voltar();
        } else if ( e.getSource() == avancarBT ) {            
            boolean avancar = false;
            if ( cardAtual.equals( ALUNO_FORM_CARD ) ) {
                avancar = listener.validaAlunoForm(to );                 
            } else if ( cardAtual.equals( MEDIDAS_FORM_CARD ) ) {
                avancar = listener.validaMedidasForm(to );                  
            }
            if ( avancar )
                opcoesGUICtrl.avancar();                        
        } else if ( e.getSource() == pularMedidasBT ) { 
            if ( cardAtual.equals( MEDIDAS_FORM_CARD ) ) {
                pularMedidas = true;

                opcoesGUICtrl.avancar();
            }
        } else if ( e.getSource() == finalizarBT ) {
            listener.finalizarBTAcionado( to );            
        }
    }                    
        
    public void mostraPNL( int opcaoCorrente, OpcaoGUIControle opcao ) {
        if ( listener == null )
            return;
        
        String card = opcao.getCard();
        cardAtual = card;
        
        voltarBT.setEnabled( true );
        avancarBT.setEnabled( true );
        finalizarBT.setEnabled( true );
        pularMedidasBT.setEnabled( true ); 
        cancelarBT.setEnabled( true ); 
        
        if ( card.equals(ALUNO_FORM_CARD ) ) {
            voltarBT.setEnabled( false ); 
            pularMedidasBT.setEnabled( false ); 
            finalizarBT.setEnabled( false ); 
        } else if ( card.equals(MEDIDAS_FORM_CARD ) ) {
            finalizarBT.setEnabled( false );
            
            pularMedidas = false;
        } else if ( card.equals(MODALIDADES_FORM_CARD ) ) {
            avancarBT.setEnabled( false );
            pularMedidasBT.setEnabled( false ); 
        }
        
        cardLayout.show(cardPNL, card );        
        
        if ( listener != null ) {
            if ( card.equals( MEDIDAS_FORM_CARD ) ) {
                listener.aposMostradoPainelMedidas( to );
            } else if ( card.equals( MODALIDADES_FORM_CARD ) ) {
                listener.aposMostradoPainelModalidades( to ); 
            }
        }
    }
        
    public void setCadAlunoFormListener( CadAlunoFormGUIListener listener ) {
        this.listener = listener;
    }
    
    public CadAlunoFormGUITO getTO() {
        return to;
    }

    public CadAlunoGUI getCadAlunoGUI() {
        return cadAlunoGUI;
    }

    public CadMedidasGUI getCadMedidasGUI() {
        return cadMedidasGUI;
    }

    public CadModalidadeGUI getCadModsGUI() {
        return cadModsGUI;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public String getCardAtual() {
        return cardAtual;
    }

    public boolean isPularMedidas() {
        return pularMedidas;
    }

    public void setPularMedidas(boolean pularMedidas) {
        this.pularMedidas = pularMedidas;
    }            

    public OpcoesGUIControle getOpcoesGUICtrl() {
        return opcoesGUICtrl;
    }
    
}
