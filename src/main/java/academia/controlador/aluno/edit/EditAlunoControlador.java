package academia.controlador.aluno.edit;

import academia.loginoper.aluno.RemoverAlunoOperLoginOK;
import academia.Consts;
import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.BDConsts;
import academia.bd.bean.ModalidadeBean;
import academia.bd.to.AlunoTO;
import academia.bd.to.MatriculaTO;
import academia.bd.dao.DAOException;
import academia.bd.to.DescontoTO;
import academia.bd.to.MedidasTO;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.controlador.aluno.AlunoSessao;
import academia.loginoper.LoginInterceptControlador;
import academia.gui.GUI;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.gui.aluno.form.edit.EditAlunoFormGUITO;
import academia.gui.aluno.form.edit.mod.EditModalidadeGUITO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.ModalidadeFormatador;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.gui.tabela.TabelaMD;
import academia.gui.medidas.MedidasFormGUITO;
import academia.util.NumeroFormatador;
import academia.gui.aluno.form.edit.EditAlunoFormGUIListener;
import academia.relatorio.VisualizarPDFException;
import academia.relatorio.GerenciadorRelatorio;
import academia.relatorio.compmed.RelatorioCompMedidasBean;
import com.itextpdf.text.DocumentException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

public class EditAlunoControlador implements EditAlunoFormGUIListener {
        
    private ControladorTO cTO;
    
    public EditAlunoControlador( ControladorTO cTO ) {
        this.cTO = cTO;        
    }
    
    public void salvarDadosAlunoBTAcionado( EditAlunoFormGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();        
        
        NumeroFormatador nf = cTO.getNumeroFormatador();
        DataUtil df = cTO.getDataUtil();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
                        
        try {
            List<String> erros = alunoCUtil.validaEditAlunoForm( to );
            if ( erros.isEmpty() ) {
                int alunoID = Integer.parseInt( to.getID() );

                Timestamp dataNascTS = null;
                try {                                            
                    Date dataNasc = df.converteData( to.getDataNasc() );
                    dataNascTS = new Timestamp( dataNasc.getTime() );
                } catch ( ParseException e ) {
                    
                }
                
                String nome = to.getNome();
                
                AlunoTO a = new AlunoTO();
                a.setID( alunoID );
                a.setNome( nome );         
                a.setDataNasc( dataNascTS );
                a.setObs( to.getObs() );                                                                          
                
                bd.getAlunoDAO().atualiza( a );                                                        
                                
                int currMatID = bd.getAlunoDAO().buscaMatCorr( alunoID );
                if ( currMatID != BDConsts.ID_NULO ) {
                    double desconto = nf.valorFlutuante( to.getAlunoTO().getDesconto() );
                    double ultimoDesconto = bd.getMatriculaDAO().buscaDescontoAtual( currMatID );
                    if ( desconto != ultimoDesconto ) {
                        DescontoTO d = new DescontoTO();
                        d.setMatID( currMatID );
                        d.setPorcentagem( desconto ); 
                        bd.getDescontoDAO().insere( d );
                    }
                }

                AlunoFiltroGUITO filtroTO = gui.getJP().getAlunoGUI().getFiltroGUI().getTO();
                alunoCUtil.filtrar( filtroTO );                          
                alunoCUtil.atualizaQuantAnivers();                    
                
                to.setVisivel( false );                

                msgUtil.mostraInfo( MSGConsts.DADOS_SALVOS, MSGConsts.ALUNO_TITULO );                                                                            
            } else {
                String msg = msgUtil.constroiMSG( erros );            
                msgUtil.mostraAlerta( msg, MSGConsts.ALUNO_TITULO );
            }
        } catch (ParseException ex) {
            msgUtil.mostraErro( MSGConsts.DESCONTO_INVALIDO, MSGConsts.ALUNO_TITULO ); 
        } catch ( DAOException ex ) {
            Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void removerBTAcionado( EditAlunoFormGUITO to ) {
        GUI gui = cTO.getGUI();

        RemoverAlunoOperLoginOK oper = new RemoverAlunoOperLoginOK( cTO, to );
        LoginInterceptControlador rlCtrl = new LoginInterceptControlador( cTO, oper );

        gui.getLoginInterceptGUI().setLoginInterceptListener( rlCtrl );                         
        gui.getLoginInterceptGUI().getTO().limpar();
        gui.getLoginInterceptGUI().getTO().setVisivel( true ); 
    }        

    public void limparDadosAlunoBTAcionado(EditAlunoFormGUITO to) {
        AlunoSessao alunoSessao = cTO.getAlunoSessao();
        
        MatriculaTO mat = alunoSessao.getMatricula();
        if ( mat != null ) {
            Timestamp dataMat = mat.getDataInicio();
            to.limpar(new EditLimparAlunoCB( cTO, dataMat ) );
        }        
    }

    public void registrarNovasMedidasBTAcionado( EditAlunoFormGUITO to ) {
        GUI gui = cTO.getGUI();

        DataUtil df = cTO.getDataUtil();
        
        Timestamp dataMat = new Timestamp( System.currentTimeMillis() );
        
        MedidasFormGUITO formTO = gui.getMedidasFormGUI().getTO();
        formTO.limpar(new EditLimparAlunoCB( cTO, dataMat ) );
        
        formTO.setID( Consts.TEXTO_VASIO ); 
        formTO.setDataReg( df.formataDataAtual() );
        
        formTO.setRemoverBTAbilitado( false ); 
        formTO.setVisivel( true ); 
    }

    public void editarMedidasFuncAcionada( EditAlunoFormGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        NumeroFormatador nf = cTO.getNumeroFormatador();
        DataUtil df = cTO.getDataUtil();
        
        TabelaMD medidasTBLMD = to.getMedidasTO().getMedidasTBLMD();
        
        int linha = medidasTBLMD.getIndiceLinhaSelecionada();
        if ( linha == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUMA_MEDS_SELECIONADA, MSGConsts.ALUNO_TITULO ); 
        } else {
            String idS = medidasTBLMD.getCelulaValor( linha, 0 );
            int medidasID = Integer.parseInt( idS );

            try {
                MedidasTO medidas = bd.getMedidasDAO().busca( medidasID );
                if ( medidas == null ) {
                    msgUtil.mostraErro( MSGConsts.INCONSISTENCIA_EDIT_MEDIDAS, MSGConsts.ALUNO_TITULO ); 
                } else {
                    MedidasFormGUITO medsFrmTO = gui.getMedidasFormGUI().getTO();
                    medsFrmTO.setID( String.valueOf( medidas.getID() ) ); 
                    medsFrmTO.setPeso( String.valueOf( medidas.getPeso() ) );
                    medsFrmTO.setAltura( nf.formatoFlutuante( medidas.getAltura() ) );
                    medsFrmTO.setBracoEsquerdo( String.valueOf( medidas.getBracoEsquerdo() ) ); 
                    medsFrmTO.setBracoDireito( String.valueOf( medidas.getBracoDireito() ) );
                    medsFrmTO.setAntebracoEsquerdo( String.valueOf( medidas.getAntebracoEsquerdo() ) );                
                    medsFrmTO.setAntebracoDireito( String.valueOf( medidas.getAntebracoDireito() ) );
                    medsFrmTO.setTorax( String.valueOf( medidas.getTorax() ) );
                    medsFrmTO.setCintura( String.valueOf( medidas.getCintura() ) );
                    medsFrmTO.setBumbum( String.valueOf( medidas.getBumbum() ) );
                    medsFrmTO.setCoxaEsquerda( String.valueOf( medidas.getCoxaEsquerda() ) );
                    medsFrmTO.setCoxaDireita( String.valueOf( medidas.getCoxaDireita() ) );
                    medsFrmTO.setPanturrilhaEsquerda( String.valueOf( medidas.getPanturrilhaEsquerda() ) );
                    medsFrmTO.setPanturrilhaDireita( String.valueOf( medidas.getPanturrilhaDireita() ) ); 
                    medsFrmTO.setDataReg( df.formataData( medidas.getDataReg() ) ); 
                            
                    medsFrmTO.setRemoverBTAbilitado( true ); 
                    medsFrmTO.setVisivel( true ); 
                }                
            } catch (DAOException ex) {
                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }                                    
        }
    }
                                  
    public void compararMedidasBTAcionado( EditAlunoFormGUITO to ) {
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        GerenciadorRelatorio gr = cTO.getGerenciadorRelatorio();
        
        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        TabelaMD medsTBLMD = to.getMedidasTO().getMedidasTBLMD();
        
        int quantLSel = medsTBLMD.getQuantLinhasSelecionadas();        
        if ( quantLSel == 2 ) {        
            int[] medsSel = medsTBLMD.getIndicesLinhasSelecionadas();
            
            String meds1IDS = medsTBLMD.getCelulaValor( medsSel[0], 0 );
            String meds2IDS = medsTBLMD.getCelulaValor( medsSel[1], 0 );            
            
            int meds1ID = Integer.parseInt( meds1IDS );
            int meds2ID = Integer.parseInt( meds2IDS );
            
            try {
                MedidasTO meds1 = bd.getMedidasDAO().busca( meds1ID );
                MedidasTO meds2 = bd.getMedidasDAO().busca( meds2ID );
                if ( meds1.getDataReg().compareTo( meds2.getDataReg() ) >= 0 ) {
                    MedidasTO aux = meds1;
                    meds1 = meds2;
                    meds2 = aux;
                }                
                
                long meds1DataRegMS = meds1.getDataReg().getTime();
                long meds2DataRegMS = meds2.getDataReg().getTime();
                
                long dias = ( meds2DataRegMS - meds1DataRegMS ) / Consts.QMS_EM_DIA;
                
                RelatorioCompMedidasBean compmed = new RelatorioCompMedidasBean();
                
                compmed.setLnDataReg( 
                        df.formataData( meds1.getDataReg() ),
                        df.formataData( meds2.getDataReg() ),
                        String.valueOf( dias ) + " dias" ); 
                
                compmed.setLnPeso(
                        String.valueOf( meds1.getPeso() ),
                        String.valueOf( meds2.getPeso() ),
                        String.valueOf( meds2.getPeso() - meds1.getPeso() ) ); 

                compmed.setLnAltura(
                        nf.formatoFlutuante( meds1.getAltura() ),
                        nf.formatoFlutuante( meds2.getAltura() ),
                        nf.formatoFlutuante( meds2.getAltura() - meds1.getAltura() ) ); 

                compmed.setLnBracoEsquerdo(
                        String.valueOf( meds1.getBracoEsquerdo() ),
                        String.valueOf( meds2.getBracoEsquerdo() ),
                        String.valueOf( meds2.getBracoEsquerdo() - meds1.getBracoEsquerdo() ) ); 

                compmed.setLnBracoDireito(
                        String.valueOf( meds1.getBracoDireito() ),
                        String.valueOf( meds2.getBracoDireito() ),
                        String.valueOf( meds2.getBracoDireito() - meds1.getBracoDireito() ) ); 

                compmed.setLnAntebracoEsquerdo(
                        String.valueOf( meds1.getAntebracoEsquerdo() ),
                        String.valueOf( meds2.getAntebracoEsquerdo() ),
                        String.valueOf( meds2.getAntebracoEsquerdo() - meds1.getAntebracoEsquerdo() ) ); 
                
                compmed.setLnAntebracoDireito(
                        String.valueOf( meds1.getAntebracoDireito() ),
                        String.valueOf( meds2.getAntebracoDireito() ),
                        String.valueOf( meds2.getAntebracoDireito() - meds1.getAntebracoDireito() ) );                 

                compmed.setLnTorax(
                        String.valueOf( meds1.getTorax() ),
                        String.valueOf( meds2.getTorax() ),
                        String.valueOf( meds2.getTorax() - meds1.getTorax() ) );                 

                compmed.setLnCintura(
                        String.valueOf( meds1.getCintura() ),
                        String.valueOf( meds2.getCintura() ),
                        String.valueOf( meds2.getCintura() - meds1.getCintura() ) );                 

                compmed.setLnBumbum(
                        String.valueOf( meds1.getBumbum() ),
                        String.valueOf( meds2.getBumbum() ),
                        String.valueOf( meds2.getBumbum() - meds1.getBumbum() ) );                 
                
                compmed.setLnCoxaEsquerda(
                        String.valueOf( meds1.getCoxaEsquerda() ),
                        String.valueOf( meds2.getCoxaEsquerda() ),
                        String.valueOf( meds2.getCoxaEsquerda() - meds1.getCoxaEsquerda() ) );                 
                        
                compmed.setLnCoxaDireita(
                        String.valueOf( meds1.getCoxaDireita() ),
                        String.valueOf( meds2.getCoxaDireita() ),
                        String.valueOf( meds2.getCoxaDireita() - meds1.getCoxaDireita() ) );                 
                
                compmed.setLnPanturrilhaEsquerda(
                        String.valueOf( meds1.getPanturrilhaEsquerda() ),
                        String.valueOf( meds2.getPanturrilhaEsquerda() ),
                        String.valueOf( meds2.getPanturrilhaEsquerda() - meds1.getPanturrilhaEsquerda() ) );                 
                
                compmed.setLnPanturrilhaDireita(
                        String.valueOf( meds1.getPanturrilhaDireita() ),
                        String.valueOf( meds2.getPanturrilhaDireita() ),
                        String.valueOf( meds2.getPanturrilhaDireita() - meds1.getPanturrilhaDireita() ) );                 
                                
                OutputStream arqOut = new FileOutputStream( Consts.REL_COMP_MEDIDAS_ARQUIVO );
                
                gr.getRelatorioCompMedidasGerador().geraRelatorio( arqOut, compmed );
                
                gr.visualizarPDF( Consts.REL_COMP_MEDIDAS_ARQUIVO );                 
            } catch (DAOException ex) {
                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            } catch ( IOException | DocumentException | VisualizarPDFException e ) {
                String msgErro = null;
                if ( e instanceof VisualizarPDFException ) {
                    msgErro = MSGConsts.RELATORIO_NAO_MOSTRADO.replace( "%1", e.getMessage() );
                } else {
                    msgErro = MSGConsts.RELATORIO_NAO_GERADO.replace( "%1", e.getMessage() );
                }                                    
                msgUtil.mostraErro( msgErro, MSGConsts.RELATORIO_TITULO );
                
                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, e);
            }                        
        } else {
            msgUtil.mostraAlerta( MSGConsts.SEL_DUAS_MEDIDAS, MSGConsts.ALUNO_TITULO ); 
        }
    }     
    
    public void addModalidadeBTAcionado( EditAlunoFormGUITO to ) {
        BD bd = cTO.getBD();        
        MSGUtil msgUtil = cTO.getMSGUtil();
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        AlunoSessao alunoSessao = cTO.getAlunoSessao();

        EditModalidadeGUITO emTO = to.getModsTO();
        
        if ( alunoSessao.getMatricula() == null ) {
            msgUtil.mostraErro( MSGConsts.ALUNO_SEM_MAT_ATIVA, MSGConsts.ALUNO_TITULO );
        } else {        
            int matID = alunoSessao.getMatricula().getID();        
            
            List<String> linhasSelecionadas = emTO.getOutrasModsSelecionadas();
            try {                
                for( String linha : linhasSelecionadas ) {
                    int modID = mf.extraiModalidadeID( linha );                
                    bd.getModalidadeDAO().adicionaModalidadeAMatricula( matID, modID );                
                }        

                alunoCUtil.carregarListasModalidades( emTO, matID );
            } catch (DAOException ex) {
                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }       
        }
    }

    public void removeModalidadeBTAcionado( EditAlunoFormGUITO to ) {
        BD bd = cTO.getBD();        
        MSGUtil msgUtil = cTO.getMSGUtil();
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        AlunoSessao alunoSessao = cTO.getAlunoSessao();

        EditModalidadeGUITO emTO = to.getModsTO();
        
        if ( alunoSessao.getMatricula() == null ) {
            
            msgUtil.mostraErro( MSGConsts.ALUNO_SEM_MAT_ATIVA, MSGConsts.ALUNO_TITULO );            
        } else {        
            int matID = alunoSessao.getMatricula().getID();        
        
            List<String> linhasSelecionadas = emTO.getAlunoModsSelecionadas();
            try {                
                for( String linha : linhasSelecionadas ) {
                    int matModID = mf.extraiModalidadeID( linha ); 
                    bd.getModalidadeDAO().encerraModalidadeDoAluno( matModID );                
                }        
                alunoCUtil.carregarListasModalidades( emTO, matID );
            } catch (DAOException ex) {
                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void historicoModalidadesBTAcionado( EditAlunoFormGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        
        MSGUtil msgUtil = cTO.getMSGUtil();
        DataUtil df = cTO.getDataUtil();
                
        AlunoSessao alunoSessao = cTO.getAlunoSessao();
        
        if ( alunoSessao.getMatricula() == null ) {
            
            msgUtil.mostraErro( MSGConsts.ALUNO_SEM_MAT_ATIVA, MSGConsts.ALUNO_TITULO );            
        } else {        
            int matID = alunoSessao.getMatricula().getID();        
                
            TabelaMD modsTBLMD = gui.getHistoricoModsGUI().getTO().getModsTBLMD();
            try {                        
                modsTBLMD.removeTodasAsLinhas();

                List<ModalidadeBean> modalidades = bd.getMatriculaDAO().buscaModalidades( matID );
                for ( ModalidadeBean mod : modalidades ) {
                    String dataEncS = Consts.NAO_ENCERRADA_TEXTO;
                    if ( mod.getDataEncerramento() != null )
                        dataEncS = df.formataData( mod.getDataEncerramento() );

                    String finalizadaS = Consts.NAO_TEXTO;
                    if ( mod.getDataFim() != null )
                        finalizadaS = Consts.SIM_TEXTO;

                    modsTBLMD.addLinha( new String[]{
                        String.valueOf( mod.getID() ),
                        mod.getDescricao(),
                        df.formataData( mod.getDataContrato() ),
                        dataEncS,
                        finalizadaS
                    } );
                }

                gui.getHistoricoModsGUI().getTO().setVisivel( true ); 
            } catch (DAOException ex) {
                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }            
    
}
