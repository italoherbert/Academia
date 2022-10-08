package academia.controlador.aluno.cad;

import academia.controlador.ControladorTO;
import academia.gui.aluno.pnl.LimparAlunoGUICallback;

public class CadLimparAlunoGUICB implements LimparAlunoGUICallback {
    
    private ControladorTO cTO;

    public CadLimparAlunoGUICB( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public String getDataMatricula() {
        return cTO.getDataUtil().formataDataAtual();
    }

    public String getMedidasDataReg() {
        return cTO.getDataUtil().formataDataAtual();
    }
        
}
