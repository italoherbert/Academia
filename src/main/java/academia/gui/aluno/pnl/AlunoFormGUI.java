package academia.gui.aluno.pnl;

import academia.gui.painels.aluno.AlunoFormPNL;
import java.awt.FlowLayout;
import javax.swing.JPanel;

public class AlunoFormGUI extends JPanel implements AlunoFormGUITODriver {
    
    private AlunoFormPNL formPNL = new AlunoFormPNL();
    
    private AlunoFormGUITO to = new AlunoFormGUITO( this );

    public AlunoFormGUI() {        
        super.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        super.add( formPNL );
    }
    
    public AlunoFormGUI getFormGUI() {
        return this;
    }
    
    public AlunoFormPNL getFormPNL() {
        return formPNL;
    }   
    
    public AlunoFormGUITO getTO() {
        return to;
    }
    
}
