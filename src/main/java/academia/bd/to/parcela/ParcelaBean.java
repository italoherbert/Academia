package academia.bd.to.parcela;

import java.util.List;

public class ParcelaBean {
    
    private double valorTotal;
    private double valorSubtotalSemDescontoPag = 0;
    private List<ParcelaModalidadeBean> modalidades;
    
    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public double getValorSubtotalSemDescontoPag() {
        return valorSubtotalSemDescontoPag;
    }

    public void setValorSubtotalSemDescontoPag(double subtotal) {
        this.valorSubtotalSemDescontoPag = subtotal;
    }
    
    public List<ParcelaModalidadeBean> getModalidades() {
        return modalidades;
    }

    public void setModalidades(List<ParcelaModalidadeBean> modalidades) {
        this.modalidades = modalidades;
    }
            
}
