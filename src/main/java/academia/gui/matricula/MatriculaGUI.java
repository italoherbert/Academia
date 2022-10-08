package academia.gui.matricula;

import academia.gui.jp.JPGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import libs.gui.tabela.TabelaM1;

public class MatriculaGUI extends JDialog implements ActionListener, MouseListener {
    
    private TabelaM1 matsTBL = new TabelaM1();
    private JButton carregarParcelasBT;
    private JButton removerBT;
    private JButton fecharBT;
    
    private MatriculaGUIListener listener;
    private MatriculaGUITO to = new MatriculaGUITO( this );
    
    public MatriculaGUI( JPGUI jp ) {        
        carregarParcelasBT = new JButton( "Carregar parcelas" );
        removerBT = new JButton( "Remover" );
        fecharBT = new JButton( "Fechar" );
        
        matsTBL.getTMD().addColuna( "ID", 50 );
        matsTBL.getTMD().addColuna( "Data de inicio", 150 );
        matsTBL.getTMD().addColuna( "Data de encerramento", 150 ); 
        matsTBL.getTMD().redimensionaColunas();
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesPNL.add( carregarParcelasBT );
        botoesPNL.add( removerBT );
        botoesPNL.add( fecharBT );
        
        JPanel tabelaPNL = new JPanel();
        tabelaPNL.setBorder( new TitledBorder( "Tabela de matrículas" ) ); 
        tabelaPNL.setLayout( new GridLayout() );
        tabelaPNL.add( matsTBL );
        
        JPanel contentPNL = new JPanel();
        contentPNL.setLayout( new BorderLayout() );
        contentPNL.add( BorderLayout.CENTER, tabelaPNL );
        contentPNL.add( BorderLayout.SOUTH, botoesPNL );
        
        super.setTitle( "Matriculas do aluno" ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );    
        super.setContentPane( contentPNL );
        super.setSize( 400, 300 );
        super.setLocationRelativeTo( jp );                   
        super.setAlwaysOnTop( true ); 
                
        carregarParcelasBT.addActionListener( this );         
        removerBT.addActionListener( this ); 
        fecharBT.addActionListener( this );  

        matsTBL.getJTable().addMouseListener( this );        
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == fecharBT ) {
            super.setVisible( false ); 
        } else if ( e.getSource() == carregarParcelasBT ) {
            listener.carregarParcelasBTAcionado( to ); 
        } else if ( e.getSource() == removerBT )  {
            listener.removerBTAcionado( to ); 
        }
    }
    
    public void mouseClicked( MouseEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == matsTBL.getJTable() ) {
            if ( e.getClickCount() == 2 )
               listener.carregarParcelasBTAcionado( to ); 
        }
    }
        
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}           

    
    public MatriculaGUITO getTO() {
        return to;
    }
    
    public void setMatriculaListener( MatriculaGUIListener listener ) {
        this.listener = listener;
    }
    
    public TabelaM1 getMatsTBL() {
        return matsTBL;
    }
    
}
