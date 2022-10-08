package academia.relatorio.modelo;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;

public class RelatorioM1 {
    
    public final static BaseColor FONTE_CAMPO_COR = BaseColor.BLACK;
       
    public final static int TITULO_FONTE_TAM = 24;
    public final static int CAMPO_FONTE_TAM = 14;
    
    public final static FontFamily FAMILIA_FONTE = Font.FontFamily.TIMES_ROMAN;
    public final static int ESTILO_FONTE = Font.NORMAL;
        
    public Paragraph criaTitulo( String titulo ) {
        Font fonte = this.criaFonte( TITULO_FONTE_TAM ); 
        
        Paragraph p = new Paragraph( titulo, fonte );
        p.setAlignment( Element.ALIGN_CENTER );
        
        return p;
    }
        
    public Paragraph criaCampo( String campo, String valor, BaseColor corValor ) {
        Font fonteCampo = this.criaFonte( CAMPO_FONTE_TAM );
        Font fonteValor = this.criaFonte( CAMPO_FONTE_TAM );
        
        fonteCampo.setColor( FONTE_CAMPO_COR ); 
        fonteValor.setColor( corValor ); 
                
        Anchor campoA = new Anchor( campo, fonteCampo );
        Anchor valorA = new Anchor( valor, fonteValor );
        
        Paragraph p = new Paragraph();
        p.add( campoA );
        p.add( valorA );
        
        return p;
    }     
        
    public RelatorioTabelaM1 criaTabelaModelo() {
        return new RelatorioTabelaM1();
    }
    
    private Font criaFonte( int tam ) {
        return new Font( FAMILIA_FONTE, tam, ESTILO_FONTE );
    }
    
}
