package libs.gui.tabela;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TabelaCelulaRenderer extends DefaultTableCellRenderer {
    
    private Map<Integer, Color> cores = new HashMap();        
    
    public Component getTableCellRendererComponent( 
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col ) {
        
        Component c = super.getTableCellRendererComponent( table, value, isSelected, hasFocus, row, col );

        if ( !isSelected ) {        
            c.setBackground( Color.WHITE ); 
            c.setForeground( Color.BLACK ); 

            Set<Integer> linhas = cores.keySet();
            for( int linha : linhas ) {            
                if ( row == linha ) {
                    Color cor = cores.get( linha );
                    c.setBackground( cor ); 
                }
            }
        }
        
        return c;
    }

    public Map<Integer, Color> getCoresMap() {
        return cores;
    }

    public void setCoresMap(Map<Integer, Color> cores) {
        this.cores = cores;
    }        
    
}
