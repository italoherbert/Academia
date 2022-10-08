package academia.controlador.pag;

import academia.bd.to.parcela.ParcelasBean;

public class PagSessao {

    private int matID;
    private ParcelasBean parcelas;

    public int getMatID() {
        return matID;
    }

    public void setMatID(int matID) {
        this.matID = matID;
    }        

    public ParcelasBean getParcelas() {
        return parcelas;
    }

    public void setParcelas(ParcelasBean parcelas) {
        this.parcelas = parcelas;
    }
    
}
