package academia.relatorio.modelo;

import com.itextpdf.text.BaseColor;

public class RelatorioTabelaColunaM1 {
    
    private String rotulo;
    private int largura;
    private BaseColor valorCor;

    public String getRotulo() {
        return rotulo;
    }

    public void setRotulo(String rotulo) {
        this.rotulo = rotulo;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public BaseColor getValorCor() {
        return valorCor;
    }

    public void setValorCor(BaseColor valorCor) {
        this.valorCor = valorCor;
    }
    
}
