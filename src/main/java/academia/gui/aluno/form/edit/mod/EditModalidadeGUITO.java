package academia.gui.aluno.form.edit.mod;

import academia.gui.painels.selecionarmods.SelecionarModsPNLTO;
import libs.gui.listaadd.ListaAddMD;

public class EditModalidadeGUITO extends SelecionarModsPNLTO {
    
    private EditModalidadeGUI gui;
    
    public EditModalidadeGUITO( EditModalidadeGUI gui ) {
        super( gui );
        this.gui = gui;
    }
    
    public ListaAddMD getListaAddMD() {
        return gui.getSelecionarModsPNL().getListaAdd().getMD();
    }
    
    public void mostrarSemMatPNL() {
        gui.mostrarPNL( EditModalidadeGUI.SEM_MAT_ATIVA_OP );
    }
    
    public void mostrarMatAtivaPNL() {
        gui.mostrarPNL( EditModalidadeGUI.COM_MAT_ATIVA_OP ); 
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }    
    
}
