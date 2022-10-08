package academia.relatorio;

import academia.util.DataUtil;
import academia.util.NumeroFormatador;

public interface RelatorioTO {
    
    public RelConfig getConfig();
    
    public NumeroFormatador getNumeroFormatador();
    
    public DataUtil getDataUtil();
    
}
