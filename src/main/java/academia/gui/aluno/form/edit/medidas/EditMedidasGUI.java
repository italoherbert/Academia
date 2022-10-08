package academia.gui.aluno.form.edit.medidas;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import libs.gui.tabela.TabelaM1;

public class EditMedidasGUI extends JPanel {
    
    public final static String SEM_MAT_ATIVA_OP = "sem-mat";
    public final static String COM_MAT_ATIVA_OP = "com-mat";
        
    private TabelaM1 medidasTBL = new TabelaM1();
    private JButton registrarNovaBT;
    private JButton compararBT;
    private JButton editarBT;
    
    private CardLayout card = new CardLayout();
    
    private EditMedidasGUITO to = new EditMedidasGUITO( this );
    
    public EditMedidasGUI() {
        registrarNovaBT = new JButton( "Registrar nova" ); 
        editarBT = new JButton( "Editar" );
        compararBT = new JButton( "Comparar" );
        
        medidasTBL.getTMD().addColuna( "ID", 50 );
        medidasTBL.getTMD().addColuna( "Peso (Kg)", 100 );
        medidasTBL.getTMD().addColuna( "Altura (Metros)", 120 );
        medidasTBL.getTMD().redimensionaColunas();               
        medidasTBL.getJTable().setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION ); 
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        botoesPNL.add( compararBT );
        botoesPNL.add( registrarNovaBT );                        
        botoesPNL.add( editarBT );
        
        JPanel formPNL = new JPanel();        
        formPNL.setLayout( new BorderLayout() );
        formPNL.add( BorderLayout.CENTER, medidasTBL );
        formPNL.add( BorderLayout.SOUTH, botoesPNL );
        
        JPanel semMatPNL = new JPanel();
        semMatPNL.setLayout( new FlowLayout( FlowLayout.LEFT ) ); 
        semMatPNL.add( new JLabel( "Sem matrícula ativa." ) );
        
        super.setLayout( card ); 
        super.add( SEM_MAT_ATIVA_OP, semMatPNL );
        super.add( COM_MAT_ATIVA_OP, formPNL );
    }
    
    public void mostrarPNL( String op ) {
        card.show( this, op );
    }
    
    public EditMedidasGUITO getTO() {
        return to;
    }
    
    public TabelaM1 getMedidasTBL() {
        return medidasTBL;
    }

    public JButton getRegistrarNovaBT() {
        return registrarNovaBT;
    }

    public JButton getCompararBT() {
        return compararBT;
    }

    public JButton getEditarBT() {
        return editarBT;
    }
    
}
