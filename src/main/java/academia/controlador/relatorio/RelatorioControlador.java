package academia.controlador.relatorio;

import academia.Consts;
import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.BDConsts;
import academia.bd.dao.DAOException;
import academia.bd.to.AlunoTO;
import academia.bd.to.BDConfigTO;
import academia.bd.to.ConfigTO;
import academia.bd.to.PagamentoTO;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.edit.EditAlunoControlador;
import academia.gui.relatorio.RelatorioGUIListener;
import academia.gui.relatorio.RelatorioGUITO;
import academia.relatorio.VisualizarPDFException;
import academia.relatorio.GerenciadorRelatorio;
import academia.relatorio.dodia.RelatorioDiaBean;
import academia.relatorio.dodia.RelatorioDiaTO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import com.itextpdf.text.DocumentException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RelatorioControlador implements RelatorioGUIListener {

    private ControladorTO cTO;
    
    public RelatorioControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
        
    public void gerarRelatorioDiaBTAcionado( RelatorioGUITO to ) {
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        DataUtil df = cTO.getDataUtil();
                        
        GerenciadorRelatorio grel = cTO.getGerenciadorRelatorio();
                        
        List<String> erros = this.validaParamsRelatorioDia( to );
        if ( erros.isEmpty() ) {            
            Timestamp dataDia = null;
            try {
                Date data = df.converteData( to.getDataDia() );
                dataDia = new Timestamp( data.getTime() );
            } catch ( ParseException e ) {
                
            }    
            
            try {
                List<PagamentoTO> pags = bd.getPagamentoDAO().buscaPagsPorDataDia( dataDia );
                
                RelatorioDiaBean relpag = new RelatorioDiaBean();
                relpag.setDataDia( dataDia );
                
                List<RelatorioDiaTO> rpags = new ArrayList();
                for( PagamentoTO p : pags ) {
                    RelatorioDiaTO rpag = new RelatorioDiaTO();
                    
                    int usuarioID = p.getUsuarioID();
                    int matID = p.getMatID();                                        
                    double valor = p.getValor();
                    double desconto = p.getDesconto();
                    Timestamp dataPag = p.getDataPag();                                        
                    
                    String nomeUsuario = bd.getUsuarioDAO().buscaNome( usuarioID );
                    String nomeAluno = bd.getMatriculaDAO().buscaNomeAluno( matID );
                    
                    //double descontoAluno = bd.getPagamentoBO().calculaDescontoAluno( matID, dataPag );
                                        
                    double valorLiquido = valor - ( ( valor * desconto ) / 100d );
                    
                    rpag.setNomeUsuario( nomeUsuario );
                    rpag.setNomeAluno( nomeAluno );
                    rpag.setValor( valorLiquido ); 
                    rpag.setDataPag( dataPag ); 
                    
                    rpags.add( rpag );
                }
                
                relpag.setPagamentos( rpags ); 
                                                
                OutputStream out = new FileOutputStream( Consts.REL_DIA_ARQUIVO );
                grel.getRelatorioDiaGerador().geraRelatorio( out, relpag );
                
                grel.visualizarPDF( Consts.REL_DIA_ARQUIVO ); 
            } catch ( IOException | DocumentException | VisualizarPDFException e ) {
                String msgErro = null;
                if ( e instanceof VisualizarPDFException ) {
                    msgErro = MSGConsts.RELATORIO_NAO_MOSTRADO.replace( "%1", e.getMessage() );
                } else {
                    msgErro = MSGConsts.RELATORIO_NAO_GERADO.replace( "%1", e.getMessage() );
                }                                    
                msgUtil.mostraErro( msgErro, MSGConsts.RELATORIO_TITULO );

                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, e);
            } catch ( DAOException ex ) {
                Logger.getLogger(RelatorioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }                                               
        } else {
            String msg = msgUtil.constroiMSG( erros );
            msgUtil.mostraAlerta( msg, MSGConsts.RELATORIO_TITULO ); 
        }
    }

    public void gerarRelatorioAlunosEmDiaBTAcionado(RelatorioGUITO to) {
        BD bd = cTO.getBD();
        BDConfigTO bdConfig = cTO.getBDConfig();
        GerenciadorRelatorio gr = cTO.getGerenciadorRelatorio();
        
        MSGUtil msgUtil = cTO.getMSGUtil();
        DataUtil df = cTO.getDataUtil();
        
        try {
            int contEmDia = 0;
            int contEmTolerancia = 0;
            int contEmDebito = 0;
            Date dataAtual = df.apenasData( new Date() );
            
            int diariaAlunoID = bdConfig.getDiariaAlunoID();
            
            List<AlunoTO> alunos = bd.getAlunoDAO().buscaTodos( true, diariaAlunoID );
            for( AlunoTO a : alunos ) {
                int matID = a.getMatriculaCorrente();                
                
                if ( matID != BDConsts.ID_NULO ) {
                    Timestamp pagoAte = bd.getPagamentoBO().pagoAte( matID );

                    Date pagoAteDT = df.apenasData( pagoAte );                
                    if ( pagoAteDT.compareTo( dataAtual ) >= 0 ) {
                        contEmDia++;
                    } else {
                        ConfigTO configBDTO = bd.getConfigDAO().busca();
                        Calendar pagoAteC = Calendar.getInstance();
                        pagoAteC.setTime( pagoAteDT );
                        pagoAteC.add( Calendar.DAY_OF_MONTH, configBDTO.getPGTolerancia() );                               

                        pagoAteDT = df.apenasData( pagoAteC.getTime() );                                          
                        if ( pagoAteDT.compareTo( dataAtual ) >= 0 ) {
                            contEmTolerancia++;
                        } else {                                         
                            contEmDebito++;   
                        }                    
                    }
                }
            }
            
            OutputStream arqOut = new FileOutputStream( Consts.REL_ALUNOS_EM_DIA_ARQUIVO );
            
            gr.getRelatorioAlunoEmDiaGerador().geraRelatorio( arqOut, contEmDia, contEmTolerancia, contEmDebito );
            
            gr.visualizarPDF( Consts.REL_ALUNOS_EM_DIA_ARQUIVO ); 
        } catch ( IOException | DocumentException | VisualizarPDFException e ) {
            String msgErro = null;
            if ( e instanceof VisualizarPDFException ) {
                msgErro = MSGConsts.RELATORIO_NAO_MOSTRADO.replace( "%1", e.getMessage() );
            } else {
                msgErro = MSGConsts.RELATORIO_NAO_GERADO.replace( "%1", e.getMessage() );
            }                                    
            msgUtil.mostraErro( msgErro, MSGConsts.RELATORIO_TITULO );

            Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, e);
        } catch ( DAOException ex ) {
            Logger.getLogger(RelatorioControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private List<String> validaParamsRelatorioDia( RelatorioGUITO to ) {
        DataUtil df = cTO.getDataUtil();
        
        List<String> erros = new ArrayList();
        
        try {
            df.converteData( to.getDataDia() );
        } catch ( ParseException e ) {
            erros.add( MSGConsts.DATA_REL_DIA_INVALIDA );
        }
        
        return erros;
    }    
            
}
