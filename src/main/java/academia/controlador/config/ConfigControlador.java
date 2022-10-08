package academia.controlador.config;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.gui.config.ConfigGUIListener;
import academia.gui.config.ConfigGUITO;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigControlador implements ConfigGUIListener {
    
    private ControladorTO cTO;
    
    public ConfigControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void alterarToleranciaPagBTAcionado(ConfigGUITO to) {
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        try { 
            boolean tolerValida = true;
            int toler = 0;
            try {
                toler = Integer.parseInt( to.getToleranciaPag() );
                if ( toler < 0 )
                    tolerValida = false;
            } catch ( NumberFormatException e ) {
                tolerValida = false;
            }
            
            if ( tolerValida ) {
                bd.getConfigDAO().alteraTolerancia( toler );
                
                msgUtil.mostraInfo( MSGConsts.VAL_TOLER_ALTERADO, MSGConsts.CONFIG_TITULO ); 
            } else {
                msgUtil.mostraAlerta( MSGConsts.VAL_TOLER_INVALIDO, MSGConsts.CONFIG_TITULO );
            }
        } catch (DAOException ex) {
            Logger.getLogger(ConfigControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void autoCarregarModsCBAcionado(ConfigGUITO to) {
        BD bd = cTO.getBD();
        
        try { 
            bd.getConfigDAO().alteraAutoCarregarModalidades( to.isSelecAutoCarregarModalidades() );
        } catch (DAOException ex) {
            Logger.getLogger(ConfigControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void autoCarregarUsuariosCBAcionado(ConfigGUITO to) {
        BD bd = cTO.getBD();
        
        try { 
            bd.getConfigDAO().alteraAutoCarregarUsuarios( to.isSelecAutoCarregarUsuarios() );
        } catch (DAOException ex) {
            Logger.getLogger(ConfigControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
