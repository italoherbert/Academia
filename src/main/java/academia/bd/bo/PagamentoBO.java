package academia.bd.bo;

import academia.Consts;
import academia.MSGConsts;
import academia.bd.BDConsts;
import academia.bd.bean.DataDiaPagPGQuantBean;
import academia.bd.bean.ModalidadeBean;
import academia.bd.dao.ConfigDAO;
import academia.bd.dao.DAOException;
import academia.bd.dao.MatriculaDAO;
import academia.bd.dao.ModalidadeDAO;
import academia.bd.dao.PagamentoDAO;
import academia.bd.to.ConfigTO;
import academia.bd.to.DescontoTO;
import academia.bd.to.MatriculaTO;
import academia.bd.to.MensalidadeTO;
import academia.bd.to.PagamentoTO;
import academia.controlador.pag.GeraParcelaException;
import academia.bd.to.parcela.ParcelaBean;
import academia.bd.to.parcela.ParcelaModalidadeBean;
import academia.bd.to.parcela.VisaoParcelaBean;
import academia.bd.to.parcela.ParcelasBean;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PagamentoBO {

    private PagamentoDAO pagDAO;
    private MatriculaDAO matDAO;
    private ModalidadeDAO modDAO;
    private ConfigDAO configDAO;
    private PagamentoBODriver drv;

    public PagamentoBO( PagamentoBODriver drv, PagamentoDAO pagDAO, MatriculaDAO matDAO, ModalidadeDAO modDAO, ConfigDAO configDAO ) {
        this.pagDAO = pagDAO;
        this.matDAO = matDAO;
        this.modDAO = modDAO;
        this.configDAO = configDAO;
        this.drv = drv;
    }        
    
    public double calculaTotalComDescontoAluno( int matID ) throws DAOException {        
        double total = 0;         
                
        List<ModalidadeBean> mods = matDAO.buscaModalidades( matID );
        for( ModalidadeBean m : mods ) {            
            if ( m.getDataEncerramento() == null ) {
                int modID = m.getID();            
                MensalidadeTO mensalidade = modDAO.buscaMensalidadeAtual( modID );

                total += mensalidade.getValor();
            }
        }
        
        Timestamp dataAtual = new Timestamp( System.currentTimeMillis() );
        List<DescontoTO> descontos = matDAO.buscaDescontos( matID );
        double descontoAluno = this.calculaDescontoAluno( descontos, dataAtual );
            
        total -= ( ( total * descontoAluno ) / 100d );
        
        return total;
    }
    
    public double calculaDesconto( double valor, double descontoPag, double descontoAluno ) {                            
        double total = valor;
        total = total - ( ( total * descontoAluno ) / 100d );                
        total = total - ( ( total * descontoPag   ) / 100d );                
        return total;
    }
    
    public Timestamp pagoAte( int matID ) throws DAOException {        
        if ( matID == BDConsts.ID_NULO )
            return null;
        
        DataDiaPagPGQuantBean dmpgs = matDAO.buscaDataDiaPagPGQuant( matID );
        Timestamp dataMat = dmpgs.getDataDiaPag();
        int quantPags = dmpgs.getQuantPagamentos();
        
        return this.pagoAte( dataMat, quantPags );
    }
    
    public Timestamp pagoAte( Date dataDiaPag, int quantPags ) {            
        Calendar diaPagC = Calendar.getInstance();
        diaPagC.setTime( dataDiaPag );        
        diaPagC.add( Calendar.MONTH, quantPags );         
        diaPagC.add( Calendar.DAY_OF_MONTH, -1 );
        
        return new Timestamp( diaPagC.getTimeInMillis() );
    }
        
    public MensalidadeTO calculaMensalidade( List<MensalidadeTO> mens, Timestamp dataParcela ) {
        MensalidadeTO men = null;
        MensalidadeTO menAnt = null;
        int tam = mens.size();
        for( int i = 0; men == null && i < tam; i++ ) {
            MensalidadeTO menCorr = mens.get( i );

            Timestamp dataAlter = menCorr.getDataAlter();

            if ( dataParcela.compareTo( dataAlter ) < 0 )
                men = menAnt;                                        
            else menAnt = menCorr;
        }                                   

        if ( men == null && tam > 0 )
            men = mens.get( tam-1 );                                                    
        
        return men;
    }
        
    public double calculaDescontoAluno( int matID, Timestamp dataAtual ) throws DAOException {                
        List<DescontoTO> descontos = matDAO.buscaDescontos( matID );                    
        return this.calculaDescontoAluno( descontos, dataAtual );
    }
    
    public double calculaDescontoAluno( List<DescontoTO> descontos, Timestamp dataParcela ) {
        DescontoTO desc = null;
        DescontoTO descAnt = null;
        int tam = descontos.size();
        for( int i = 0; desc == null && i < tam; i++ ) {
            DescontoTO descCorr = descontos.get( i );

            Timestamp dataAlter = descCorr.getDataAlter();

            if ( dataParcela.compareTo( dataAlter ) < 0 )
                desc = descAnt;                                        
            else descAnt = descCorr;
        }                                   

        if ( desc == null ) {
            if ( tam > 0 )
                return descontos.get( tam-1 ).getPorcentagem();                                                    
        } else {
            return desc.getPorcentagem();
        }
        
        return 0;
    }
    
    public ParcelasBean calculaParcelas( int matID, boolean esconderParcelasPagas ) throws DAOException, GeraParcelaException {                
        ParcelasBean parcelas = new ParcelasBean();
                
        ConfigTO config = configDAO.busca();
        MatriculaTO matricula = matDAO.busca( matID );
        List<ModalidadeBean> modalidades = matDAO.buscaModalidades( matID );
        List<PagamentoTO> pagamentos = matDAO.buscaPagamentos( matID );
        List<DescontoTO> descontos = matDAO.buscaDescontos( matID );
                        
        int tolerancia = config.getPGTolerancia();
                        
        Calendar dataDiaPag = Calendar.getInstance();
        dataDiaPag.setTime( matricula.getDataDiaPag() ); 
        
        Calendar dataFim = Calendar.getInstance();
        if ( matricula.getDataFim() == null ) {
            dataFim.setTime( new Date() );
        } else {
            dataFim.setTime( matricula.getDataFim() );             
        }
                
        List<VisaoParcelaBean> visoesParcelas = new ArrayList();
        int contador = 1;
        
        for( PagamentoTO pg : pagamentos ) {            
            if ( !esconderParcelasPagas ) {
                Timestamp dataParcela = new Timestamp( dataDiaPag.getTimeInMillis() );                                                 
                
                Calendar dataVencC = Calendar.getInstance();
                dataVencC.setTime( dataParcela );
                dataVencC.add( Calendar.DAY_OF_MONTH, tolerancia-1 );                 
                
                Timestamp dataVenc = new Timestamp( dataVencC.getTimeInMillis() );
                
                dataVencC.setTime( dataParcela );
                dataVencC.add( Calendar.MONTH, 1 );             
                
                double descontoAluno = this.calculaDescontoAluno( descontos, dataParcela );
                double descontoPag = pg.getDesconto();
                                
                ParcelaBean parcela = this.geraParcela( modalidades, dataParcela, descontoAluno, descontoPag );                                    

                VisaoParcelaBean visao = new VisaoParcelaBean( parcela );
                visao.setPagsTBLID( contador ); 
                visao.setDataParcela( dataParcela ); 
                visao.setDataVencimento( dataVenc );
                visao.setPagamento( pg ); 
                visao.setDescontoAluno( descontoAluno ); 
                visao.setDescontoPag( descontoPag ); 
                visao.setPendente( false ); 

                visoesParcelas.add( visao );
                contador++;
            }
            dataDiaPag.add( Calendar.MONTH, 1 );
        }        
        
        while( dataDiaPag.compareTo( dataFim ) < 0 ) {
            Timestamp dataParcela = new Timestamp( dataDiaPag.getTimeInMillis() );
                          
            Calendar dataVencC = Calendar.getInstance();
            dataVencC.setTime( dataParcela );
            dataVencC.add( Calendar.DAY_OF_MONTH, tolerancia-1 );
                        
            Timestamp dataVenc = new Timestamp( dataVencC.getTimeInMillis() );                
            
            dataVencC.setTime( dataParcela );
            dataVencC.add( Calendar.MONTH, 1 ); 
            
            double descontoAluno = this.calculaDescontoAluno( descontos, dataParcela );            
            double descontoPag = 0;
            
            ParcelaBean parcela = this.geraParcela( modalidades, dataParcela, descontoAluno, descontoPag );            
            VisaoParcelaBean visao = new VisaoParcelaBean( parcela );
            visao.setPagsTBLID( contador ); 
            visao.setDataParcela( dataParcela ); 
            visao.setDataVencimento( dataVenc );
            visao.setPagamento( null );
            visao.setDescontoAluno( descontoAluno ); 
            visao.setDescontoPag( descontoPag ); 
            visao.setPendente( true );         
                
            visoesParcelas.add( visao );            
            
            dataDiaPag.add( Calendar.MONTH, 1 ); 
            contador++;
        }
        
        parcelas.setVisaoParcelas( visoesParcelas ); 
                
        VisaoParcelaBean parcelaPendente = parcelas.getMD().buscaProxParcelaPendente();        
        double total = 0;
        if ( parcelaPendente != null )
            total = parcelaPendente.getCParcela().getValorTotal();                        
        
        parcelas.setValorProxParcela( total ); 
        
        List<VisaoParcelaBean> parcelasPendentes = parcelas.getMD().buscaParcelasPendentes();        
        int quantParcelasPendentes = parcelasPendentes.size();
        
        total = 0;
        for ( VisaoParcelaBean pp : parcelasPendentes )
            total += pp.getCParcela().getValorTotal();        
        
        parcelas.setQuantParcelas( quantParcelasPendentes );
        parcelas.setValorTotal( total ); 
                                
        Timestamp dataMat = matricula.getDataDiaPag();
        int quantPags = pagamentos.size();
        Date pagoAte = this.pagoAte( dataMat, quantPags );                
                        
        parcelas.setDataDiaPag( matricula.getDataDiaPag() ); 
        parcelas.setPagoAte( new Timestamp( pagoAte.getTime() ) );
        
        parcelas.setNomeAluno( matDAO.buscaNomeAluno( matID ) ); 

        return parcelas;
    }
        
    public ParcelaBean geraParcela( List<ModalidadeBean> mods, 
                                      Timestamp dataParcela,
                                      double descontoAluno,
                                      double descontoPag ) throws GeraParcelaException {                                              
        double valorTotal = 0;        
        double valorSubtotal = 0;
        List<ParcelaModalidadeBean> pmods = new ArrayList();
                
        for( ModalidadeBean m : mods ) {
            List<MensalidadeTO> mens = m.getMensalidades();                         

            if ( mens.isEmpty() ) {
                String msg = MSGConsts.MOD_SEM_MENS.replace( "%1", m.getDescricao() );
                throw new GeraParcelaException( msg );
            }
                        
            double total = 0; 
            double subtotal = 0;
                        
            if ( this.isProcessar( m, dataParcela ) ) {                                                                                                                        
                MensalidadeTO men = this.calculaMensalidade( mens, dataParcela );
                double valorBruto = men.getValor();                                    
                
                Calendar dataContratoC = Calendar.getInstance();
                dataContratoC.setTime( m.getDataContrato() );                             
                if ( dataContratoC.get( Calendar.DAY_OF_MONTH ) > 28 ) {
                    dataContratoC.add( Calendar.MONTH, 1 );
                    dataContratoC.set( Calendar.DAY_OF_MONTH, 1 ); 
                }                
                                               
                long dataContratoMS = dataContratoC.getTimeInMillis();                                                                  
                long dataParcelaMS = dataParcela.getTime();                                
                
                double diasSub = dataParcelaMS - dataContratoMS;                
                diasSub = Math.ceil( diasSub / Consts.QMS_EM_DIA ); 
                
                if ( diasSub == 0 || diasSub == 28 || diasSub == 29 || diasSub == 31 )
                    diasSub = Consts.QDIAS_EM_MES;                                
                          
                /*
                double fatorAjuste = 1.0d;
                if ( diasSub < Consts.QDIAS_EM_MES )
                    fatorAjuste = diasSub / Consts.QDIAS_EM_MES;
                   */
                
                subtotal = valorBruto;// * fatorAjuste;
                subtotal = subtotal - ( ( subtotal * descontoAluno ) / 100d );                
                total = subtotal - ( ( subtotal * descontoPag   ) / 100d );                
                
                ParcelaModalidadeBean cpm = new ParcelaModalidadeBean();
                cpm.setModalidade( m );
                cpm.setTotalBruto( valorBruto );
                cpm.setTotalLiquido( total ); 
                cpm.setQuantDias( (int)diasSub );  
                pmods.add( cpm );
            }
                        
            valorTotal += total;
            valorSubtotal += subtotal;
        }   
        
        ParcelaBean parcela = new ParcelaBean();        
        parcela.setModalidades( pmods );  
        parcela.setValorTotal( valorTotal ); 
        parcela.setValorSubtotalSemDescontoPag( valorSubtotal ); 

        return parcela;                
    }   
    
    private boolean isProcessar( ModalidadeBean m, Timestamp dataParcela ) {
        boolean processar = true;
                                                              
        //MADMOD
        Date dataContrato = drv.apenasData( m.getDataContrato() );                 
        Date dataEncerramento = drv.apenasData( m.getDataEncerramento() );
                                
        if ( dataParcela.compareTo( dataContrato ) >= 0 ) {                
            if ( dataEncerramento == null ) {
                processar = true;
            } else {                
                if ( dataParcela.compareTo( dataEncerramento ) > 0 )
                    processar = false;                                    
            }
        } else {
            processar = false;
        }
                
        return processar;
    } 
    
}
