package academia.bd.bean;

import java.sql.Timestamp;

public class DataDiaPagPGQuantBean {
   
    private Timestamp dataDiaPag;
    private int quantPagamentos;

    public Timestamp getDataDiaPag() {
        return dataDiaPag;
    }

    public void setDataDiaPag(Timestamp dataDiaPag) {
        this.dataDiaPag = dataDiaPag;
    }

    public int getQuantPagamentos() {
        return quantPagamentos;
    }

    public void setQuantPagamentos(int quantPagamentos) {
        this.quantPagamentos = quantPagamentos;
    }
    
}
