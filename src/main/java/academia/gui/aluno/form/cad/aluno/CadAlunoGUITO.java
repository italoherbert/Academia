package academia.gui.aluno.form.cad.aluno;

import academia.gui.aluno.pnl.AlunoFormGUI;
import academia.gui.aluno.pnl.AlunoFormGUITO;
import academia.gui.aluno.pnl.AlunoFormGUITODriver;
import academia.gui.aluno.pnl.LimparAlunoGUICallback;

public class CadAlunoGUITO extends AlunoFormGUITO {
        
    private CadAlunoGUI gui;
    
    public CadAlunoGUITO( CadAlunoGUI gui ) {
        super( new AlunoFormGUITODriver() {
            public AlunoFormGUI getFormGUI() {
                return gui.getAlunoFormGUI();
            }
        });
        this.gui = gui;
    }
    
    public void limpar( LimparAlunoGUICallback cb ) {
        super.limpar( cb );
        
        this.setInserirPagamento( false ); 
    }
    
    public boolean isInserirPagamento() {
        return gui.getInserirPagamentoCB().isSelected();
    }
    
    public void setInserirPagamento( boolean ins ) {
        gui.getInserirPagamentoCB().setSelected( ins );
    }
    
}
