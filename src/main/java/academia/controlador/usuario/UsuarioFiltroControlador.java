package academia.controlador.usuario;

import academia.MSGConsts;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.controlador.modalidade.ModalidadeFiltroControlador;
import academia.gui.usuario.filtro.UsuarioFiltroGUIListener;
import academia.gui.usuario.filtro.UsuarioFiltroGUITO;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioFiltroControlador implements UsuarioFiltroGUIListener {

    private ControladorTO cTO;

    public UsuarioFiltroControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void filtrarBTAcionado( UsuarioFiltroGUITO to ) {
        UsuarioCtrlUtil usuarioCUtil = cTO.getUsuarioCUtil();
        MSGUtil msgUtil = cTO.getMSGUtil();
        try {             
            int cont = usuarioCUtil.filtra( to ); 
            if ( cont <= 0 )                              
                msgUtil.mostraAlerta( MSGConsts.NENHUM_USUARIO_ENCONTRADO, MSGConsts.MOD_TITULO ); 
            
        } catch (DAOException ex) {
            Logger.getLogger(ModalidadeFiltroControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void mostrarInativosCBAlterado( UsuarioFiltroGUITO to ) {
        UsuarioCtrlUtil usuarioCUtil = cTO.getUsuarioCUtil();

        try {             
            usuarioCUtil.filtra( to );             
        } catch (DAOException ex) {
            Logger.getLogger(ModalidadeFiltroControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
