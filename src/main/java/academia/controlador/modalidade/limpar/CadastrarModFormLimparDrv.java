package academia.controlador.modalidade.limpar;

import academia.Consts;
import academia.controlador.ControladorTO;
import academia.gui.modalidade.form.ModFormLimparDriver;

public class CadastrarModFormLimparDrv implements ModFormLimparDriver {
    
    private ControladorTO cTO;
    
    public CadastrarModFormLimparDrv( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public String getDataCriacao() {
        return cTO.getDataUtil().formataDataAtual();
    }

    public String getValorInicial() {
        return Consts.TEXTO_VASIO;
    }
    
}
