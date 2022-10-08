package academia.bd.to;

import java.sql.Timestamp;

public class MedidasTO {
    
    private int id;
    private int matID;
    
    private int peso;
    private double altura;
    
    private int bracoEsquerdo;
    private int antebracoEsquerdo;
    private int bracoDireito;
    private int antebracoDireito;
    private int torax;
    private int cintura;
    private int bumbum;
    private int coxaEsquerda;
    private int coxaDireita;
    private int panturrilhaEsquerda;
    private int panturrilhaDireita;

    private Timestamp dataReg;
    
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

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public int getBracoEsquerdo() {
        return bracoEsquerdo;
    }

    public void setBracoEsquerdo(int bracoEsquerdo) {
        this.bracoEsquerdo = bracoEsquerdo;
    }

    public int getAntebracoEsquerdo() {
        return antebracoEsquerdo;
    }

    public void setAntebracoEsquerdo(int antebracoEsquerdo) {
        this.antebracoEsquerdo = antebracoEsquerdo;
    }

    public int getBracoDireito() {
        return bracoDireito;
    }

    public void setBracoDireito(int bracoDireito) {
        this.bracoDireito = bracoDireito;
    }

    public int getAntebracoDireito() {
        return antebracoDireito;
    }

    public void setAntebracoDireito(int antebracoDireito) {
        this.antebracoDireito = antebracoDireito;
    }

    public int getTorax() {
        return torax;
    }

    public void setTorax(int torax) {
        this.torax = torax;
    }

    public int getCintura() {
        return cintura;
    }

    public void setCintura(int cintura) {
        this.cintura = cintura;
    }

    public int getBumbum() {
        return bumbum;
    }

    public void setBumbum(int bumbum) {
        this.bumbum = bumbum;
    }

    public int getCoxaEsquerda() {
        return coxaEsquerda;
    }

    public void setCoxaEsquerda(int coxaEsquerda) {
        this.coxaEsquerda = coxaEsquerda;
    }

    public int getCoxaDireita() {
        return coxaDireita;
    }

    public void setCoxaDireita(int coxaDireita) {
        this.coxaDireita = coxaDireita;
    }

    public int getPanturrilhaEsquerda() {
        return panturrilhaEsquerda;
    }

    public void setPanturrilhaEsquerda(int panturrilhaEsquerda) {
        this.panturrilhaEsquerda = panturrilhaEsquerda;
    }

    public int getPanturrilhaDireita() {
        return panturrilhaDireita;
    }

    public void setPanturrilhaDireita(int panturrilhaDireita) {
        this.panturrilhaDireita = panturrilhaDireita;
    }

    public Timestamp getDataReg() {
        return dataReg;
    }

    public void setDataReg(Timestamp dataReg) {
        this.dataReg = dataReg;
    }
    
}
