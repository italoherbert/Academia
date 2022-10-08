package academia.bd.to;

import java.sql.Timestamp;

public class MensalidadeTO {
    
    private int id;
    private int modID;
    private double valor;
    private Timestamp dataAlter;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getModID() {
        return modID;
    }

    public void setModID(int modID) {
        this.modID = modID;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Timestamp getDataAlter() {
        return dataAlter;
    }

    public void setDataAlter(Timestamp dataAlter) {
        this.dataAlter = dataAlter;
    }

    
}
