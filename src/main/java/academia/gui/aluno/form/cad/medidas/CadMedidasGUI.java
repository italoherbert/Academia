package academia.gui.aluno.form.cad.medidas;

import academia.gui.painels.medidas.MedidasFormPNL;
import java.awt.FlowLayout;
import javax.swing.JPanel;

public class CadMedidasGUI extends JPanel implements CadMedidasGUITODriver {
    
    private MedidasFormPNL formPNL = new MedidasFormPNL();
        
    private CadMedidasGUITO to = new CadMedidasGUITO( this );
    
    public CadMedidasGUI() {        
        super.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        super.add( formPNL );        
    }

    public CadMedidasGUI getFormGUI() {
        return this;
    }

    public CadMedidasGUITO getTO() {
        return to;
    }
    
    public MedidasFormPNL getFormPNL() {
        return formPNL;
    }
    
}
