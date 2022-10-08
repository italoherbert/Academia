package libs.gui.tabela;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TabelaMD implements TabelaMDConsts {

    private TabelaMDDriver driver;
    private List<Integer> colLarguras = new ArrayList();

    private TabelaCelulaRenderer renderer = new TabelaCelulaRenderer();
    
    public TabelaMD( TabelaMDDriver driver ) {
        this.driver = driver;        
    }   
    
    public void pintaTabela( Map<Integer, Color> cores ) {
        renderer.setCoresMap( cores );

        JTable tabela = driver.getJTable();
        
        int quantCols = tabela.getColumnCount();
        for( int i = 0; i < quantCols; i++ )
            tabela.getColumnModel().getColumn( i ).setCellRenderer( renderer );                                 
    }
    
    public void addLinha( String[] dados ) {
        JTable tabela = driver.getJTable();
        DefaultTableModel model = (DefaultTableModel)tabela.getModel();
        model.addRow( dados );
    }   
            
    public void addColuna( String col, int largura ) {
        JTable tabela = driver.getJTable();
        DefaultTableModel model = (DefaultTableModel)tabela.getModel();
        model.addColumn( col );
        
        colLarguras.add( largura );
    }
    
    public void redimensionaColunas() {
        JTable tabela = driver.getJTable();
        int quantCols = tabela.getColumnCount();
        
        for( int i = 0; i < quantCols; i++ ) {
            int largura = colLarguras.get( i );
            tabela.getColumnModel().getColumn( i ).setPreferredWidth( largura );
        }
    }
        
    public int getQuantLinhas() {
        return driver.getJTable().getRowCount();
    }      
    
    public int getQuantColunas() {
        return driver.getJTable().getColumnCount();
    }
    
    public void removeLinha( int indice ) {
        DefaultTableModel model = (DefaultTableModel)driver.getJTable().getModel();        
        model.removeRow( indice );
    }
    
    public void removeTodasAsLinhas() {
        JTable tabela = driver.getJTable();        
        
        DefaultTableModel model = (DefaultTableModel)tabela.getModel();        
        while( tabela.getRowCount() > 0 )
            model.removeRow( 0 );            
    }
    
    public String[] getLinhaValores( int linha ) {
        JTable tabela = driver.getJTable();
        DefaultTableModel model = (DefaultTableModel)tabela.getModel();
        
        String[] dados = null; 
        
        if( tabela.getSelectedRowCount() == 1 ) {
            dados = new String[ tabela.getColumnCount() ];
            for( int j = 0; j < tabela.getColumnCount(); j++ )                            
                dados[ j ] = String.valueOf( model.getValueAt( linha, j ) );
        }
            
        return dados;
    } 
    
    public String[] getLinhaSelecionada() {
        int indice = driver.getJTable().getSelectedRow();       
        return this.getLinhaValores( indice );
    }
        
    public String[][] getLinhasSelecionadas() {
        int[] indices = driver.getJTable().getSelectedRows();        
        int quantColunas = driver.getJTable().getColumnCount();
        
        String[][] linhas = new String[ indices.length ][ quantColunas ];
        for( int i = 0; i < indices.length; i++ ) {
            int indice = indices[ i ];
            for( int j = 0; j < quantColunas; j++ ) {
                linhas[ i ][ j ] = driver.getJTable().getValueAt( indice, j ).toString();
            }
        }
        
        return linhas;
    }
    
    public int getQuantLinhasSelecionadas() {
        return driver.getJTable().getSelectedRowCount();
    }        
    
    public int getIndiceLinhaSelecionada() {
        return driver.getJTable().getSelectedRow();
    }
    
    public int[] getIndicesLinhasSelecionadas() {
        return driver.getJTable().getSelectedRows();
    }
    
    public void selecionaLinha( int i1, int i2 ) {
        driver.getJTable().setRowSelectionInterval( i1, i2 ); 
    }
    
    public String getCelulaValor( int l, int c ) {
        DefaultTableModel model = (DefaultTableModel)driver.getJTable().getModel();        
        return String.valueOf( model.getValueAt( l, c ) );
    }
    
    public void setCelulaValor( int l, int c, String valor ) {
        DefaultTableModel model = (DefaultTableModel)driver.getJTable().getModel();        
        model.setValueAt( valor, l, c );
    }
    
    public TabelaCelulaRenderer getRenderer() {
        return renderer;
    }
        
}
