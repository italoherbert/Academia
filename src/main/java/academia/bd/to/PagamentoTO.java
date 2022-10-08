package academia.bd.to;

import java.sql.Timestamp;

public class PagamentoTO {
    
    private int id;
    private int matID;
    private int usuarioID;
    private double valor;
    private double desconto;
    private Timestamp dataPag;

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

    public int getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(int usuarioID) {
        this.usuarioID = usuarioID;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public Timestamp getDataPag() {
        return dataPag;
    }

    public void setDataPag(Timestamp dataPag) {
        this.dataPag = dataPag;
    }
    
}
