package academia.gui.medidas.comparar;

import academia.compmed.CompMed;
import libs.gui.tabela.TabelaMD;

public class CompararMedidasGUITO extends CompMed {
    
    private CompararMedidasGUI gui;
    
    public CompararMedidasGUITO( CompararMedidasGUI gui ) {
        this.gui = gui;        
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
        
    public TabelaMD getMedidasTBLMD() {
        return gui.getMedidasTBL().getTMD();
    }

    public String[] getLinha(int indice) {
        TabelaMD t = gui.getMedidasTBL().getTMD();
        return new String[] {
            t.getCelulaValor( indice, 1 ),
            t.getCelulaValor( indice, 2 ),
            t.getCelulaValor( indice, 3 ) 
        };
    }
    
    public void setLinha( int indice, String... linha ) {        
        TabelaMD t = gui.getMedidasTBL().getTMD();
        t.setCelulaValor(indice, 1, linha[0] );
        t.setCelulaValor(indice, 2, linha[1] );
        t.setCelulaValor(indice, 3, linha[2] );
    }
    
}
