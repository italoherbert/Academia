package academia.gui.aluno.form.edit.medidas;

import libs.gui.tabela.TabelaMD;

public class EditMedidasGUITO {

    private EditMedidasGUI gui;
    
    public EditMedidasGUITO( EditMedidasGUI gui ) {        
        this.gui = gui;
    }
    
    public TabelaMD getMedidasTBLMD() {
        return gui.getMedidasTBL().getTMD();
    }
    
    public void mostrarSemMatPNL() {
        gui.mostrarPNL( EditMedidasGUI.SEM_MAT_ATIVA_OP ); 
    }
    
    public void mostrarMatAtivaPNL() {
        gui.mostrarPNL( EditMedidasGUI.COM_MAT_ATIVA_OP ); 
    }
    
}
