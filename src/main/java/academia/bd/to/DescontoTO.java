package academia.bd.to;

import java.sql.Timestamp;

public class DescontoTO {
    
    private int id;
    private int matID;
    private double porcentagem;
    private Timestamp dataAlter;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getMatID() {
        return matID;
    }

    public void setMatID(int matID) {
        this.matID = matID;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(double porcentagem) {
        this.porcentagem = porcentagem;
    }

    public Timestamp getDataAlter() {
        return dataAlter;
    }

    public void setDataAlter(Timestamp dataAlter) {
        this.dataAlter = dataAlter;
    }
    
}
