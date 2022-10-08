package academia.gui.aluno.form.cad.modalidade;

import academia.gui.painels.selecionarmods.SelecionarModsPNLTO;
import academia.gui.painels.selecionarmods.SelecionarModsPNL;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTODriver;

public class CadModalidadeGUITO extends SelecionarModsPNLTO {
    
    private CadModalidadeGUITODriver drv;
    
    public CadModalidadeGUITO( CadModalidadeGUITODriver drv ) {
        super(new SelecionarModsPNLTODriver() {
            public SelecionarModsPNL getSelecionarModsPNL() {
                return drv.getFormGUI().getFormPNL();
            }
        } );        
        this.drv = drv;
    }
        
}
