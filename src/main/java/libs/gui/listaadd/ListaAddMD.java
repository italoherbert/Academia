package libs.gui.listaadd;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListaAddMD {
 
    private ListaAdd listaAdd;
    private String[] itens1 = new String[0];
    private String[] itens2 = new String[0];     
        
    public ListaAddMD( ListaAdd listaAdd ) {
        this.listaAdd = listaAdd;
    }
    
    public void pintaLista( Map<Integer, Color> cores ) {
        ListaAddCelulaRenderer renderer = new ListaAddCelulaRenderer();
        renderer.setCoresMap( cores );

        listaAdd.getListaAddPNL().getItens1LST().setCellRenderer( renderer );         
    }
    
    public void addItensSelecionados() {        
        List<String> itens2LSSel = this.getItens2Selecionados();
        String[] itens1VS = this.getItens1();
        String[] itens2VS = this.getItens2();

        List<String> itens1 = new ArrayList();
        List<String> itens2 = new ArrayList();

        for( String mod : itens1VS )
            itens1.add( mod );            
        for( String mod : itens2LSSel )
            itens1.add( mod );

        for ( String mod : itens2VS )
            if ( !itens2LSSel.contains( mod ) )
                itens2.add( mod ); 

        this.setItensLista1( itens1 );
        this.setItensLista2( itens2 ); 
    }
    
    public void removeItensSelecionados() {
        List<String> itens1LSSel = this.getItens1Selecionados();
        String[] itens1VS = this.getItens1();
        String[] itens2VS = this.getItens2();

        List<String> itens1 = new ArrayList();
        List<String> itens2 = new ArrayList();

        for( String mod : itens2VS )
            itens2.add( mod );
        for( String mod : itens1LSSel )
            itens2.add( mod );

        for( String mod : itens1VS )
            if ( !itens1LSSel.contains( mod ) )
                itens1.add( mod );

        this.setItensLista1( itens1 );
        this.setItensLista2( itens2 );         
    }
    
    public void limpar() {
        itens1 = new String[0];
        itens2 = new String[0];
        
        listaAdd.getListaAddPNL().getItens1LST().setListData( itens1 );
        listaAdd.getListaAddPNL().getItens2LST().setListData( itens2 ); 
    }
        
    public void setItensLista1( List<String> itens ) {
        itens1 = new String[ itens.size() ];        
        itens.toArray( itens1 );
                
        listaAdd.getListaAddPNL().getItens1LST().setListData( itens1 );
    }
    
    public void setItensLista2( List<String> itens ) {
        itens2 = new String[ itens.size() ];        
        itens.toArray( itens2 );
        
        listaAdd.getListaAddPNL().getItens2LST().setListData( itens2 );
    }
    
    public void setItens1( String[] itens ) {
        itens1 = itens;        
        listaAdd.getListaAddPNL().getItens1LST().setListData( itens1 ); 
    }
    
    public void setItens2( String[] itens ) {
        itens2 = itens;        
        listaAdd.getListaAddPNL().getItens1LST().setListData( itens2 ); 
    }
    
    public String[] getItens1() {
        return itens1;
    }
    
    public String[] getItens2() {
        return itens2;
    }
    
    public List<String> getItens1Selecionados() {
        return listaAdd.getListaAddPNL().getItens1LST().getSelectedValuesList();
    }
    
    public List<String> getItens2Selecionados() {
        return listaAdd.getListaAddPNL().getItens2LST().getSelectedValuesList();
    }
        
}
