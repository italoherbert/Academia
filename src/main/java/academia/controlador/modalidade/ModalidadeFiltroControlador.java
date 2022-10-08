package academia.controlador.modalidade;

import academia.MSGConsts;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUIListener;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModalidadeFiltroControlador implements ModalidadeFiltroGUIListener {

    private ControladorTO cTO;
    
    public ModalidadeFiltroControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void filtrarBTAcionado( ModalidadeFiltroGUITO to ) {        
        ModalidadeCtrlUtil modCUtil = cTO.getModCUtil();
        MSGUtil msgUtil = cTO.getMSGUtil();
        try {             
            int cont = modCUtil.filtra( to ); 
            if ( cont <= 0 )                              
                msgUtil.mostraAlerta( MSGConsts.NENHUMA_MOD_ENCONTRADA, MSGConsts.MOD_TITULO ); 
            
        } catch (DAOException ex) {
            Logger.getLogger(ModalidadeFiltroControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarInativasCBAlterado( ModalidadeFiltroGUITO to ) {
        ModalidadeCtrlUtil modCUtil = cTO.getModCUtil();
        
        try { 
            modCUtil.filtra( to );                         
        } catch (DAOException ex) {
            Logger.getLogger(ModalidadeFiltroControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
