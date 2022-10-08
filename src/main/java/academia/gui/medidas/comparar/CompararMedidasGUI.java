package academia.gui.medidas.comparar;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import libs.gui.tabela.TabelaM1;

public class CompararMedidasGUI extends JDialog implements ActionListener {
    
    private TabelaM1 medidasTBL = new TabelaM1();
    
    private JButton fecharBT;
    
    private CompararMedidasGUITO to = new CompararMedidasGUITO( this );
        
    public CompararMedidasGUI( Container jp ) {                
        fecharBT = new JButton( "Fechar" );
        
        medidasTBL.getTMD().addColuna( "", 150 );
        medidasTBL.getTMD().addColuna( "Anterior", 100 );
        medidasTBL.getTMD().addColuna( "Nova", 100 );
        medidasTBL.getTMD().addColuna( "Diferença", 100 );
        medidasTBL.getTMD().redimensionaColunas();
        
        medidasTBL.getTMD().addLinha( new String[]{ "Data de registro", "", "", "" } );         
        medidasTBL.getTMD().addLinha( new String[]{ "Peso (Kg)", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Altura (Metros)", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Braço esquerdo", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Braço direito", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Antebraço esquerdo", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Antebraço direito", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Torax", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Cintura", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Bumbum", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Coxa esquerda", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Coxa direita", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Panturrilha esquerda", "", "", "" } ); 
        medidasTBL.getTMD().addLinha( new String[]{ "Panturrilha direita", "", "", "" } ); 
        
        medidasTBL.setBorder( new TitledBorder( "Comparação de medidas" ) );
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesPNL.add( fecharBT );
        
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.CENTER, medidasTBL );
        conteudoPNL.add( BorderLayout.SOUTH, botoesPNL );
               
        super.setTitle( "Comparação de medidas do aluno" );
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.setContentPane( conteudoPNL );                 
        super.setSize( 550, 500 );
        super.setLocationRelativeTo( jp ); 
        super.setAlwaysOnTop( true ); 
        super.setModal( false );
        super.setModalityType( ModalityType.APPLICATION_MODAL );                
        
        fecharBT.addActionListener( this );
    }

    public void actionPerformed( ActionEvent e ) {        
        if ( e.getSource() == fecharBT ) {
            super.setVisible( false ); 
        }
    }
    
    public CompararMedidasGUITO getTO() {
        return to;
    }
    
    public TabelaM1 getMedidasTBL() {
        return medidasTBL;
    }
    
}
