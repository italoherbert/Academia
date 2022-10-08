package academia.gui.jp;

import academia.GUIConsts;
import academia.gui.aluno.AlunoGUI;
import academia.gui.config.ConfigGUI;
import academia.gui.modalidade.ModalidadeGUI;
import academia.gui.relatorio.RelatorioGUI;
import academia.gui.usuario.UsuarioGUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

public class JPGUI extends JFrame implements ActionListener, WindowListener {

    private final static String VASIO_OP = "vasio";
    private final static String ALUNO_OP = "aluno";
    private final static String MODALIDADE_OP = "modalidade";
    private final static String USUARIO_OP = "usuario";
    private final static String RELATORIO_OP = "relatorio";
    private final static String CONFIG_OP = "config";
    
    private AlunoGUI alunoGUI = new AlunoGUI();
    private ModalidadeGUI modalidadeGUI = new ModalidadeGUI();
    private ConfigGUI configGUI = new ConfigGUI();
    private UsuarioGUI usuarioGUI = new UsuarioGUI();
    private RelatorioGUI relatorioGUI = new RelatorioGUI();
    
    private OpcoesPNL opcoesPNL = new OpcoesPNL();
    private JPanel centroPainel;
    private JLabel usuarioLogadoL;
    private JLabel usuarioLogadoTipoLV;
    private JLabel usuarioLogadoDoisPontosL;
    private JLabel usuarioLogadoLV;
    
    private CardLayout centroCL = new CardLayout();
    
    private JPGUITO to = new JPGUITO( this );
    private JPGUIListener listener;
    
    public JPGUI() {
        super.setTitle( GUIConsts.JP_TITULO );
        //super.setExtendedState( JFrame.MAXIMIZED_BOTH ); 
        super.setSize( 700, 600 );
        super.setLocationRelativeTo( null );
        super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        
        ImageIcon icon = new ImageIcon( GUIConsts.JP_TITULO_IMG );
        super.setIconImage( icon.getImage() ); 
       
        usuarioLogadoL = new JLabel( "Logado como " );
        usuarioLogadoDoisPontosL = new JLabel( ": " );
        
        usuarioLogadoTipoLV = new JLabel();
        usuarioLogadoTipoLV.setForeground( Color.RED ); 
        
        usuarioLogadoLV = new JLabel();
        usuarioLogadoLV.setForeground( Color.BLUE );         
                                        
        centroPainel = new JPanel();
        centroPainel.setLayout( centroCL );
        centroPainel.add( new JPanel(), VASIO_OP );
        centroPainel.add( alunoGUI, ALUNO_OP );
        centroPainel.add( modalidadeGUI, MODALIDADE_OP );
        centroPainel.add( usuarioGUI, USUARIO_OP );
        centroPainel.add( relatorioGUI, RELATORIO_OP );
        centroPainel.add( configGUI, CONFIG_OP );
        
        JPanel usuarioPNL = new JPanel();
        usuarioPNL.setBackground( new Color( 250, 250, 250 ) ); 
        usuarioPNL.setLayout( new BoxLayout( usuarioPNL, BoxLayout.X_AXIS ) );
        usuarioPNL.add( usuarioLogadoL );
        usuarioPNL.add( usuarioLogadoTipoLV );
        usuarioPNL.add( usuarioLogadoDoisPontosL );
        usuarioPNL.add( usuarioLogadoLV );

        JPanel uMortePNL = new JPanel();
        uMortePNL.setBackground( new Color( 250, 250, 250 ) ); 
        uMortePNL.setBorder( new EtchedBorder() );         
        uMortePNL.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        uMortePNL.add( usuarioPNL );
        
        JPanel nortePNL = new JPanel();
        nortePNL.setLayout( new BorderLayout() );
        nortePNL.add( BorderLayout.NORTH, uMortePNL );
        nortePNL.add( BorderLayout.CENTER, opcoesPNL );
        
        Container c = super.getContentPane();
        c.setLayout( new BorderLayout() ); 
        c.add( BorderLayout.NORTH, nortePNL );
        c.add( BorderLayout.CENTER, centroPainel );       
        
        opcoesPNL.getAlunosBT().setText( GUIConsts.JP_ALUNOS_BT_TEXTO );
        opcoesPNL.getModalidadesBT().setText( GUIConsts.JP_MODALIDADES_BT_TEXTO );
        opcoesPNL.getUsuariosBT().setText( GUIConsts.JP_USUARIOS_BT_TEXTO );
        opcoesPNL.getRelatoriosBT().setText( GUIConsts.JP_RELATORIOS_BT_TEXTO ); 
        opcoesPNL.getConfiguracoesBT().setText( GUIConsts.JP_CONFIGS_BT_TEXTO ); 
        opcoesPNL.getSairBT().setText( GUIConsts.JP_SAIR_BT_TEXTO ); 
                
        opcoesPNL.getAlunosBT().setIcon( new ImageIcon( GUIConsts.JP_ALUNOS_OP_IMG ) );
        opcoesPNL.getModalidadesBT().setIcon( new ImageIcon( GUIConsts.JP_MODALIDADES_OP_IMG ) );         
        opcoesPNL.getUsuariosBT().setIcon( new ImageIcon( GUIConsts.JP_USUARIOS_OP_IMG ) );
        opcoesPNL.getRelatoriosBT().setIcon( new ImageIcon( GUIConsts.JP_RELATORIOS_OP_IMG ) );
        opcoesPNL.getConfiguracoesBT().setIcon( new ImageIcon( GUIConsts.JP_CONFIGS_OP_IMG ) );
        opcoesPNL.getSairBT().setIcon( new ImageIcon( GUIConsts.JP_SAIR_OP_IMG ) ); 
        
        opcoesPNL.getAlunosBT().addActionListener( this );
        opcoesPNL.getModalidadesBT().addActionListener( this ); 
        opcoesPNL.getUsuariosBT().addActionListener( this );
        opcoesPNL.getRelatoriosBT().addActionListener( this ); 
        opcoesPNL.getConfiguracoesBT().addActionListener( this ); 
        opcoesPNL.getSairBT().addActionListener( this ); 
                
        super.addWindowListener( this );         
    }

    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        opcoesPNL.getAlunosBT().setSelected( false );
        opcoesPNL.getModalidadesBT().setSelected( false );
        opcoesPNL.getUsuariosBT().setSelected( false );
        opcoesPNL.getRelatoriosBT().setSelected( false ); 
        opcoesPNL.getConfiguracoesBT().setSelected( false ); 
        opcoesPNL.getSairBT().setSelected( false ); 
        
        if ( e.getSource() == opcoesPNL.getAlunosBT() ) {            
            alunoGUI.getFiltroGUI().getBuscaPNL().getNomeTF().requestFocusInWindow();
            opcoesPNL.getAlunosBT().setSelected( true );
            alunoGUI.getTO().getFiltroTO().limpar();
            centroCL.show( centroPainel, ALUNO_OP );             
        } else if ( e.getSource() == opcoesPNL.getModalidadesBT() ) {            
            modalidadeGUI.getFiltroGUI().getFiltroPNL().getDescricaoTF().requestFocusInWindow();
            opcoesPNL.getModalidadesBT().setSelected( true );             
            modalidadeGUI.getTO().getFiltroTO().limpar();
            listener.modalidadesBTAcionado( to ); 
            centroCL.show( centroPainel, MODALIDADE_OP );
        } else if ( e.getSource() == opcoesPNL.getUsuariosBT() ) {            
            opcoesPNL.getUsuariosBT().setSelected( true );             
            listener.usuariosBTAcionado( to ); 
            centroCL.show( centroPainel, USUARIO_OP );
        } else if ( e.getSource() == opcoesPNL.getRelatoriosBT() ) {            
            opcoesPNL.getRelatoriosBT().setSelected( true );             
            listener.relatoriosBTAcionado( to ); 
            centroCL.show( centroPainel, RELATORIO_OP );
        } else if ( e.getSource() == opcoesPNL.getConfiguracoesBT() ) {
            configGUI.getConfigPNL().getToleranciaPagTF().requestFocusInWindow();
            opcoesPNL.getConfiguracoesBT().setSelected( true );
            listener.configBTAcionado( to ); 
            centroCL.show( centroPainel, CONFIG_OP );
        } else if ( e.getSource() == opcoesPNL.getSairBT() ) {
            opcoesPNL.getSairBT().setSelected( true ); 
            listener.sairBTAcionado( to ); 
        }
    }

    public void windowClosing(WindowEvent we) {
        if( listener == null )
            return;
        
        listener.fecharBTAcionado( to ); 
    }

    public void windowClosed(WindowEvent we) {}    
    public void windowOpened(WindowEvent we) {}
    public void windowIconified(WindowEvent we) {}
    public void windowDeiconified(WindowEvent we) {}
    public void windowActivated(WindowEvent we) {}
    public void windowDeactivated(WindowEvent we) {}    
    
    public void setJPListener( JPGUIListener listener ) {
        this.listener = listener;
    }
    
    public JPGUITO getTO() {
        return to;
    }
    
    public AlunoGUI getAlunoGUI() {
        return alunoGUI;
    }
    
    public ModalidadeGUI getModalidadeGUI() {
        return modalidadeGUI;
    }

    public UsuarioGUI getUsuarioGUI() {
        return usuarioGUI;
    }
    
    public RelatorioGUI getRelatorioGUI() {
        return relatorioGUI;
    }
    
    public ConfigGUI getConfigGUI() {
        return configGUI;
    }
    
    public OpcoesPNL getOpcoesPNL() {
        return opcoesPNL;
    }

    public JLabel getUsuarioLogadoLV() {
        return usuarioLogadoLV;
    }

    public JLabel getUsuarioLogadoTipoLV() {
        return usuarioLogadoTipoLV;
    }
    
}
