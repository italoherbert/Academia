package academia.controlador.mensalidade.limpar;

import academia.controlador.ControladorTO;
import academia.gui.mensalidade.novovalor.NovoValorMensLimparForm;

public class NovoValMensLprFormDrv implements NovoValorMensLimparForm {
    
    private ControladorTO cTO;
    
    public NovoValMensLprFormDrv( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public String getDataRegistro() {
        return cTO.getDataUtil().formataDataAtual();
    }
            
}
