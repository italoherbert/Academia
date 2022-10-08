package academia.relatorio.modelo;

import com.itextpdf.text.BaseColor;

public class RelatorioTabelaLinha {
    
    private String[] dados;
    private BaseColor corTexto;
    private BaseColor corFundo;

    public String[] getDados() {
        return dados;
    }

    public void setDados(String[] dados) {
        this.dados = dados;
    }

    public BaseColor getCorTexto() {
        return corTexto;
    }

    public void setCorTexto(BaseColor corTexto) {
        this.corTexto = corTexto;
    }

    public BaseColor getCorFundo() {
        return corFundo;
    }

    public void setCorFundo(BaseColor corFundo) {
        this.corFundo = corFundo;
    }
    
}
