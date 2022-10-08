package academia.relatorio.dodia;

import academia.relatorio.RelatorioTO;
import academia.relatorio.modelo.RelatorioM1;
import academia.relatorio.modelo.RelatorioTabelaM1;
import academia.util.DataUtil;
import academia.util.NumeroFormatador;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.List;

public class RelatorioDiaGerador {
       
    public final static String TITULO = "Relatório do dia";
    
    public final static String[] TABELA_COLUNAS = {
        "Funcionário", "Aluno", "Valor pago", "Hora"
    };
    public final static int[] TABELA_COLUNAS_LARGURAS = {
        60, 90, 40, 30
    };
    
    public final static int VALOR_PAGO_INDICE = 2;
    public final static BaseColor VALOR_PAGO_COR = BaseColor.RED;
    
    public final static String CAMPO_VAL_REC = "Valor total recebido (R$): ";
    public final static String CAMPO_DATA = "Data: ";            
    
    private RelatorioTO rTO;
    private RelatorioM1 modelo = new RelatorioM1();
    
    public RelatorioDiaGerador( RelatorioTO rTO ) {
        this.rTO = rTO;
    }
    
    public void geraRelatorio( OutputStream arqOut, RelatorioDiaBean rpag ) throws DocumentException {
        NumeroFormatador nf = rTO.getNumeroFormatador();
        DataUtil df = rTO.getDataUtil();
        
        double valorRecebido = 0;
        Timestamp dataDia = rpag.getDataDia();
        List<RelatorioDiaTO> pags = rpag.getPagamentos();
                
        for( RelatorioDiaTO pag : pags )
            valorRecebido += pag.getValor();    
        
        
        Document doc = new Document();        
        PdfWriter.getInstance( doc, arqOut );        
        doc.open();
        
        Paragraph tituloP = modelo.criaTitulo( TITULO );
                
        String campo = CAMPO_VAL_REC;
        String valor = nf.formatoReal( valorRecebido );
        BaseColor cor = BaseColor.RED;        
        Paragraph valorRecebP = modelo.criaCampo( campo, valor, cor ) ;

        campo = CAMPO_DATA;
        valor = df.formataData( dataDia );
        cor = BaseColor.BLUE;        
        Paragraph dataDiaP = modelo.criaCampo( campo, valor, cor );
                    
        RelatorioTabelaM1 tabelaModelo = new RelatorioTabelaM1();
        for( int i = 0; i < TABELA_COLUNAS.length; i++ ) {
            String rotulo = TABELA_COLUNAS[ i ];
            int largura = TABELA_COLUNAS_LARGURAS[ i ];
            if ( i == VALOR_PAGO_INDICE ) {                
                tabelaModelo.addColuna( rotulo, largura, VALOR_PAGO_COR );
            } else {
                tabelaModelo.addColuna( rotulo, largura );
            }               
        }
        
        for( RelatorioDiaTO reldia : rpag.getPagamentos() ) {
            String nomeUsuario = reldia.getNomeUsuario();
            String nomeAluno = reldia.getNomeAluno();
            String valorPago = nf.formatoReal( reldia.getValor() );
            String hora = df.formataHora( reldia.getDataPag() );
            tabelaModelo.addLinha( nomeUsuario, nomeAluno, valorPago, hora );
        }
        
        PdfPTable tabela = tabelaModelo.construirTabela();
        
        doc.add( tituloP );
        doc.add( new Paragraph( " " ) );
        doc.add( valorRecebP );
        doc.add( dataDiaP );
        doc.add( new Paragraph( " " ) );
        doc.add( new Paragraph( " " ) );
        doc.add( tabela );        
        
        doc.close();                                                                     
    }
    
}
