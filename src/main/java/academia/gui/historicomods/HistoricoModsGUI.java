package academia.gui.historicomods;

import java.awt.Container;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.TitledBorder;
import libs.gui.tabela.TabelaM1;

public class HistoricoModsGUI extends JDialog {
    
    private TabelaM1 modsTBL = new TabelaM1();
    
    private HistoricoModsGUITO to = new HistoricoModsGUITO( this ); 
    
    public HistoricoModsGUI( Container jp ) {        
        modsTBL.getTMD().addColuna( "ID", 50 );
        modsTBL.getTMD().addColuna( "Descrição", 150 );
        modsTBL.getTMD().addColuna( "Data de contrato", 150 );
        modsTBL.getTMD().addColuna( "Data de encerramento", 170 );
        modsTBL.getTMD().addColuna( "Finalizada", 120 );
        modsTBL.getTMD().redimensionaColunas();
        
        modsTBL.setBorder( new TitledBorder( "Modalidades do aluno" ) );
                
        super.setTitle( "Historico de modalidades do aluno" );
        super.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE ); 
        super.setContentPane( modsTBL ); 
        super.setSize( 600, 300 ); 
        super.setLocationRelativeTo( jp );   
        super.setAlwaysOnTop( true );
        super.setModal( false );
        super.setModalityType( ModalityType.APPLICATION_MODAL ); 
    }
    
    public TabelaM1 getModsTBL() {
        return modsTBL;
    }
    
    public HistoricoModsGUITO getTO() {
        return to;
    }
    
}
