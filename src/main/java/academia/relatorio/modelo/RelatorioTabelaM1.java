package academia.relatorio.modelo;

import static academia.relatorio.modelo.RelatorioM1.ESTILO_FONTE;
import static academia.relatorio.modelo.RelatorioM1.FAMILIA_FONTE;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.util.ArrayList;
import java.util.List;

public class RelatorioTabelaM1 {
    
    public final static BaseColor CABECALHO_COR = new BaseColor( 240, 240, 240 ); 
    public final static BaseColor CABECALHO_COR_FUNDO = BaseColor.DARK_GRAY;
    
    public final static BaseColor VALOR_COR = BaseColor.BLACK;
    
    public final static float VALOR_FUNDO_CINZA_ESCALA1 = 0.9f;
    public final static float VALOR_FUNDO_CINZA_ESCALA2 = 0.98f;
    
    public final static int CABECALHO_FONTE_TAM = 14;
    public final static int VALOR_FONTE_TAM = 14;          
    
    private List<RelatorioTabelaColunaM1> colunas = new ArrayList();
    private List<RelatorioTabelaLinha> linhas = new ArrayList();
        
    public PdfPTable construirTabela() throws DocumentException {
        int quantColunas = colunas.size();
        
        Font cabecalhoFonte = this.criaFont(VALOR_FONTE_TAM );        
        cabecalhoFonte.setColor(CABECALHO_COR ); 
        
        PdfPTable tabela = new PdfPTable( quantColunas );
        tabela.setHeaderRows( 1 );
        
        int[] larguras = new int[ quantColunas ];
        int i = 0;
        for( RelatorioTabelaColunaM1 col : colunas ) {
            String rotulo = col.getRotulo();
            
            Anchor a = new Anchor( rotulo, cabecalhoFonte );
            PdfPCell c = new PdfPCell( a ); 
            c.setPaddingBottom( 8 ); 
            
            c.setBackgroundColor( CABECALHO_COR_FUNDO ); 
            c.setBorderWidth( 0 );                
            c.setHorizontalAlignment( Element.ALIGN_LEFT );
            
            tabela.addCell( c );
            
            larguras[ i ] = col.getLargura();
            
            i++;
        }                              
        tabela.setWidths( larguras );                               
        
        int tam = linhas.size();
        for ( i = 0; i < tam; i++ ) {
            RelatorioTabelaLinha linha = linhas.get( i );
            String[] dadosLinha = linha.getDados();
            for ( int j = 0; j < dadosLinha.length; j++ ) {                
                BaseColor colunaValoresCor = colunas.get( j ).getValorCor();                
                
                Font fonte = this.criaFont( VALOR_FONTE_TAM );               
                if ( linha.getCorTexto() == null )
                    fonte.setColor( colunaValoresCor );
                else fonte.setColor( linha.getCorTexto() ); 
                        
                Anchor a = new Anchor( dadosLinha[ j ], fonte );
                
                PdfPCell c = new PdfPCell( a );
                c.setBorderWidth( 0 );
                c.setPadding( 3 ); 
            
                if ( linha.getCorFundo() == null ) {                
                    if ( i % 2 == 1 ) 
                        c.setGrayFill( VALOR_FUNDO_CINZA_ESCALA1 );                
                    else c.setGrayFill( VALOR_FUNDO_CINZA_ESCALA2 ); 
                } else {
                    c.setBackgroundColor( linha.getCorFundo() ); 
                }
                tabela.addCell( c );
            }
        }        
        
        return tabela;
    }
    
    public RelatorioTabelaColunaM1 addColuna( String rotulo, int largura ) {
        RelatorioTabelaColunaM1 col = new RelatorioTabelaColunaM1();
        col.setRotulo(rotulo );
        col.setLargura( largura );
        col.setValorCor( VALOR_COR ); 
        
        colunas.add( col );        
        
        return col;
    }
    
    public RelatorioTabelaColunaM1 addColuna( String rotulo, int largura, BaseColor valoresCor ) {
        RelatorioTabelaColunaM1 col = this.addColuna(rotulo, largura ); 
        col.setValorCor( valoresCor ); 
        
        return col;
    }
    
    public void setCorTexto( int indice, BaseColor corTexto ) {
        if ( indice < linhas.size() ) {
            RelatorioTabelaLinha linha = linhas.get( indice );
            linha.setCorTexto( corTexto );
        } else {
            throw new RuntimeException( "Indice de linha maior que limite" );
        }
    }
    
    public void setCorFundo( int indice, BaseColor corFundo ) {
        if ( indice < linhas.size() ) {
            RelatorioTabelaLinha linha = linhas.get( indice );
            linha.setCorFundo( corFundo);
        } else {
            throw new RuntimeException( "Indice de linha maior que limite" );
        }
    }
     
    public RelatorioTabelaLinha getLinha( int indice ) {
        return linhas.get( indice );
    }
    
    public void addLinha( String... ln ) {
        RelatorioTabelaLinha linha = new RelatorioTabelaLinha();
        linha.setDados( ln ); 
        linhas.add( linha );
    }
    
    private Font criaFont( int tam ) {
        return new Font( FAMILIA_FONTE, tam, ESTILO_FONTE );
    }    
    
}
