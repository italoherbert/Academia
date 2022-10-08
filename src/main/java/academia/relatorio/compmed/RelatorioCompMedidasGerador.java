package academia.relatorio.compmed;

import academia.relatorio.RelatorioTO;
import academia.relatorio.modelo.RelatorioM1;
import academia.relatorio.modelo.RelatorioTabelaM1;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;

public class RelatorioCompMedidasGerador {
    
    public final static String TITULO = "Relatório de comparação de medidas";
    public final static String[] TABELA_COLUNAS = {
        "", "Anterior", "Nova", "Diferença"
    };            
    public final static int[] TABELA_COLUNAS_LARGURAS = {
        150, 80, 80, 80
    };
       
    private RelatorioTO rTO;
    private RelatorioM1 modelo = new RelatorioM1();

    public RelatorioCompMedidasGerador(RelatorioTO rTO) {
        this.rTO = rTO;
    }
    
    public void geraRelatorio( OutputStream arqOut, RelatorioCompMedidasBean compmed ) throws DocumentException {        
        Document doc = new Document();        
        PdfWriter.getInstance( doc, arqOut );        
        doc.open();

        Paragraph tituloP = modelo.criaTitulo( TITULO );
        
        RelatorioTabelaM1 tabelaModelo = new RelatorioTabelaM1();
        int tam = TABELA_COLUNAS.length;
        for( int i = 0; i < tam; i++ ) {
            String rotulo = TABELA_COLUNAS[ i ];
            int largura = TABELA_COLUNAS_LARGURAS[ i ];

            if ( i == 0 ) {                                        
                tabelaModelo.addColuna( rotulo, largura );
            } else {
                tabelaModelo.addColuna( rotulo, largura, BaseColor.BLUE );
            }                       
        }
                
        tam = compmed.getQuantMedidas();
        for( int i = 0; i < tam; i++ ) {
            String rotulo = compmed.getTipoMedida( i );
            String[] linha = compmed.getLinha( i );
            String unidade = compmed.getUnidadeMedida( i );
                                    
            int quantCols = compmed.getQuantColunas();            
            String[] ln = new String[ quantCols + 1 ];
            ln[ 0 ] = rotulo;
            
            for( int j = 0; j < quantCols; j++ )
                ln[ j + 1 ] = linha[ j ] + " " + unidade;
            
            tabelaModelo.addLinha( ln );             
        }
                
        PdfPTable tabela = tabelaModelo.construirTabela();        
        
        doc.add( tituloP );
        doc.add( new Paragraph( " " ) );
        doc.add( new Paragraph( " " ) );
        doc.add( tabela );
        
        doc.close();
    }
    
}
