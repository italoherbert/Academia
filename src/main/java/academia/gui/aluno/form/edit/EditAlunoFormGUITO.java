package academia.gui.aluno.form.edit;

import academia.gui.aluno.form.edit.aluno.EditAlunoGUITO;
import academia.gui.aluno.form.edit.medidas.EditMedidasGUITO;
import academia.gui.aluno.form.edit.mod.EditModalidadeGUITO;
import academia.gui.painels.aluno.AlunoFormPNLTO;
import academia.gui.painels.aluno.AlunoFormPNL;
import academia.gui.painels.aluno.AlunoFormPNLTODriver;

public class EditAlunoFormGUITO extends AlunoFormPNLTO {
    
    private EditAlunoFormGUI gui;    
    
    public EditAlunoFormGUITO( EditAlunoFormGUI gui )  {
        super(new AlunoFormPNLTODriver() {
            public AlunoFormPNL getFormPNL() {
                return gui.getEditAlunoGUI().getAlunoFormGUI().getFormPNL();
            }
        });
        this.gui = gui;
    }
    
    public EditModalidadeGUITO getModsTO() {
        return gui.getEditModGUI().getTO();
    }
    
    public EditMedidasGUITO getMedidasTO() {
        return gui.getEditMedsGUI().getTO();
    }
    
    public EditAlunoGUITO getAlunoTO() {
        return gui.getEditAlunoGUI().getTO();
    }
         
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
}
