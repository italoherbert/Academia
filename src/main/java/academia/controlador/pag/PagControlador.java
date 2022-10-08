package academia.controlador.pag;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.bean.ModalidadeBean;
import academia.bd.to.PagamentoTO;
import academia.bd.dao.DAOException;
import academia.bd.to.BDConfigTO;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.bd.to.parcela.ParcelaBean;
import academia.bd.to.parcela.ParcelaModalidadeBean;
import academia.bd.to.parcela.VisaoParcelaBean;
import academia.bd.to.parcela.ParcelasBean;
import academia.gui.GUI;
import academia.gui.pag.PagamentoGUITO;
import academia.gui.pag.visualizar.VisualizarParcelaGUITO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.NumeroFormatador;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.gui.tabela.TabelaMD;
import academia.gui.pag.PagamentoGUIListener;
import java.sql.Timestamp;
import java.text.ParseException;

public class PagControlador implements PagamentoGUIListener {

    private ControladorTO cTO;
    
    public PagControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void editarParcelaFuncAcionada( PagamentoGUITO to ) {
        GUI gui = cTO.getGUI();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        NumeroFormatador nf = cTO.getNumeroFormatador();
        DataUtil df = cTO.getDataUtil();
        
        ParcelasBean parcelas = cTO.getPagSessao().getParcelas();
        
        TabelaMD pgsTBLMD = to.getPGsTBLMD();
                
        int linha = pgsTBLMD.getIndiceLinhaSelecionada();
        if ( linha == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUMA_PARCELA_SEL, MSGConsts.PAG_TITULO ); 
        } else {
            String idS = pgsTBLMD.getCelulaValor( linha, 0 );
            int id = Integer.parseInt( idS );
            
            VisualizarParcelaGUITO parcelaTO = gui.getVisualizarParcelaGUI().getTO();
            TabelaMD parcelasTBLMD = parcelaTO.getParcelaTBLMD();
            
            VisaoParcelaBean guiParcela = parcelas.getMD().buscaParcela( id );
            if ( guiParcela == null )
                throw new RuntimeException( "ERRO: Objeto GUIParcelaBean nulo, ID não encontrado." );
                        
            ParcelaBean cp = guiParcela.getCParcela();            
            
            parcelasTBLMD.removeTodasAsLinhas();
            
            List<ParcelaModalidadeBean> cpmods = cp.getModalidades();
            
            double subtotal = 0;
            for( ParcelaModalidadeBean cpm : cpmods ) {
                ModalidadeBean m = cpm.getModalidade();
                
                String quantDias = "1 mês";
                if ( cpm.getQuantDias() < 30 )
                    quantDias = String.valueOf( cpm.getQuantDias() )+" dias";
                
                parcelasTBLMD.addLinha( new String[] {                    
                    String.valueOf( m.getMatModID() ), 
                    m.getDescricao(),
                    df.formataData( m.getDataContrato() ),
                    nf.formatoReal( cpm.getTotalBruto() ),
                    quantDias,
                    nf.formatoFlutuante( guiParcela.getDescontoAluno() ),
                    nf.formatoFlutuante( guiParcela.getDescontoPag() ),
                    nf.formatoReal( cpm.getTotalLiquido() ) 
                } );
                
                subtotal += cpm.getTotalBruto();
            }
                        
            parcelaTO.setDataParcela( df.formataData( guiParcela.getDataParcela() ) ); 
            parcelaTO.setSubtotal( nf.formatoReal( subtotal ) ); 
            parcelaTO.setTotal( nf.formatoReal( cp.getValorTotal() ) );                         
            parcelaTO.setVisivel( true ); 
        }
    }
    
    public void esconderParcelasPagasCBAlterado( PagamentoGUITO to ) {
        PagCtrlUtil pagCUtil = cTO.getPagCUtil();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        try { 
            pagCUtil.calculaParcelas( to );            
        } catch ( GeraParcelaException e ) {
            msgUtil.mostraErro( e.getMessage(), MSGConsts.PAG_TITULO ); 
        } catch ( DAOException ex ) {
            Logger.getLogger(PagControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void quitarProxDebitoBTAcionado( PagamentoGUITO to ) {
        BD bd = cTO.getBD();
        GUI gui = cTO.getGUI();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();        
        MSGUtil msgUtil = cTO.getMSGUtil();

        NumeroFormatador nf = cTO.getNumeroFormatador();
                
        PagCtrlUtil pagCUtil = cTO.getPagCUtil();
        
        PagSessao pagSessao = cTO.getPagSessao();
        UsuarioTO usuarioLogado = cTO.getUsuarioLogado();
        
        ParcelasBean parcelasBean = pagSessao.getParcelas();
        
        VisaoParcelaBean parcela = parcelasBean.getMD().buscaProxParcelaPendente();
        if ( parcela == null ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUMA_PARCELA_PENDENTE, MSGConsts.PAG_TITULO ); 
        } else {            
            int matID = pagSessao.getMatID();
            int usuarioID = usuarioLogado.getID();
            
            double desconto = 0;
            try {
                desconto = nf.valorFlutuante( to.getDescontoProxDeb() );
            } catch ( ParseException e ) {

            }                                    
                        
            try {                                
                PagamentoTO pag = new PagamentoTO();
                pag.setMatID( matID );
                pag.setUsuarioID( usuarioID ); 

                pag.setValor( parcela.getCParcela().getValorSubtotalSemDescontoPag() );                 
                pag.setDesconto( desconto ); 
                pag.setDataPag( new Timestamp( System.currentTimeMillis() ) ); 

                bd.getPagamentoDAO().insere( pag );

                to.limpar();
                pagCUtil.calculaParcelas( to ); 

                alunoCUtil.filtrar( gui.getJP().getAlunoGUI().getFiltroGUI().getTO() );
                
                msgUtil.mostraInfo( MSGConsts.PAG_REALIZADO, MSGConsts.PAG_TITULO );                
            } catch ( GeraParcelaException e ) {
                msgUtil.mostraErro( e.getMessage(), MSGConsts.PAG_TITULO ); 
            } catch (DAOException ex) {
                Logger.getLogger(PagControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    public void quitarDebitosBTAcionado( PagamentoGUITO to ) {
        BD bd = cTO.getBD();        
        GUI gui = cTO.getGUI();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();        
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        PagCtrlUtil pagCUtil = cTO.getPagCUtil();
        
        PagSessao pagSessao = cTO.getPagSessao();
        UsuarioTO usuarioLogado = cTO.getUsuarioLogado();
        ParcelasBean parcelasBean = pagSessao.getParcelas();
                            
        int matID = pagSessao.getMatID();
        int usuarioID = usuarioLogado.getID();

        int quant = 0;
        try {
            quant = Integer.parseInt( to.getQuantParcelasDebs() );
        } catch ( NumberFormatException e ) {

        }

        double desconto = 0;
        try {
            desconto = nf.valorFlutuante( to.getDescontoDebs() );
        } catch ( ParseException e ) {

        }    

        if ( quant <= 0 ) {
            msgUtil.mostraAlerta( MSGConsts.QUANT_PARCELAS_INVALIDA, MSGConsts.PAG_TITULO ); 
        } else {
            try {
                List<VisaoParcelaBean> parcelas = parcelasBean.getMD().buscaParcelasPendentes();
                int tam = parcelas.size();
                for( int i = 0; i < quant; i++ ) {
                    PagamentoTO pag = new PagamentoTO();
                    pag.setUsuarioID( usuarioID ); 
                    pag.setMatID( matID );

                    double total = 0;
                    if ( i < tam ) {              
                        VisaoParcelaBean guiP = parcelas.get( i );
                                                
                        total = guiP.getCParcela().getValorSubtotalSemDescontoPag();
                    } else {                        
                        total = bd.getPagamentoBO().calculaTotalComDescontoAluno( matID );                                                                                        
                    }
                    
                    pag.setValor( total ); 
                    pag.setDesconto( desconto ); 
                    pag.setDataPag( new Timestamp( System.currentTimeMillis() ) ); 

                    bd.getPagamentoDAO().insere( pag );
                }
                
                to.limpar();
                pagCUtil.calculaParcelas( to );                 

                alunoCUtil.filtrar( gui.getJP().getAlunoGUI().getFiltroGUI().getTO() );
                
                msgUtil.mostraInfo( MSGConsts.PAG_REALIZADO, MSGConsts.PAG_TITULO ); 
            } catch ( GeraParcelaException e ) {
                msgUtil.mostraErro( e.getMessage(), MSGConsts.PAG_TITULO ); 
            } catch (DAOException ex) {
                Logger.getLogger(PagControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                    
    }
    
    public void removerUltPagBTAcionado(PagamentoGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        ParcelasBean parcelasBean = cTO.getPagSessao().getParcelas();
        MSGUtil msgUtil = cTO.getMSGUtil();
        BDConfigTO bdConfig = cTO.getBDConfig();
        PagCtrlUtil pagCUtil = cTO.getPagCUtil();
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        UsuarioTO usuarioLogado = cTO.getUsuarioLogado();
        
        VisaoParcelaBean ultParPaga = parcelasBean.getMD().buscaUltimaParcelaPaga();                
        if ( ultParPaga == null ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUMA_PARCELA_PAGA, MSGConsts.PAG_TITULO ); 
        } else {
            int pagUID = ultParPaga.getPagamento().getUsuarioID();
            int logUID = usuarioLogado.getID();
            
            int logUTipo = usuarioLogado.getTipoID();
            
            try {
                if ( logUID == pagUID || logUTipo == bdConfig.getAdminUsuarioTipoID() ) {            
                    int pagID = ultParPaga.getPagamento().getID();            
                    try {
                        bd.getPagamentoDAO().remove( pagID );
                        pagCUtil.calculaParcelas( to ); 
                        
                        alunoCUtil.filtrar( gui.getJP().getAlunoGUI().getFiltroGUI().getTO() );
                        
                        msgUtil.mostraInfo( MSGConsts.PAG_REMOVIDO, MSGConsts.PAG_TITULO ); 
                    } catch ( GeraParcelaException e ) {
                        msgUtil.mostraErro( e.getMessage(), MSGConsts.PAG_TITULO ); 
                    }
                } else {
                    String nome = bd.getUsuarioDAO().buscaNome( pagUID );
                    String msg = MSGConsts.TENTATIVA_REM_PARCELA_OUTRO_FUNC.replace( "%1", nome );
                    msgUtil.mostraAlerta( msg, MSGConsts.PAG_TITULO ); 
                }
            } catch (DAOException ex) {
                Logger.getLogger(PagControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void atualizaQuitProxDeb( PagamentoGUITO to ) {
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        double proxDebValor = 0;
        double desconto = 0;
        
        try {
            proxDebValor = nf.valorFlutuante( to.getProxDeb() );
        } catch ( ParseException e ) {
            
        }
        
        try {
            desconto = nf.valorFlutuante( to.getDescontoProxDeb() );            
        } catch ( ParseException e ) {
            
        }
        
        double total = proxDebValor - ( ( proxDebValor * desconto ) / 100d );
        
        to.setTotalProxDeb( nf.formatoReal( total ) ); 
    }
    
    public void atualizaQuitDebs( PagamentoGUITO to ) {
        BD bd = cTO.getBD();
        
        PagSessao pagSessao = cTO.getPagSessao();
        ParcelasBean parcelasBean = pagSessao.getParcelas();
                        
        NumeroFormatador nf = cTO.getNumeroFormatador();
                
        int quantParcelas = 0;
        double desconto = 0;
                
        try {
            quantParcelas = Integer.parseInt( to.getQuantParcelasDebs() );                
        } catch ( NumberFormatException e ) {
            
        }
        try {
            desconto = nf.valorFlutuante( to.getDescontoDebs() );
        } catch ( ParseException e ) {
            
        }
           
        int matID = pagSessao.getMatID();
        List<VisaoParcelaBean> parcelasPendentes = parcelasBean.getMD().buscaParcelasPendentes();        
        
        try {        
            double total = 0;
            int tam = parcelasPendentes.size();
            for ( int i = 0; i < quantParcelas; i++ ) {
                if ( i < tam ) {
                    VisaoParcelaBean pp = parcelasPendentes.get( i );                    
                    total += pp.getCParcela().getValorTotal();                
                } else { 
                    total += bd.getPagamentoBO().calculaTotalComDescontoAluno( matID );                
                }
            }
            
            total -= ( ( total * desconto ) / 100d );            
                        
            to.setTotalDebs( nf.formatoReal( total ) );                 
        } catch ( DAOException ex ) {
            Logger.getLogger(PagControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
