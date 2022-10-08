package academia.gui.aluno.form.edit.mod;

import academia.gui.painels.selecionarmods.SelecionarModsPNL;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTODriver;

public class EditModalidadeGUI extends JPanel implements SelecionarModsPNLTODriver {
    
    public final static String SEM_MAT_ATIVA_OP = "sem-mat";
    public final static String COM_MAT_ATIVA_OP = "com-mat";        
    
    private SelecionarModsPNL editModPNL = new SelecionarModsPNL( false );
    private JButton historicoBT;
    
    private EditModalidadeGUITO to = new EditModalidadeGUITO( this );
    
    private CardLayout card = new CardLayout();
    
    public EditModalidadeGUI() {
        historicoBT = new JButton( "Histórico de modalidades" );
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        botoesPNL.add( historicoBT );
        
        JPanel editModP = new JPanel();
        editModP.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        editModP.add( editModPNL );
    
        JPanel formPNL = new JPanel();
        formPNL.setLayout( new BorderLayout() );
        formPNL.add( BorderLayout.CENTER, editModP );
        formPNL.add( BorderLayout.SOUTH , botoesPNL );                              
        
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
    
    public EditModalidadeGUITO getTO() {
        return to;
    }        

    public SelecionarModsPNL getSelecionarModsPNL() {
        return editModPNL;
    }

    public JButton getHistoricoBT() {
        return historicoBT;
    }
    
    
}
