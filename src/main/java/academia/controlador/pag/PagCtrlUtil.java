package academia.controlador.pag;

import academia.bd.to.parcela.VisaoParcelaBean;
import academia.bd.to.parcela.ParcelasBean;
import academia.Consts;
import academia.GUIConsts;
import academia.bd.BD;
import academia.bd.to.PagamentoTO;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.bd.to.parcela.ParcelaBean;
import academia.gui.pag.PagamentoGUITO;
import academia.util.DataUtil;
import academia.util.NumeroFormatador;
import java.awt.Color;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import libs.gui.tabela.TabelaMD;

public class PagCtrlUtil {
        
    private ControladorTO cTO;

    public PagCtrlUtil( ControladorTO cTO ) {
        this.cTO = cTO;
    }        
    
    public void calculaParcelas( PagamentoGUITO guiTO ) throws DAOException, GeraParcelaException {
        BD bd = cTO.getBD();
        
        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        PagSessao pagSessao = cTO.getPagSessao();
        
        int matID = pagSessao.getMatID();

        ParcelasBean parcelas = bd.getPagamentoBO().calculaParcelas(matID, guiTO.isEsconderPPGsSelecionado() );        
        pagSessao.setParcelas( parcelas ); 
          
        TabelaMD pgsTBLMD = guiTO.getPGsTBLMD();
        pgsTBLMD.removeTodasAsLinhas();
        
        Map<Integer, Color> coresMap = new HashMap();
        int coresContador = 0;
        
        List<VisaoParcelaBean> visaoParcelas = parcelas.getVisaoParcelas();
        int tam = visaoParcelas.size();
        for( int i=tam-1; i >=0; i-- ) {            
            VisaoParcelaBean guiParcela = visaoParcelas.get( i );
            ParcelaBean cParcela = guiParcela.getCParcela();
            
            String dataPagS; 
            if ( guiParcela.isPendente() ) {
                dataPagS = Consts.PENDENTE_TEXTO;
            } else {
                Timestamp dataPag = guiParcela.getPagamento().getDataPag();
                dataPagS = df.formataData( dataPag ); 
            }                        
                        
            Date dataAtual = df.apenasData( new Date() );
            Date dataVenc = df.apenasData( guiParcela.getDataVencimento() );
            
            if ( guiParcela.isPendente() ) {                                
                if ( dataAtual.compareTo( dataVenc ) <= 0 ) {                    
                    coresMap.put( coresContador, GUIConsts.COR_PARCELA_TOLERANCIA );                    
                } else {
                    coresMap.put( coresContador, GUIConsts.COR_PARCELA_ATRAZADA );
                }
            } else {
                coresMap.put( coresContador, GUIConsts.COR_TEXTO_NORMAL );
            }
            
            String funcionario;
            PagamentoTO pag = guiParcela.getPagamento();            
            if ( pag == null ) {
                funcionario = Consts.PENDENTE_TEXTO;
            } else {
                int usuarioID = guiParcela.getPagamento().getUsuarioID();
                String usuarioNome = bd.getUsuarioDAO().buscaNome( usuarioID );            
                if ( usuarioNome == null )
                    funcionario = Consts.PENDENTE_TEXTO;
                else funcionario = usuarioNome;
            }
                                    
            String idS = String.valueOf( guiParcela.getPagsTBLID() ); 
            String dataParcelaS = df.formataData( guiParcela.getDataParcela() );
            String dataVencS = df.formataData( guiParcela.getDataVencimento() );
            String valorS = nf.formatoReal( cParcela.getValorTotal() );            
            
            pgsTBLMD.addLinha(new String[] { 
                idS, funcionario, dataParcelaS, dataVencS, dataPagS, valorS 
            } );
            
            coresContador++;
        }
        pgsTBLMD.pintaTabela( coresMap ); 
                        
        guiTO.setProxDeb( nf.formatoFlutuante( parcelas.getValorProxParcela() ) );
        guiTO.setTotalProxDeb( nf.formatoReal( parcelas.getValorProxParcela() ) );  
                
        guiTO.setQuantParcelasDebs( String.valueOf( parcelas.getQuantParcelas() ) );
        guiTO.setTotalDebs( nf.formatoReal( parcelas.getValorTotal() ) );
                                                        
        guiTO.setDiaPag( df.formataDataDia( parcelas.getDataDiaPag() ) ); 
        guiTO.setPagoAte( df.formataData( parcelas.getPagoAte() ) );         
        
        guiTO.setNomeTitulo( parcelas.getNomeAluno() ); 
    }                                                             
    
}
