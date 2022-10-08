package academia.gui.aluno.form.cad.aluno;

import academia.gui.aluno.pnl.AlunoFormGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class CadAlunoGUI extends JPanel {
        
    private AlunoFormGUI alunoFormGUI = new AlunoFormGUI();              

    private JCheckBox inserirPagamentoCB;
    
    private CadAlunoGUITO to = new CadAlunoGUITO( this );
    
    public CadAlunoGUI() {        
        inserirPagamentoCB = new JCheckBox( "Inserir pagamento" );

        FlowLayout sulLayout = new FlowLayout( FlowLayout.LEFT );
        sulLayout.setHgap( 20 );
        sulLayout.setVgap( 20 ); 
        
        JPanel sulPNL = new JPanel();
        sulPNL.setLayout( sulLayout );
        sulPNL.add( inserirPagamentoCB );
                
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.CENTER, alunoFormGUI );
        super.add( BorderLayout.SOUTH, sulPNL );                                
    }

    public CadAlunoGUITO getTO() {
        return to;
    }
    
    public AlunoFormGUI getAlunoFormGUI() {
        return alunoFormGUI;
    }    

    public JCheckBox getInserirPagamentoCB() {
        return inserirPagamentoCB;
    }

    public void setInserirPagamentoCB(JCheckBox inserirPagamentoCB) {
        this.inserirPagamentoCB = inserirPagamentoCB;
    }
    
}
