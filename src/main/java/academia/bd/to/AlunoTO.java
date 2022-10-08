package academia.bd.to;

import java.sql.Timestamp;

public class AlunoTO {
    
    private int id;    
    private int matriculaCorrente;
    private Timestamp dataNasc;
    private String nome;
    private String obs;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getMatriculaCorrente() {
        return matriculaCorrente;
    }

    public void setMatriculaCorrente(int matriculaCorrente) {
        this.matriculaCorrente = matriculaCorrente;
    }

    public Timestamp getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Timestamp dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }
    
}
