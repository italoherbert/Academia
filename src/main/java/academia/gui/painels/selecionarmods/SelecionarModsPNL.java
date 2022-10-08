package academia.gui.painels.selecionarmods;

import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import libs.gui.listaadd.ListaAdd;

public class SelecionarModsPNL extends JPanel {

    private ListaAdd listaAdd; 
    
    public SelecionarModsPNL() {
        this( true );
    }
    
    public SelecionarModsPNL( boolean processarAddRemove ) {
        listaAdd = new ListaAdd( processarAddRemove );
        listaAdd.setBorder( new TitledBorder( "Modalidades (+/-)" ) ); 
        
        super.setLayout( new GridLayout() );
        super.add( listaAdd );
    }
    
    public ListaAdd getListaAdd() {
        return listaAdd;
    }
    
}
