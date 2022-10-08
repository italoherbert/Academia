package academia.gui.aluno.form.cad.medidas;

import academia.gui.painels.medidas.MedidasFormPNLTO;
import academia.gui.painels.medidas.MedidasFormPNL;
import academia.gui.painels.medidas.MedidasFormPNLTODriver;

public class CadMedidasGUITO extends MedidasFormPNLTO {    
    
    private CadMedidasGUITODriver drv;

    public CadMedidasGUITO( CadMedidasGUITODriver drv ) {
        super(new MedidasFormPNLTODriver() {
            public MedidasFormPNL getFormPNL() {
                return drv.getFormGUI().getFormPNL();
            }
        } );
        this.drv = drv;
    }     
        
}
