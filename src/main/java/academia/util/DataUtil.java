package academia.util;

import academia.Consts;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {

    private SimpleDateFormat formatadorDataHora = new SimpleDateFormat( Consts.DATA_HORA_MASCARA_SDF );
    private SimpleDateFormat formatadorData = new SimpleDateFormat( Consts.DATA_MASCARA_SDF );
    private SimpleDateFormat formatadorHora = new SimpleDateFormat( Consts.HORA_MASCARA_SDF ); 
    private SimpleDateFormat formatadorDataMes = new SimpleDateFormat( Consts.DATA_MES_SDF );        
    private SimpleDateFormat formatadorDia = new SimpleDateFormat( Consts.DIA_SDF );
    
    public Date apenasData( Date data ) {
        if ( data == null )
            return null;
        
        String dataS = this.formataData( data );
        try {            
            return new Timestamp( this.converteData( dataS ).getTime() );
        } catch ( ParseException ex) {
            throw new RuntimeException( ex );
        }
    }        
    
    public Date calculaDataPag( Date dataMat ) {
        Calendar c = Calendar.getInstance();
        c.setTime( dataMat );
        
        int dia = c.get( Calendar.DAY_OF_MONTH );
        if ( dia > 28 ) {
            c.add( Calendar.MONTH, 1 );
            c.set( Calendar.DAY_OF_MONTH, 1 );
        }
        
        return c.getTime();
    }
    
    public int calculaIdade( Date dataNasc ) {
        Calendar c = Calendar.getInstance();
        c.setTime( dataNasc );
        
        Calendar c2 = Calendar.getInstance();
        c2.setTime( new Date() );
        
        int diaNasc = c.get( Calendar.DAY_OF_YEAR );
        int anoNasc = c.get( Calendar.YEAR );
        
        int diaAtual = c2.get( Calendar.DAY_OF_YEAR );
        int anoAtual = c2.get( Calendar.YEAR );
        
        int idade = anoAtual - anoNasc;
        
        if ( diaAtual < diaNasc )
            idade--;
        
        return idade;
    }
    
    public String formataDataDia( Date data ) {
        return formatadorDia.format( data );
    }
    
    public String formataDataMes( Date data ) {
        return formatadorDataMes.format( data );
    }
    
    public String formataDataHoraAtual() {
        return this.formataDataHora( new Date() );
    }
    
    public String formataDataAtual() {
        return formatadorData.format( new Date() );
    }
        
    public String formataDataHora( Date data ) {
        return formatadorDataHora.format( data );
    }
    
    public String formataHora( Date data ) {
        return formatadorHora.format( data );
    }
    
    public String formataData( Date data ) {
        return formatadorData.format( data );
    }
    
    public Date converteData( String data ) throws ParseException {
        return formatadorData.parse( data );
    }
        
    public Date extrairData( Date data ) throws ParseException {
        String dataS = formatadorData.format( data ); 
        return formatadorData.parse( dataS );
    }    
    
}
