package academia.util;

public class ModalidadeFormatador {
 
    public String formataModalidade( int id, String descricao ) {
        return ""+id+": "+descricao;
    }
    
    public int extraiModalidadeID( String modFormat ) {
        return Integer.parseInt( modFormat.split( ":" )[0] );
    }
    
}
