package academia.controlador.modalidade.limpar;

import academia.Consts;
import academia.controlador.ControladorTO;
import academia.gui.modalidade.form.ModFormLimparDriver;
import java.sql.Timestamp;

public class EditarModFormLimparDrv implements ModFormLimparDriver {
        
    private ControladorTO cTO;
    private Timestamp dataCriacaoTS = null;
    private double valorInicial = 0;

    public EditarModFormLimparDrv( ControladorTO cTO, Timestamp dataCriacaoTS, double valorInicial ) {
        this.cTO = cTO;
        this.dataCriacaoTS = dataCriacaoTS;
        this.valorInicial = valorInicial;
    }

    public String getDataCriacao() {
        if ( dataCriacaoTS == null )
            return Consts.TEXTO_VASIO;

        return cTO.getDataUtil().formataData( dataCriacaoTS );
    }

    public String getValorInicial() {
        return cTO.getNumeroFormatador().formatoFlutuante( valorInicial );
    }
    
}
