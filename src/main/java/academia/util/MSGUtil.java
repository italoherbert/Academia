package academia.util;

import java.util.List;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class MSGUtil {

    private JDialog dialog = new JDialog();
    
    public String constroiMSG( List<String> mensagens ) {
        StringBuilder sb = new StringBuilder();
        boolean prim = true;
        for( String msg : mensagens ) {
            if ( prim ) {
                sb.append( msg );
                prim = false;
            } else {
                sb.append( "\n" );
                sb.append( msg );
            }
        }  
        
        return sb.toString();
    }
    
    public void mostraInfo( String msg, String titulo ) {
        dialog.setAlwaysOnTop( true ); 
        JOptionPane.showMessageDialog( dialog, msg, titulo, JOptionPane.INFORMATION_MESSAGE ); 
    }
    
    public void mostraAlerta( String msg, String titulo ) {
        dialog.setAlwaysOnTop( true ); 
        JOptionPane.showMessageDialog( dialog, msg, titulo, JOptionPane.WARNING_MESSAGE ); 
    }
    
    public void mostraErro( String msg, String titulo ) {
        dialog.setAlwaysOnTop( true ); 
        JOptionPane.showMessageDialog( dialog, msg, titulo, JOptionPane.ERROR_MESSAGE ); 
    }    
    
    public int mostraPergunta( String msg, String titulo ) {
        dialog.setAlwaysOnTop( true ); 
        return JOptionPane.showConfirmDialog( dialog, msg, titulo, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
    }
    
}

