package academia.gui.aluno.pnl;

import academia.gui.painels.aluno.AlunoFormPNLTO;
import academia.gui.painels.aluno.AlunoFormPNL;
import academia.gui.painels.aluno.AlunoFormPNLTODriver;

public class AlunoFormGUITO extends AlunoFormPNLTO {
    
    private AlunoFormGUITODriver drv;
    
    public AlunoFormGUITO( AlunoFormGUITODriver drv ) {
        super(new AlunoFormPNLTODriver() {
            public AlunoFormPNL getFormPNL() {
                return drv.getFormGUI().getFormPNL();
            }
        } );
        this.drv = drv;
    }       
         
}
