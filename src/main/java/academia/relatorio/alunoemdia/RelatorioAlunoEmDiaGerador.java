package academia.relatorio.alunoemdia;

import academia.relatorio.RelatorioTO;
import academia.relatorio.modelo.RelatorioM1;
import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.OutputStream;
import java.text.DecimalFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class RelatorioAlunoEmDiaGerador {

    private final static String TITULO = "Relatório de alunos em dia";
    private final static String G_TITULO = "Alunos";
    
    private final static String N_ALUNOS = "Nº de alunos: ";
    
    private final static String ROTULO_CONFIG = "{2}";
    private final static String LEGENDA_CONFIG = "{0} = {1}";
    
    private final static String NUM_FORMATO = "0";
    private final static String PORCENT_FORMATO = "0.0%";
    
    private final static String EM_DIA = "Em dia";
    private final static String EM_TOLER = "Em tolerancia";
    private final static String EM_DEBITO = "Em debito";
    
    
    private float largura = 400f;
    private float altura = 300f;
         
    private RelatorioTO rTO;
    private RelatorioM1 modelo = new RelatorioM1();            
            
    public RelatorioAlunoEmDiaGerador( RelatorioTO rTO ) {
        this.rTO = rTO;
    }
    
    public void geraRelatorio( OutputStream arqOut, int contEmDia, int contEmTolerancia, int contEmDebito ) throws DocumentException {
        int contTotal = contEmDia + contEmTolerancia + contEmDebito;
        
        Document doc = new Document();
        
        PdfWriter pdfGravador = PdfWriter.getInstance( doc, arqOut );
        
        doc.open();
        
        Paragraph tituloP = modelo.criaTitulo( TITULO );
        Paragraph nAlunosP = modelo.criaCampo( 
                N_ALUNOS, String.valueOf( contTotal ), BaseColor.BLUE );        
                
        DefaultPieDataset dados = new DefaultPieDataset();
        dados.setValue( EM_DIA, contEmDia );
        dados.setValue( EM_TOLER, contEmTolerancia ); 
        dados.setValue( EM_DEBITO, contEmDebito );
        
        JFreeChart grafico = ChartFactory.createPieChart( 
                G_TITULO, dados, true, true, false );
        
        PiePlot plot = (PiePlot)grafico.getPlot();
        plot.setSectionPaint( 0, Color.BLUE );
        plot.setSectionPaint( 1, Color.CYAN );
        plot.setSectionPaint( 2, Color.RED ); 
        
        DecimalFormat nf = new DecimalFormat( NUM_FORMATO );
        DecimalFormat pf = new DecimalFormat( PORCENT_FORMATO );
        
        StandardPieSectionLabelGenerator gen = 
                new StandardPieSectionLabelGenerator( ROTULO_CONFIG, nf, pf );
        
        StandardPieSectionLabelGenerator legendaGen =
                new StandardPieSectionLabelGenerator( LEGENDA_CONFIG );
        
        plot.setLabelGenerator( gen ); 
        plot.setLegendLabelGenerator( legendaGen );        
                
        
        PdfContentByte conteudoBytes = pdfGravador.getDirectContent();
        
        double l = pdfGravador.getPageSize().getWidth();
        double a = pdfGravador.getPageSize().getHeight();
        Graphics2D g2D = new PdfGraphics2D( conteudoBytes, (int)l, (int)a );
        int x = (int)( ( l - largura ) / 2d );
        int y = 130;
        
        grafico.draw( g2D, new Rectangle2D.Double( x, y, largura, altura ) );
        g2D.dispose();
                   
        doc.add( tituloP );
        doc.add( new Paragraph( " " ) );
        doc.add( nAlunosP );
        
        doc.close();
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(float largura) {
        this.largura = largura;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }
    
}
