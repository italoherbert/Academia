package academia.gui.aluno;

import academia.gui.aluno.filtro.AlunoFiltroGUITO;

public class AlunoGUITO {
    
    private AlunoGUI gui;
    
    public AlunoGUITO( AlunoGUI gui ) {
        this.gui = gui;
    }
    
    public AlunoFiltroGUITO getFiltroTO() {
        return gui.getFiltroGUI().getTO();
    }
    
}
