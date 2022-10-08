package academia.relatorio.compmed;

import academia.compmed.CompMed;

public class RelatorioCompMedidasBean extends CompMed {

    public final static int QUANT_COLUNAS = 3;        
    
    private String[][] dados = new String[ QUANT_MEDIDAS ][ QUANT_COLUNAS ];
    
    public int getQuantColunas() {
        return QUANT_COLUNAS;
    }
    
    public String[] getLinha( int indice ) {
        return dados[ indice ];
    }
    
    public void setLinha( int indice, String... linha ) {
        dados[ indice ] = linha;
    } 
        
}
