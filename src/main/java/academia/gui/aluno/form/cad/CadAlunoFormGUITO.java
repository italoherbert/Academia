package academia.gui.aluno.form.cad;

import academia.gui.aluno.form.cad.aluno.CadAlunoGUITO;
import academia.gui.aluno.pnl.AlunoFormGUITO;
import academia.gui.aluno.form.cad.medidas.CadMedidasGUITO;
import academia.gui.aluno.form.cad.modalidade.CadModalidadeGUITO;
import academia.gui.aluno.pnl.LimparAlunoGUICallback;

public class CadAlunoFormGUITO {

    private CadAlunoFormGUI gui;
    
    public CadAlunoFormGUITO( CadAlunoFormGUI gui ) {        
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
    public void limpar( LimparAlunoGUICallback lprCB ) {
        AlunoFormGUITO alunoTO = gui.getCadAlunoGUI().getTO();
        CadMedidasGUITO medidasTO = gui.getCadMedidasGUI().getTO();
        CadModalidadeGUITO modsTO = gui.getCadModsGUI().getTO();
        
        alunoTO.limpar( lprCB );
        medidasTO.limpar( lprCB );
        modsTO.limpar();
        
        gui.getOpcoesGUICtrl().para( CadAlunoFormGUI.ALUNO_FORM_CARD ); 
    }
    
    public CadAlunoGUITO getAlunoTO() {
        return gui.getCadAlunoGUI().getTO();
    }

    public CadModalidadeGUITO getModalidadesTO() {
        return gui.getCadModsGUI().getTO();
    }

    public CadMedidasGUITO getMedidasTO() {
        return gui.getCadMedidasGUI().getTO();
    }        
    
    
    public boolean isPularMedidas() {
        return gui.isPularMedidas();
    }    
    
}
