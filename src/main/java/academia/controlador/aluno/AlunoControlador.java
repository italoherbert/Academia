package academia.controlador.aluno;

import academia.Consts;
import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.BDConsts;
import academia.bd.to.AlunoTO;
import academia.bd.to.MatriculaTO;
import academia.bd.dao.DAOException;
import academia.bd.to.ModalidadeTO;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.VisualizacaoMGR;
import academia.controlador.aluno.cad.CadLimparAlunoGUICB;
import academia.controlador.matricula.MatriculaSessao;
import academia.controlador.matricula.MatriculaCtrlUtil;
import academia.controlador.pag.GeraParcelaException;
import academia.controlador.pag.PagCtrlUtil;
import academia.controlador.pag.PagSessao;
import academia.gui.GUI;
import academia.gui.aluno.AlunoGUIListener;
import academia.gui.aluno.AlunoGUITO;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.gui.aluno.form.cad.CadAlunoFormGUITO;
import academia.gui.aluno.form.edit.EditAlunoFormGUITO;
import academia.gui.aluno.form.edit.aluno.EditAlunoGUITO;
import academia.gui.aluno.form.edit.medidas.EditMedidasGUITO;
import academia.gui.aluno.form.edit.mod.EditModalidadeGUITO;
import academia.gui.matricula.MatriculaGUITO;
import academia.gui.pag.PagamentoGUITO;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.ModalidadeFormatador;
import academia.util.NumeroFormatador;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import libs.gui.tabela.TabelaMD;

public class AlunoControlador implements AlunoGUIListener {

    private ControladorTO cTO;
    
    public AlunoControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
        
    public void cadastrarBTAcionado( AlunoGUITO to ) {
        GUI gui = cTO.getGUI();
                
        CadLimparAlunoGUICB cadAFLimparDrv = new CadLimparAlunoGUICB( cTO );

        CadAlunoFormGUITO formTO = gui.getCadAlunoGUI().getTO();
        formTO.limpar( cadAFLimparDrv );

        formTO.getAlunoTO().setID( Consts.TEXTO_VASIO );         

        formTO.setVisivel( true );                        
    }

    public void editarBTAcionado( AlunoGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();        
        VisualizacaoMGR visuMGR = cTO.getVisuMGR();
        
        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        AlunoSessao alunoSessao = cTO.getAlunoSessao();
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        UsuarioTO usuarioLogado = cTO.getUsuarioLogado();
        
        TabelaMD alunoTBLMD = to.getFiltroTO().getAlunoTBLMD();
                        
        int linhaS = alunoTBLMD.getIndiceLinhaSelecionada();
        if ( linhaS == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUM_ALUNO_SEL, MSGConsts.ALUNO_TITULO ); 
        } else {
            String sID = alunoTBLMD.getCelulaValor( linhaS, 0 );
            int alunoID = Integer.parseInt( sID );

            try {
                AlunoTO aluno = bd.getAlunoDAO().busca( alunoID );
                                
                EditAlunoFormGUITO formTO = gui.getEditAlunoGUI().getTO();
                formTO.setID( sID ); 
                formTO.setNome( aluno.getNome() );  
                
                if ( aluno.getDataNasc() != null )
                    formTO.setDataNasc( df.formataData( aluno.getDataNasc() ) );
                else formTO.setDataNasc( Consts.TEXTO_VASIO ); 
                
                formTO.setObs( aluno.getObs() ); 
                
                EditAlunoGUITO alunoGUITO = gui.getEditAlunoGUI().getTO().getAlunoTO();
                EditMedidasGUITO medidasGUITO = gui.getEditAlunoGUI().getTO().getMedidasTO();
                EditModalidadeGUITO modsGUITO = gui.getEditAlunoGUI().getTO().getModsTO();

                MatriculaTO mat = null;
                int matCorrID = aluno.getMatriculaCorrente();
                if ( matCorrID == BDConsts.ID_NULO ) {                    
                    
                    medidasGUITO.mostrarSemMatPNL();                                        
                    modsGUITO.mostrarSemMatPNL();
                    alunoGUITO.setDescontoPNLVisivel( false ); 
                } else {
                    mat = bd.getMatriculaDAO().busca( matCorrID );
                    if ( mat == null ) {
                        msgUtil.mostraErro( MSGConsts.INCONSISTENCIA_MAT_ALUNO, MSGConsts.ALUNO_TITULO );                      
                    } else {
                        double ultimoDesconto = bd.getMatriculaDAO().buscaDescontoAtual( matCorrID );
                        formTO.setDesconto( nf.formatoFlutuante( ultimoDesconto ) ); 
                        
                        Timestamp dataInicio = mat.getDataInicio();
                        formTO.setDataMatricula( df.formataData( dataInicio ) );
                        
                        SelecionarModsPNLTO editModTO = gui.getEditAlunoGUI().getEditModGUI().getTO();                        
                        alunoCUtil.carregarListasModalidades( editModTO, matCorrID );
                        
                        EditMedidasGUITO editMedsTO = gui.getEditAlunoGUI().getEditMedsGUI().getTO();
                        alunoCUtil.carregarTabelaMedidas( editMedsTO, matCorrID ); 

                        medidasGUITO.mostrarMatAtivaPNL();                        
                        modsGUITO.mostrarMatAtivaPNL();
                        alunoGUITO.setDescontoPNLVisivel( true );                                                
                    }                                        
                }
                                
                // CARREGA SESSAO DE EDICAO DO ALUNO
                alunoSessao.setMatricula( mat ); 
                                                
                visuMGR.configVisuEditAluno( usuarioLogado.getTipoID() ); 
                                
                formTO.setVisivel( true ); 
            } catch ( DAOException ex ) {
                Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }       
        }
    }

    public void pagamentoParcelasBTAcionado( AlunoGUITO to ) {
        GUI gui = cTO.getGUI();                        
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        PagCtrlUtil pagCUtil = cTO.getPagCUtil();
        PagSessao pagSessao = cTO.getPagSessao();        
        
        TabelaMD alunoTBLMD = to.getFiltroTO().getAlunoTBLMD();
                
        int linhaS = alunoTBLMD.getIndiceLinhaSelecionada();
        if ( linhaS == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUM_ALUNO_SEL, MSGConsts.ALUNO_TITULO ); 
        } else {
            String sID = alunoTBLMD.getCelulaValor( linhaS, 0 );
            int alunoID = Integer.parseInt( sID );
            
            try {
                AlunoTO aluno = bd.getAlunoDAO().busca( alunoID );
                int matID = aluno.getMatriculaCorrente();
                if ( matID == BDConsts.ID_NULO ) {
                    msgUtil.mostraAlerta( MSGConsts.NENHUMA_MAT_ATIVA, MSGConsts.ALUNO_TITULO ); 
                } else { 
                    // CARREGA JANELA DE PAGAMENTOS POR MATRICULA DO ALUNO
                    // seta ID da matricula
                    pagSessao.setMatID( matID ); 
                                        
                    // calcula parcelas por matrícula do aluno
                    PagamentoGUITO pagTO = gui.getPagamentoGUI().getTO();
                    pagTO.limpar();
                    pagCUtil.calculaParcelas( pagTO ); 

                    pagTO.setNomeTitulo( aluno.getNome() ); 
                    pagTO.setVisivel( true ); 
                }
            } catch ( GeraParcelaException e ) {
                msgUtil.mostraErro( e.getMessage(), MSGConsts.PAG_TITULO ); 
            } catch (DAOException ex) {
                Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }

    public void pagamentoDiariaBTAcionado( AlunoGUITO to ) {
        BD bd = cTO.getBD();
        GUI gui = cTO.getGUI();
        
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
        
        try {
            List<ModalidadeTO> modalidades = bd.getModalidadeDAO().buscaTodasComDiaria();
            List<String> mods = new ArrayList();
            for( ModalidadeTO m : modalidades ) {
                int id = m.getID();
                String descricao = m.getDescricao();
                mods.add( mf.formataModalidade( id, descricao ) );
            }
            
            gui.getPagamentoDiariaGUI().getTO().limpar();
            gui.getPagamentoDiariaGUI().getTO().setOutrasMods( mods ); 
            gui.getPagamentoDiariaGUI().getTO().setVisivel( true ); 
        } catch (DAOException ex) {
            Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }                
    }        

    public void listarMatsBTAcionado( AlunoGUITO to ) {
        GUI gui = cTO.getGUI();                        
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        MatriculaCtrlUtil matCUtil = cTO.getMatCUtil();
        MatriculaSessao lstMatsSessao = cTO.getMatSessao();
                        
        TabelaMD alunoTBLMD = to.getFiltroTO().getAlunoTBLMD();
                
        int linhaS = alunoTBLMD.getIndiceLinhaSelecionada();
        if ( linhaS == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUM_ALUNO_SEL, MSGConsts.ALUNO_TITULO ); 
        } else {
            String sID = alunoTBLMD.getCelulaValor( linhaS, 0 );
            int alunoID = Integer.parseInt( sID );
                        
            try {
                // CARREGA SESSAO LISTAR MATRICULAS                
                lstMatsSessao.setAlunoID( alunoID );
                
                MatriculaGUITO matGUITO = gui.getMatriculaGUI().getTO();
                matCUtil.carregarMats( matGUITO );                 
                                
                matGUITO.setVisivel( true );                
            } catch ( DAOException ex ) {
                Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                
    }

    public void encerrarMatBTAcionado( AlunoGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
     
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        TabelaMD alunoTBLMD = to.getFiltroTO().getAlunoTBLMD();
               
        int linhaS = alunoTBLMD.getIndiceLinhaSelecionada();
        if ( linhaS == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUM_ALUNO_SEL, MSGConsts.ALUNO_TITULO ); 
        } else {
            String sID = alunoTBLMD.getCelulaValor( linhaS, 0 );
            int alunoID = Integer.parseInt( sID );
            try {                
                boolean matAtiva = bd.getAlunoDAO().verificarSeMatAtiva( alunoID );
                if ( matAtiva ) {
                    String nome = bd.getAlunoDAO().buscaNome( alunoID );
                
                    String pergunta = MSGConsts.PERGUNTA_ENCERRAR_MAT.replace( "%1", nome );
                    int result = msgUtil.mostraPergunta( pergunta, MSGConsts.ALUNO_TITULO );
                    if ( result == JOptionPane.YES_OPTION ) {               
                        bd.getAlunoDAO().encerraMat( alunoID );                                        
                        
                        AlunoFiltroGUITO afTO = gui.getJP().getAlunoGUI().getFiltroGUI().getTO();
                        alunoCUtil.filtrar( afTO );
                        
                        msgUtil.mostraInfo( MSGConsts.MAT_ENCERRADA, MSGConsts.ALUNO_TITULO ); 
                    }
                } else {
                    msgUtil.mostraAlerta( MSGConsts.NENHUMA_MAT_ATIVA, MSGConsts.ALUNO_TITULO ); 
                }
            } catch (DAOException ex) {
                Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }            
    }

    public void novaMatBTAcionado( AlunoGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        DataUtil dutil = cTO.getDataUtil();
        
        MatriculaSessao matSessao = cTO.getMatSessao();
        
        TabelaMD alunoTBLMD = to.getFiltroTO().getAlunoTBLMD();
               
        int linhaS = alunoTBLMD.getIndiceLinhaSelecionada();
        if ( linhaS == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUM_ALUNO_SEL, MSGConsts.ALUNO_TITULO ); 
        } else {
            String sID = alunoTBLMD.getCelulaValor( linhaS, 0 );
            int alunoID = Integer.parseInt( sID );
        
            try {
                matSessao.setAlunoID( alunoID );
                
                int currMatID = bd.getAlunoDAO().buscaMatCorr( alunoID );
                Timestamp dataMat = new Timestamp( System.currentTimeMillis() );

                gui.getNovaMatGUI().getTO().setDataMat( dutil.formataData( dataMat ) );                                    
                gui.getNovaMatGUI().getTO().setCopiarModsOpVisivel( currMatID != BDConsts.ID_NULO ); 
                gui.getNovaMatGUI().setVisible( true );             
            } catch (DAOException ex) {
                Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
    }

    /*
    public void efetuarTodosPagamentosBTAcionado(AlunoGUITO to) {
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        int usuarioID = cTO.getUsuarioLogado().getID();
        
        try {
            ConfigTO config = bd.getConfigDAO().busca();
            int diariaID = config.getDiariaAlunoID();
            List<AlunoTO> alunos = bd.getAlunoDAO().buscaTodos( false, diariaID );

            for( AlunoTO aluno : alunos ) {            
                int matID = aluno.getMatriculaCorrente();
                ParcelasBean parcelasBean = bd.getPagamentoBO().calculaParcelas( matID, false );
                
                List<VisaoParcelaBean> parcelas = parcelasBean.getMD().buscaParcelasPendentes();
                int quant = parcelasBean.getQuantParcelas();
                                
                for( int i = 0; i < quant; i++ ) {
                    PagamentoTO pag = new PagamentoTO();
                    pag.setUsuarioID( usuarioID ); 
                    pag.setMatID( matID );

                    VisaoParcelaBean guiP = parcelas.get( i );
                    
                    double total = guiP.getCParcela().getValorSubtotalSemDescontoPag();
                    
                    pag.setValor( total ); 
                    pag.setDesconto( 0 ); 
                    pag.setDataPag( new Timestamp( System.currentTimeMillis() ) ); 

                    bd.getPagamentoDAO().insere( pag );
                }                
            }    
            msgUtil.mostraInfo( "Parcelas quitadas!", MSGConsts.ALUNO_TITULO );
        } catch ( GeraParcelaException e ) {
            msgUtil.mostraErro( e.getMessage(), MSGConsts.PAG_TITULO ); 
        } catch (DAOException ex) {
            Logger.getLogger(PagControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    */
    
}
