package academia.controlador.mensalidade;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.to.MensalidadeTO;
import academia.bd.to.ModalidadeTO;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.controlador.mensalidade.limpar.NovoValMensLprFormDrv;
import academia.controlador.modalidade.ModSessao;
import academia.gui.GUI;
import academia.gui.mensalidade.MensalidadeGUIListener;
import academia.gui.mensalidade.MensalidadeGUITO;
import academia.gui.mensalidade.novovalor.NovoValorMensalidadeGUITO;
import academia.util.MSGUtil;
import academia.util.NumeroFormatador;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MensalidadeControlador implements MensalidadeGUIListener {
 
    private ControladorTO cTO;

    public MensalidadeControlador(ControladorTO cTO) {
        this.cTO = cTO;
    }

    public void alterarValorBTAcionado(MensalidadeGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        ModSessao modSessao = cTO.getModSessao();        
        
        int modID = modSessao.getModID();        
        try {
            MensalidadeTO men = bd.getModalidadeDAO().buscaMensalidadeAtual( modID );            
            if ( men == null ) {
                ModalidadeTO mod = bd.getModalidadeDAO().busca( modID );                
                String desc = mod.getDescricao();
                String msg = MSGConsts.MOD_SEM_MENS.replace( "%1", desc );
                msgUtil.mostraAlerta( msg, MSGConsts.MENS_TITULO );
            } else {                                
                NovoValorMensalidadeGUITO novoValMensTO = gui.getNovoValMensGUI().getTO();
                
                NovoValMensLprFormDrv novoValMLprDrv = new NovoValMensLprFormDrv( cTO );                
                novoValMensTO.limpar( novoValMLprDrv );                
                
                novoValMensTO.setValorAtual( nf.formatoReal( men.getValor() ) ); 
                
                novoValMensTO.setVisivel( true ); 
            }
        } catch (DAOException ex) {
            Logger.getLogger(MensalidadeControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
