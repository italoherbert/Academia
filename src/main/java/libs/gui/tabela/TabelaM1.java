package libs.gui.tabela;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class TabelaM1 extends JPanel implements TabelaMDDriver {
    
    private TabelaMD tabelaMD = new TabelaMD( this );    
    private JTable tabela;
    
    public TabelaM1() {
        this( false );
    }
    
    public TabelaM1( boolean celulaEditavel ) {       
        if ( celulaEditavel ) {
            tabela = new JTable();
        } else {
            tabela = new JTable() {
                public boolean isCellEditable( int l, int c ) {
                    return celulaEditavel;
                }
            };
        }
        
        tabela.setRowHeight( 22 ); 
        tabela.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );                         
        tabela.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
                
        super.setLayout( new GridLayout() );
        super.add( new JScrollPane( tabela ) );
    }        
                    
    public TabelaMD getTMD() {
        return tabelaMD;
    }
    
    public JTable getJTable() {
        return tabela;
    }

}
