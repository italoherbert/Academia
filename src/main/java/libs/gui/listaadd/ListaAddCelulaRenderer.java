package libs.gui.listaadd;

import java.awt.Color;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ListaAddCelulaRenderer implements ListCellRenderer<String> {

    private Map<Integer, Color> coresMap = new HashMap();
    
    private DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
    
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
       
       JLabel label = (JLabel) defaultRenderer.getListCellRendererComponent( list, value, index, isSelected, cellHasFocus );
       Color cor = coresMap.get( index );
       if ( cor != null )
           label.setBackground( cor );        
       
       return label;
    }

    public Map<Integer, Color> getCoresMap() {
        return coresMap;
    }

    public void setCoresMap(Map<Integer, Color> coresMap) {
        this.coresMap = coresMap;
    }
    
}
