package academia.relatorio.dodia;

import java.sql.Timestamp;

public class RelatorioDiaTO {

    private String nomeUsuario;
    private String nomeAluno;
    private double valor;
    private Timestamp dataPag;

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Timestamp getDataPag() {
        return dataPag;
    }

    public void setDataPag(Timestamp dataPag) {
        this.dataPag = dataPag;
    }
    
}
