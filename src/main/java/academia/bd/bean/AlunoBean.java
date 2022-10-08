package academia.bd.bean;

import academia.bd.to.MedidasTO;
import academia.bd.to.AlunoTO;
import java.sql.Timestamp;
import java.util.List;

public class AlunoBean extends AlunoTO {
    
    private Timestamp dataMatricula = null;
    private Timestamp dataDiaPag = null;
    private double desconto = 0;
    private MedidasTO medidas = null;
    private List<Integer> modalidadeIDs = null;

    public Timestamp getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Timestamp dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Timestamp getDataDiaPag() {
        return dataDiaPag;
    }

    public void setDataDiaPag(Timestamp dataDiaPag) {
        this.dataDiaPag = dataDiaPag;
    }
        
    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public MedidasTO getMedidas() {
        return medidas;
    }

    public void setMedidas(MedidasTO medidas) {
        this.medidas = medidas;
    }

    public List<Integer> getModalidadeIDs() {
        return modalidadeIDs;
    }

    public void setModalidadeIDs(List<Integer> ids) {
        this.modalidadeIDs = ids;
    }
           
}
