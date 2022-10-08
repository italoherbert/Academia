package academia.controlador;

import academia.bd.BD;
import libs.bd.GBDException;

public class SistemaCtrlUtil {
    
    private ControladorTO cTO;

    public SistemaCtrlUtil(ControladorTO cTO) {
        this.cTO = cTO;
    }
    
    public void finalizar() throws GBDException {
        BD bd = cTO.getBD();        
        bd.finaliza();
    }
        
}
