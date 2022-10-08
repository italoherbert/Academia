package academia.controlador.mensalidade;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.to.MensalidadeTO;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.controlador.mensalidade.limpar.NovoValMensLprFormDrv;
import academia.controlador.modalidade.ModSessao;
import academia.controlador.modalidade.ModalidadeCtrlUtil;
import academia.gui.GUI;
import academia.gui.mensalidade.MensalidadeGUITO;
import academia.gui.mensalidade.novovalor.NovoValorMensalidadeGUIListener;
import academia.gui.mensalidade.novovalor.NovoValorMensalidadeGUITO;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;
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

public class NovoValorMensControlador implements NovoValorMensalidadeGUIListener {
    
    private ControladorTO cTO;

    public NovoValorMensControlador(ControladorTO cTO) {
        this.cTO = cTO;
    }

    public void alterarBTAcionado( NovoValorMensalidadeGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        NumeroFormatador nf = cTO.getNumeroFormatador();
        DataUtil df = cTO.getDataUtil();
        
        MensalidadeCtrlUtil mensCUtil = cTO.getMensCUtil();
        ModalidadeCtrlUtil modCUtil = cTO.getModCUtil();
        
        ModSessao modSessao = cTO.getModSessao();
        
        List<String> erros = this.validaForm( to );
        if ( erros.isEmpty() ) {        
            try {
                String novoValorS = to.getNovoValor();
                double novoValor = nf.valorFlutuante( novoValorS );
                Date dataReg = df.converteData( to.getDataRegistro() );

                Timestamp dataRegistro = new Timestamp( dataReg.getTime() ); 

                MensalidadeTO novaMen = new MensalidadeTO();
                novaMen.setModID( modSessao.getModID() );
                novaMen.setValor( novoValor ); 
                novaMen.setDataAlter( dataRegistro );

                bd.getMensalidadeDAO().insere( novaMen );            

                MensalidadeGUITO mensTO = gui.getMensalidadeGUI().getTO();
                mensCUtil.carregarMensalidades( mensTO ); 
                
                ModalidadeFiltroGUITO modFTO = gui.getJP().getModalidadeGUI().getFiltroGUI().getTO();
                modCUtil.filtra( modFTO );                                

                mensTO.setValorAtual( nf.formatoReal( novoValor ) );
                
                to.setVisivel( false ); 
                                                
                msgUtil.mostraInfo( MSGConsts.VALOR_MENS_ALTERADO, MSGConsts.MENS_TITULO );               
            } catch ( ParseException e ) {
                msgUtil.mostraErro( MSGConsts.INCONSISTENCIA_NOVO_VALOR_MENS, MSGConsts.MENS_TITULO ); 
            } catch (DAOException ex) {
                Logger.getLogger(NovoValorMensControlador.class.getName()).log(Level.SEVERE, null, ex);
            }        
        } else {
            String msg = msgUtil.constroiMSG( erros );
            
            msgUtil.mostraAlerta( msg, MSGConsts.MENS_TITULO ); 
        }
    }

    public void limparBTAcionado(NovoValorMensalidadeGUITO to) {
        NovoValMensLprFormDrv novoValMLprDrv = new NovoValMensLprFormDrv( cTO );
        to.limpar( novoValMLprDrv );
    }
        
    private List<String> validaForm( NovoValorMensalidadeGUITO to ) {
        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        List<String> erros = new ArrayList();
                        
        try {
            double val = nf.valorFlutuante( to.getNovoValor() );
            if ( val < 0 )
                erros.add( MSGConsts.NOVO_VALOR_MENS_INVALIDO );
        } catch ( ParseException e ) {
            erros.add( MSGConsts.NOVO_VALOR_MENS_INVALIDO );
        }
        
        try {
            df.converteData( to.getDataRegistro() );
        } catch ( ParseException e ) {
            erros.add(MSGConsts.DATA_REGISTRO_MOD_INVALIDA ); 
        }
                
        return erros;
    }
    
}
