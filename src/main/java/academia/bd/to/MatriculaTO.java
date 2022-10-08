package academia.bd.to;

import java.sql.Timestamp;

public class MatriculaTO {
    
    private int id;
    private int alunoID;
    private Timestamp dataInicio;
    private Timestamp dataFim;
    private Timestamp dataDiaPag;
    
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getAlunoID() {
        return alunoID;
    }

    public void setAlunoID(int alunoID) {
        this.alunoID = alunoID;
    }

    public Timestamp getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Timestamp dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Timestamp getDataDiaPag() {
        return dataDiaPag;
    }

    public void setDataDiaPag( Timestamp dataDiaPag ) {
        this.dataDiaPag = dataDiaPag;
    }

    public Timestamp getDataFim() {
        return dataFim;
    }

    public void setDataFim(Timestamp dataFim) {
        this.dataFim = dataFim;
    }
    
}
