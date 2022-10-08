package academia.gui.pag.visualizar;

import academia.Consts;
import libs.gui.tabela.TabelaMD;

public class VisualizarParcelaGUITO {
    
    private VisualizarParcelaGUI gui;
    
    public VisualizarParcelaGUITO( VisualizarParcelaGUI gui ) {
        this.gui = gui;
    }
 
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel );
    }
    
    public void limpar() {
        this.setDataParcela( Consts.TEXTO_VASIO );
        this.setSubtotal( Consts.TEXTO_VASIO );
        this.setTotal( Consts.TEXTO_VASIO );
        
        gui.getParcelasTBL().getTMD().removeTodasAsLinhas();
    }        
    
    public TabelaMD getParcelaTBLMD() {
        return gui.getParcelasTBL().getTMD();
    }
    
    public String getDataParcela() {
        return gui.getDadosParcelaPNL().getDataParcelaLV().getText();
    }
             
    public String getSubtotal() {
        return gui.getDadosParcelaPNL().getSubtotalLV().getText();
    }
    
    public String getTotal() {
        return gui.getDadosParcelaPNL().getTotalLV().getText();
    }
    
    public void setDataParcela( String dataParcela ) {
        gui.getDadosParcelaPNL().getDataParcelaLV().setText( dataParcela ); 
    }
    
    public void setSubtotal( String subtotal ) {
        gui.getDadosParcelaPNL().getSubtotalLV().setText( subtotal ); 
    }
    
    public void setTotal( String valor ) {
        gui.getDadosParcelaPNL().getTotalLV().setText( valor ); 
    }        
        
}
