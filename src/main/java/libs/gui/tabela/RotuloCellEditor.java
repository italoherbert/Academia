package libs.gui.tabela;

import java.awt.Component;
import java.awt.Insets;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class RotuloCellEditor extends DefaultCellEditor {
    
    public RotuloCellEditor() {
        super( new JTextField() );
    }

    @Override
    public Component getTableCellEditorComponent(JTable jtable, Object o, boolean bln, int i, int i1) {
        JTextField campo = (JTextField) super.getTableCellEditorComponent(jtable, o, bln, i, i1);
        
        System.out.println( campo );
        if ( campo != null ) {
            campo.setMargin( new Insets( 5, 5, 5, 5 ) );
        }
        
        return campo;
        
    }
    
    
    
}
