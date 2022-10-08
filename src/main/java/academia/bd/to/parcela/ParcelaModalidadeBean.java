package academia.bd.to.parcela;

import academia.bd.bean.ModalidadeBean;

public class ParcelaModalidadeBean {
    
    private ModalidadeBean modalidade;    
    private double totalBruto;
    private double totalLiquido;
    private int quantDias;
    
    public ModalidadeBean getModalidade() {
        return modalidade;
    }

    public void setModalidade(ModalidadeBean modalidade) {
        this.modalidade = modalidade;
    }

    public double getTotalBruto() {
        return totalBruto;
    }

    public void setTotalBruto(double valor) {
        this.totalBruto = valor;
    }

    public double getTotalLiquido() {
        return totalLiquido;
    }

    public void setTotalLiquido(double total) {
        this.totalLiquido = total;
    }

    public int getQuantDias() {
        return quantDias;
    }

    public void setQuantDias(int quantDias) {
        this.quantDias = quantDias;
    }
    
}
