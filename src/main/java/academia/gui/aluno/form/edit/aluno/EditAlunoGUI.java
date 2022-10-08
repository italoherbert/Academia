package academia.gui.aluno.form.edit.aluno;

import academia.gui.aluno.pnl.AlunoFormGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class EditAlunoGUI extends JPanel {
    
    private AlunoFormGUI alunoFormGUI = new AlunoFormGUI();

    private JButton salvarBT;
    private JButton limparBT;            
    
    private EditAlunoGUITO to = new EditAlunoGUITO( this );
    
    public EditAlunoGUI() {        
        salvarBT = new JButton( "Salvar" );
        limparBT = new JButton( "Limpar" );                
        
        JPanel alunoBTsPNL = new JPanel();
        alunoBTsPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) );        
        alunoBTsPNL.add( salvarBT );
        alunoBTsPNL.add( limparBT );
        
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.CENTER, alunoFormGUI );
        super.add( BorderLayout.SOUTH, alunoBTsPNL );                                
    }

    public EditAlunoGUITO getTO() {
        return to;
    }
    
    public AlunoFormGUI getAlunoFormGUI() {
        return alunoFormGUI;
    }

    public JButton getSalvarBT() {
        return salvarBT;
    }

    public JButton getLimparBT() {
        return limparBT;
    }    
    
}
