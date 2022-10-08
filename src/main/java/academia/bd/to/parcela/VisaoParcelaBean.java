package academia.bd.to.parcela;

import academia.bd.to.PagamentoTO;
import java.sql.Timestamp;

public class VisaoParcelaBean {
    
    private int pagsTBLID;
    private Timestamp dataParcela;
    private Timestamp dataVencimento;
    private double descontoAluno;
    private double descontoPag;
    private boolean pendente;
    
    private PagamentoTO pagamento;
    private ParcelaBean parcela;
    
    public VisaoParcelaBean( ParcelaBean parcela ) {
        this.parcela = parcela;
    }
        
    public ParcelaBean getCParcela() {
        return parcela;
    }

    public int getPagsTBLID() {
        return pagsTBLID;
    }

    public void setPagsTBLID(int id) {
        this.pagsTBLID = id;
    }   

    public PagamentoTO getPagamento() {
        return pagamento;
    }

    public Timestamp getDataParcela() {
        return dataParcela;
    }

    public void setDataParcela(Timestamp dataParcela) {
        this.dataParcela = dataParcela;
    }

    public void setPagamento(PagamentoTO pagamento) {
        this.pagamento = pagamento;
    }
    
    public Timestamp getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Timestamp dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public boolean isPendente() {
        return pendente;
    }

    public void setPendente(boolean pendente) {
        this.pendente = pendente;
    }

    public double getDescontoAluno() {
        return descontoAluno;
    }

    public void setDescontoAluno(double desconto) {
        this.descontoAluno = desconto;
    }

    public double getDescontoPag() {
        return descontoPag;
    }

    public void setDescontoPag(double descontoPag) {
        this.descontoPag = descontoPag;
    }

}
