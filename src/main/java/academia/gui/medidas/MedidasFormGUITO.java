package academia.gui.medidas;

import academia.gui.painels.medidas.MedidasFormPNL;
import academia.gui.painels.medidas.MedidasFormPNLTO;
import academia.gui.painels.medidas.MedidasFormPNLTODriver;

public class MedidasFormGUITO extends MedidasFormPNLTO {
            
    private MedidasFormGUI gui;
    
    public MedidasFormGUITO( MedidasFormGUI gui ) {
        super( new MedidasFormPNLTODriver() {
            public MedidasFormPNL getFormPNL() {
                return gui.getFormPNL();
            }
        });
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
    public void setRemoverBTAbilitado( boolean abilitado ) {
        gui.getRemoverBT().setEnabled( abilitado );
    }
    
}
