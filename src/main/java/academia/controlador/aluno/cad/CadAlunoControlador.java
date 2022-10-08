package academia.controlador.aluno.cad;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.bean.AlunoBean;
import academia.bd.bean.ModalidadeBean;
import academia.bd.dao.DAOException;
import academia.bd.to.DescontoTO;
import academia.bd.to.MedidasTO;
import academia.bd.to.PagamentoTO;
import academia.bd.to.UsuarioTO;
import academia.config.Config;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.controlador.pag.GeraParcelaException;
import academia.bd.to.parcela.ParcelaBean;
import academia.gui.GUI;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.gui.aluno.pnl.AlunoFormGUITO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.NumeroFormatador;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import academia.gui.aluno.form.cad.CadAlunoFormGUITO;
import academia.gui.aluno.form.cad.medidas.CadMedidasGUITO;
import academia.gui.aluno.form.cad.modalidade.CadModalidadeGUITO;
import academia.util.ModalidadeFormatador;
import academia.gui.aluno.form.cad.CadAlunoFormGUIListener;
import academia.gui.painels.medidas.MedidasFormPNLTO;

public class CadAlunoControlador implements CadAlunoFormGUIListener {

    private ControladorTO cTO;
    
    public CadAlunoControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void finalizarBTAcionado( CadAlunoFormGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        DataUtil dutil = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        Config config = cTO.getConfig();
        
        UsuarioTO usuarioLogado = cTO.getUsuarioLogado();
                
        try {
            AlunoFormGUITO alunoGUITO = to.getAlunoTO();
            CadMedidasGUITO medidasTO = to.getMedidasTO();
            CadModalidadeGUITO modalidadesTO = to.getModalidadesTO();
            
            Date dataMat = dutil.converteData( alunoGUITO.getDataMatricula() );
            Timestamp dataMatTS = new Timestamp( dataMat.getTime() );
                                    
            Date dataPag = dutil.calculaDataPag( dataMat );
            Timestamp dataPagTS = new Timestamp( dataPag.getTime() );            
            
            Timestamp dataNascTS = null;
            try {                                            
                Date dataNasc = dutil.converteData( alunoGUITO.getDataNasc() );
                dataNascTS = new Timestamp( dataNasc.getTime() );
            } catch ( ParseException e ) {

            }
            
            AlunoBean aluno = new AlunoBean();
            aluno.setNome( alunoGUITO.getNome() );            
            aluno.setObs( alunoGUITO.getObs() );
            aluno.setDataNasc( dataNascTS ); 
            aluno.setDataMatricula( dataMatTS );
            aluno.setDataDiaPag( dataPagTS );
            aluno.setDesconto( nf.valorFlutuante( alunoGUITO.getDesconto() ) );                        
                                                
            MedidasTO medidas = null;            
            if ( config.isCarregarFuncMedidas() && !to.isPularMedidas() ) {
                medidas = new MedidasTO();
                medidas.setPeso( Integer.parseInt( medidasTO.getPeso() ));
                medidas.setAltura( nf.valorFlutuante( medidasTO.getAltura() ) );
                medidas.setBracoEsquerdo( Integer.parseInt( medidasTO.getBracoEsquerdo() ) );
                medidas.setAntebracoEsquerdo( Integer.parseInt( medidasTO.getAntebracoEsquerdo() ) );
                medidas.setBracoDireito( Integer.parseInt( medidasTO.getBracoDireito() ) );
                medidas.setAntebracoDireito( Integer.parseInt( medidasTO.getAntebracoDireito() ) ); 
                medidas.setTorax( Integer.parseInt( medidasTO.getTorax() ) );
                medidas.setCintura( Integer.parseInt( medidasTO.getCintura() ) );
                medidas.setBumbum( Integer.parseInt( medidasTO.getBumbum() ) );
                medidas.setCoxaEsquerda( Integer.parseInt( medidasTO.getCoxaEsquerda() ) );
                medidas.setPanturrilhaEsquerda( Integer.parseInt( medidasTO.getPanturrilhaEsquerda() ) ); 
                medidas.setCoxaDireita( Integer.parseInt( medidasTO.getCoxaDireita() ) );
                medidas.setPanturrilhaDireita( Integer.parseInt( medidasTO.getPanturrilhaDireita() ) ); 
            }
                        
            List<Integer> modalidadeIDs = new ArrayList();
            String[] mods = modalidadesTO.getAlunoMods();
            for( String mod : mods ) {
                int id = mf.extraiModalidadeID( mod );
                modalidadeIDs.add( id ); 
            }            

            aluno.setMedidas( medidas );
            aluno.setModalidadeIDs( modalidadeIDs ); 

            int alunoID = bd.getAlunoDAO().insere( aluno );

            if ( to.getAlunoTO().isInserirPagamento() ) {
                double desconto = aluno.getDesconto();
                
                int matID = bd.getAlunoDAO().buscaMatCorr( alunoID );
                List<ModalidadeBean> modalidades = bd.getMatriculaDAO().buscaModalidades( matID );
                try {
                    
                    /* DESCONTO_PAG igual aluno 0  --> Quarto parâmetro*/
                    ParcelaBean parc = bd.getPagamentoBO().geraParcela( modalidades, dataPagTS, desconto, 0 );             
                    
                    PagamentoTO pag = new PagamentoTO();
                    pag.setMatID( matID );
                    pag.setUsuarioID( usuarioLogado.getID() ); 
                    pag.setDataPag( dataPagTS ); 
                    pag.setValor( parc.getValorSubtotalSemDescontoPag() ); /* FUNCIONARIA O MÉTODO GET_VALOR_TOTAL();*/
                    pag.setDesconto( 0 );                

                    bd.getPagamentoDAO().insere( pag ); 
                } catch ( GeraParcelaException e ) {               
                    msgUtil.mostraErro( e.getMessage(), MSGConsts.ALUNO_TITULO ); 
                }
            }
            
            AlunoFiltroGUITO filtroTO = gui.getJP().getAlunoGUI().getFiltroGUI().getTO();
            alunoCUtil.filtrar( filtroTO );             
            alunoCUtil.atualizaQuantAnivers();

            to.setVisivel( false );                                 
            
            String msg = MSGConsts.DADOS_SALVOS;
            if ( to.getAlunoTO().isInserirPagamento() )
                msg += "\n" + MSGConsts.PAG_REALIZADO;            
            
            msgUtil.mostraInfo( msg, MSGConsts.ALUNO_TITULO );                                                                            
        } catch ( ParseException ex ) {
            msgUtil.mostraErro( MSGConsts.INCONSISTENCIA_CAD_ALUNO, MSGConsts.ALUNO_TITULO ); 
        } catch ( DAOException ex ) {
            Logger.getLogger(CadAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void limparBTAcionado( CadAlunoFormGUITO to ) {        
        to.limpar( new CadLimparAlunoGUICB( cTO ) );        
    }

    public void aposMostradoPainelMedidas(CadAlunoFormGUITO to) {
        to.getMedidasTO().setDataReg( to.getAlunoTO().getDataMatricula() ); 
    }

    public void aposMostradoPainelModalidades(CadAlunoFormGUITO to) {        
        MSGUtil msgUtil = cTO.getMSGUtil();
        DataUtil df = cTO.getDataUtil();        
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();        
        
        try { 
            String dataMatS = to.getAlunoTO().getDataMatricula();
            Date dataMatD = df.converteData( dataMatS );
            Timestamp dataMatTS = new Timestamp( dataMatD.getTime() );
            
            List<String> modsNF = alunoCUtil.modalidadesNaoFinalizadas( dataMatTS );            
            String[] alMods = to.getModalidadesTO().getAlunoMods();
            
            List<String> mods = new ArrayList();
            for( String modNF : modsNF ) {
                boolean achou = false;
                for( int i = 0; !achou && i < alMods.length; i++ ) {
                    String alMod = alMods[ i ];
                    if ( modNF.equals( alMod ) ) 
                        achou = true;
                }
                if ( !achou )
                    mods.add( modNF );
            }            
            
            to.getModalidadesTO().setOutrasMods( mods ); 
        } catch ( ParseException e ) {
            msgUtil.mostraErro( MSGConsts.INCONSISTENCIA_CAD_ALUNO, MSGConsts.ALUNO_TITULO );             
        } catch ( DAOException ex ) {
            Logger.getLogger(CadAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    public boolean validaAlunoForm( CadAlunoFormGUITO to ) {
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        AlunoFormGUITO alunoTO = to.getAlunoTO();
        
        try {
            List<String> erros = alunoCUtil.validaCadAlunoForm( alunoTO );
            if ( !erros.isEmpty()) {
                String msg = msgUtil.constroiMSG( erros );
                msgUtil.mostraAlerta( msg, MSGConsts.ALUNO_TITULO ); 
            }
            return erros.isEmpty();
        } catch ( DAOException ex ) {
            Logger.getLogger(CadAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }                    
        return true;
    }  
    
    public boolean validaMedidasForm( CadAlunoFormGUITO to) {
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        MedidasFormPNLTO medsTO = to.getMedidasTO();
        
        if ( !to.isPularMedidas() ) {
            List<String> erros = alunoCUtil.validaCadMedidasForm( medsTO );
            if ( !erros.isEmpty()) {
                String msg = msgUtil.constroiMSG( erros );
                msgUtil.mostraAlerta( msg, MSGConsts.ALUNO_TITULO ); 
            }
            return erros.isEmpty();
        }
        
        return true;
    }                    
        
}
