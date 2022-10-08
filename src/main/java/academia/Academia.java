package academia;

import academia.controlador.aluno.AlunoControlador;
import academia.controlador.aluno.edit.EditMedidasAlunoControlador;
import academia.controlador.aluno.FiltroAlunoControlador;
import academia.controlador.aluno.cad.CadAlunoControlador;
import academia.controlador.aluno.edit.EditAlunoControlador;
import academia.controlador.matricula.NovaMatControlador;
import academia.controlador.config.ConfigControlador;
import academia.controlador.matricula.MatriculaControlador;
import academia.controlador.jp.JPControlador;
import academia.controlador.login.LoginInicialControlador;
import academia.controlador.mensalidade.MensalidadeControlador;
import academia.controlador.mensalidade.NovoValorMensControlador;
import academia.controlador.modalidade.ModalidadeControlador;
import academia.controlador.modalidade.ModalidadeFiltroControlador;
import academia.controlador.modalidade.ModalidadeFormControlador;
import academia.controlador.pag.PagControlador;
import academia.controlador.pagdiaria.PagDiariaControlador;
import academia.controlador.relatorio.RelatorioControlador;
import academia.controlador.usuario.AlterarSenhaControlador;
import academia.controlador.usuario.UsuarioControlador;
import academia.controlador.usuario.UsuarioFiltroControlador;
import academia.controlador.usuario.UsuarioFormControlador;
import academia.gui.GUI;
import academia.gui.telainicial.TelaInicialGUI;
import academia.relatorio.GerenciadorRelatorio;
import academia.util.MSGUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import libs.bd.GBDException;

public class Academia {
    
    static {        
        try { 
            UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Academia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Academia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Academia.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Academia.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        UIManager.put( "OptionPane.yesButtonText", "Sim" );
        UIManager.put( "OptionPane.noButtonText", "Não" );
        UIManager.put( "OptionPane.cancelButtonText", "Cancelar" );
        
        UIManager.put( "Label.font", new FontUIResource( Consts.TEXTO_FONTE ) ) ;
        UIManager.put( "Button.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "ToggleButton.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "TextField.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "FormattedTextField.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "PasswordField.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "TextArea.font", new FontUIResource( Consts.TEXTO_FONTE ) );    
        UIManager.put( "Table.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "TableHeader.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "TabbedPane.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "TitledBorder.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        UIManager.put( "List.font", new FontUIResource( Consts.TEXTO_FONTE ) );        
        UIManager.put( "OptionPane.messageFont", new FontUIResource( Consts.TEXTO_FONTE ) );        
        UIManager.put( "OptionPane.buttonFont", new FontUIResource( Consts.TEXTO_FONTE ) );        
        UIManager.put( "CheckBox.font", new FontUIResource( Consts.TEXTO_FONTE ) ); 
        UIManager.put( "RadioButton.font", new FontUIResource( Consts.TEXTO_FONTE ) );
        
        UIManager.put( "TextField.foreground", new ColorUIResource( Consts.TEXTO_COR ) ); 
        UIManager.put( "FormattedTextField.foreground", new ColorUIResource( Consts.TEXTO_COR ) ); 
        UIManager.put( "PasswordField.foreground", new ColorUIResource( Consts.TEXTO_COR ) ); 
        UIManager.put( "TextArea.foreground", new ColorUIResource( Consts.TEXTO_COR ) );         
    }       
    
    public static void main( String[] args ) { 
        TelaInicialGUI telaInicialGUI = new TelaInicialGUI();
        telaInicialGUI.getTO().setVisivel( true );                 
        
        SwingUtilities.invokeLater(new Runnable() {            
            public void run() { 
                try {                    
                    ModuloMGR mgr = new ModuloMGR();
                    mgr.inicializa();
                    
                    GUI gui = mgr.getGUI();
                    
                    GerenciadorRelatorio gr = mgr.getGerenciadorRelatorio();
                    gr.getRelatorioAlunoEmDiaGerador().setLargura( Consts.REL_ALUNOS_EM_DIA_LARGURA );
                    gr.getRelatorioAlunoEmDiaGerador().setAltura( Consts.REL_ALUNOS_EM_DIA_ALTURA );                                                                         
                                                
                    JPControlador jpC = new JPControlador( mgr );
                    
                    LoginInicialControlador loginC = new LoginInicialControlador( mgr );                    

                    AlunoControlador aC = new AlunoControlador( mgr );
                    FiltroAlunoControlador afC = new FiltroAlunoControlador( mgr );
                    CadAlunoControlador aCadFrmC = new CadAlunoControlador( mgr );
                    EditAlunoControlador aEditFrmC = new EditAlunoControlador( mgr );
                    EditMedidasAlunoControlador aMedsFrmC = new EditMedidasAlunoControlador( mgr );

                    ModalidadeControlador mC = new ModalidadeControlador( mgr );
                    ModalidadeFiltroControlador mfC = new ModalidadeFiltroControlador( mgr );
                    ModalidadeFormControlador mFrmC = new ModalidadeFormControlador( mgr );

                    MatriculaControlador aMatC = new MatriculaControlador( mgr );
                    NovaMatControlador nMatC = new NovaMatControlador( mgr );

                    MensalidadeControlador menC = new MensalidadeControlador( mgr );
                    NovoValorMensControlador novoValorMenC = new NovoValorMensControlador( mgr );

                    PagControlador pagC = new PagControlador( mgr );
                    PagDiariaControlador pagDiariaC = new PagDiariaControlador( mgr );
                    
                    UsuarioControlador usuarioC = new UsuarioControlador( mgr );
                    UsuarioFiltroControlador usuarioFC = new UsuarioFiltroControlador( mgr );
                    UsuarioFormControlador usuarioFrmC = new UsuarioFormControlador( mgr );
                    AlterarSenhaControlador altSenhaC = new AlterarSenhaControlador( mgr );
                    
                    RelatorioControlador relC = new RelatorioControlador( mgr );

                    ConfigControlador cfgC = new ConfigControlador( mgr );

                    gui.getLoginInicialGUI().setLoginInicialListener( loginC ); 
                    
                    gui.getJP().getAlunoGUI().setAlunoListener( aC );
                    gui.getJP().getAlunoGUI().getFiltroGUI().setAlunoFiltroListener( afC ); 
                    gui.getCadAlunoGUI().setCadAlunoFormListener(aCadFrmC ); 
                    gui.getEditAlunoGUI().setEditAlunoFormListener( aEditFrmC ); 
                    gui.getMedidasFormGUI().setMedidasFormListener( aMedsFrmC ); 

                    gui.getJP().getModalidadeGUI().setModalidadeListener( mC );
                    gui.getJP().getModalidadeGUI().getFiltroGUI().setModalidadeFiltroListener( mfC );
                    gui.getModalidadeFormGUI().setModalidadeFormListener( mFrmC ); 

                    gui.getMensalidadeGUI().setMensalidadeListener( menC ); 
                    gui.getNovoValMensGUI().setNovoValorMensListener( novoValorMenC );

                    gui.getMatriculaGUI().setMatriculaListener( aMatC ); 
                    gui.getNovaMatGUI().setNovaMatListener( nMatC );
                    
                    gui.getPagamentoGUI().setPagListener( pagC ); 
                    gui.getPagamentoDiariaGUI().setPagDiariaListener( pagDiariaC ); 
                    
                    gui.getJP().getUsuarioGUI().setUsuarioListener( usuarioC );
                    gui.getJP().getUsuarioGUI().getFiltroGUI().setUsuarioFiltroListener( usuarioFC ); 
                    gui.getUsuarioFormGUI().setUsuarioFormListener( usuarioFrmC ); 
                    gui.getAlterarSenhaGUI().setAlterarSenhaListener( altSenhaC ); 
                    
                    gui.getJP().getRelatorioGUI().setRelatorioGUIListener( relC ); 

                    gui.getJP().getConfigGUI().setConfigListener( cfgC ); 

                    gui.getJP().setJPListener( jpC ); 
                    
                    gui.getLoginInicialGUI().getTO().limpar();
                    
                    telaInicialGUI.getTO().setVisivel( false ); 
                    gui.getLoginInicialGUI().getTO().setVisivel( true );                                                                                                     
                    
                } catch ( Exception ex ) {                                                            
                    telaInicialGUI.getTO().setVisivel( false ); 
                    String erroMsg;
                    if ( ex instanceof GBDException ) {
                        erroMsg = MSGConsts.CONEXAO_BD_ERRO.replace( "%1", ex.getMessage() );
                    } else if ( ex instanceof IOException ) {
                        erroMsg = MSGConsts.ARQ_CONF_NAO_CARREGADO.replace( "%1", ex.getMessage() );
                    } else {
                        erroMsg = ex.getMessage();
                    }
                    
                    new MSGUtil().mostraErro( erroMsg, MSGConsts.SISTEMA_TITULO );                    
                    
                    Logger.getLogger(Academia.class.getName()).log(Level.SEVERE, null, ex);                                                        
                    System.exit( 1 );
                }
            }            
        } );
        
                       
    }
    
}
