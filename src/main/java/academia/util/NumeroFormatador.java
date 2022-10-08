package academia.util;

import academia.Consts;
import java.text.DecimalFormat;
import java.text.ParseException;

public class NumeroFormatador {
    
    private DecimalFormat realDF = new DecimalFormat( Consts.NUMERO_REAL_MASCARA );
    private DecimalFormat numeroDF = new DecimalFormat( Consts.NUMERO_FLUTUANTE_MASCARA );
    private DecimalFormat fatorDF = new DecimalFormat( Consts.NUMERO_FATOR_MASCARA );        
    
    public String formatoReal( double n ) {
        return realDF.format( n );
    }
    
    public String formatoFlutuante( double n ) {
        return numeroDF.format( n );
    }    
    
    public String formatoFator( double n ) {
        return fatorDF.format( n );
    }
        
    public double valorReal( String n ) throws ParseException {
        return realDF.parse( n ).doubleValue();
    }
        
    public double valorFlutuante( String n ) throws ParseException {
        return numeroDF.parse( n ).doubleValue();
    }
    
    public double valorFator( String n ) throws ParseException {
        return fatorDF.parse( n ).doubleValue();
    }
        
}
