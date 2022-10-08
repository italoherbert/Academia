package libs.gui.listaadd;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ListaAdd extends JPanel implements ActionListener {

    private ListaAddPNL pnl = new ListaAddPNL();
    private ListaAddMD listaAddMD = new ListaAddMD( this );
    
    private ListaAddListener listener;

    private boolean addRemFunc = true;
    
    public ListaAdd() {
        this( true );
    }
    
    public ListaAdd( boolean addRemFunc ) {
        this.addRemFunc = addRemFunc;
        
        super.setLayout( new GridLayout() ); 
        super.add( pnl );
        
        pnl.getAddBT().addActionListener( this );
        pnl.getRemoveBT().addActionListener( this );         
    }
    
    public void actionPerformed( ActionEvent e ) {        
        if ( e.getSource() == pnl.getAddBT() ) {
            if ( addRemFunc )
                listaAddMD.addItensSelecionados();            
            if ( listener != null )
                listener.addBTAcionado( listaAddMD );
        } else if ( e.getSource() == pnl.getRemoveBT() ) {
            if ( addRemFunc )
                listaAddMD.removeItensSelecionados();            
            if ( listener != null )
                listener.removeBTAcionado( listaAddMD ); 
        }        
    }
    
    public void setListaAddListener( ListaAddListener listener ) {
        this.listener = listener;
    }

    public ListaAddPNL getListaAddPNL() {
        return pnl;
    }

    public ListaAddMD getMD() {
        return listaAddMD;
    }

    public boolean isAddRemFunc() {
        return addRemFunc;
    }

    public void setAddRemFunc(boolean addRemFunc) {
        this.addRemFunc = addRemFunc;
    }
    
}
