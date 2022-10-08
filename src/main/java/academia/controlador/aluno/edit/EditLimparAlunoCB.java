package academia.controlador.aluno.edit;

import academia.controlador.ControladorTO;
import java.sql.Timestamp;
import academia.gui.aluno.pnl.LimparAlunoGUICallback;

public class EditLimparAlunoCB implements LimparAlunoGUICallback {

    private ControladorTO cTO;
    private Timestamp dataMatricula;

    public EditLimparAlunoCB( ControladorTO cTO, Timestamp dataMatricula ) {
        this.cTO = cTO;
        this.dataMatricula = dataMatricula;
    }

    public String getDataMatricula() {
        return cTO.getDataUtil().formataData( dataMatricula );
    }

    public String getMedidasDataReg() {
        return cTO.getDataUtil().formataData( dataMatricula ); 
    }

}
