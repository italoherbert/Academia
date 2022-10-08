package academia.bd.to.parcela;

import java.sql.Timestamp;
import java.util.List;

public class ParcelasBean {
    
    private String nomeAluno;
    private double valorProxParcela;
    private double valorTotal;
    private int quantParcelas;
    private int quantPagamentos;
    private Timestamp pagoAte;
    private Timestamp dataDiaPag;
    private List<VisaoParcelaBean> parcelas = null;    
    
    private ParcelasMD md = new ParcelasMD( this ); 
    
    public ParcelasMD getMD() {
        return md;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }
    
    public List<VisaoParcelaBean> getVisaoParcelas() {
        return parcelas;
    }

    public void setVisaoParcelas(List<VisaoParcelaBean> parcelas) {
        this.parcelas = parcelas;
    }

    public double getValorProxParcela() {
        return valorProxParcela;
    }

    public void setValorProxParcela(double valorProxParcela) {
        this.valorProxParcela = valorProxParcela;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double total) {
        this.valorTotal = total;
    }

    public int getQuantParcelas() {
        return quantParcelas;
    }

    public void setQuantParcelas(int quantParcelas) {
        this.quantParcelas = quantParcelas;
    }

    public int getQuantPagamentos() {
        return quantPagamentos;
    }

    public void setQuantPagamentos(int quantPagamentos) {
        this.quantPagamentos = quantPagamentos;
    }

    public Timestamp getPagoAte() {
        return pagoAte;
    }

    public void setPagoAte(Timestamp pagoAte) {
        this.pagoAte = pagoAte;
    }

    public Timestamp getDataDiaPag() {
        return dataDiaPag;
    }

    public void setDataDiaPag(Timestamp dataDiaPag) {
        this.dataDiaPag = dataDiaPag;
    }

}
