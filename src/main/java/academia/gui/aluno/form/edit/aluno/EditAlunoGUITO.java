package academia.gui.aluno.form.edit.aluno;

import academia.gui.aluno.pnl.AlunoFormGUI;
import academia.gui.aluno.pnl.AlunoFormGUITO;
import academia.gui.aluno.pnl.AlunoFormGUITODriver;

public class EditAlunoGUITO extends AlunoFormGUITO {
        
    public EditAlunoGUITO( EditAlunoGUI gui ) {
        super( new AlunoFormGUITODriver() {
            public AlunoFormGUI getFormGUI() {
                return gui.getAlunoFormGUI();
            }
        });
    }
            
}
