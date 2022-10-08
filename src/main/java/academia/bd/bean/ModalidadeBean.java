package academia.bd.bean;

import academia.bd.to.MensalidadeTO;
import academia.bd.to.ModalidadeTO;
import java.sql.Timestamp;
import java.util.List;

public class ModalidadeBean extends ModalidadeTO {
    
    private int matID;
    private int modID;
    private int matModID;
    private List<MensalidadeTO> mensalidades;
    
    private Timestamp dataContrato;
    private Timestamp dataEncerramento;
    
    public int getMatID() {
        return matID;
    }

    public void setMatID(int matID) {
        this.matID = matID;
    }

    public int getModID() {
        return modID;
    }

    public void setModID(int modID) {
        this.modID = modID;
    }

    public int getMatModID() {
        return matModID;
    }

    public void setMatModID(int matModID) {
        this.matModID = matModID;
    }

    public List<MensalidadeTO> getMensalidades() {
        return mensalidades;
    }

    public void setMensalidades(List<MensalidadeTO> mensalidades) {
        this.mensalidades = mensalidades;
    }

    public Timestamp getDataContrato() {
        return dataContrato;
    }

    public void setDataContrato(Timestamp dataContrato) {
        this.dataContrato = dataContrato;
    }

    public Timestamp getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataEncerramento(Timestamp dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }
    
}
