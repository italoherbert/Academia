package academia.gui.aluno.form.cad.modalidade;

import academia.gui.painels.selecionarmods.SelecionarModsPNL;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTO;
import java.awt.FlowLayout;
import javax.swing.JPanel;

public class CadModalidadeGUI extends JPanel 
        implements CadModalidadeGUITODriver {
    
    private SelecionarModsPNL editModPNL = new SelecionarModsPNL();
        
    private CadModalidadeGUITO to = new CadModalidadeGUITO( this );
    
    public CadModalidadeGUI() {        
        super.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        super.add( editModPNL );        
    }    
    
    public SelecionarModsPNLTO getSelecionarModsPNLTO() {
        return to;
    }

    public CadModalidadeGUITO getTO() {
        return to;
    }

    public CadModalidadeGUI getFormGUI() {
        return this;
    }
    
    public SelecionarModsPNL getFormPNL() {
        return editModPNL;
    }
    
}
