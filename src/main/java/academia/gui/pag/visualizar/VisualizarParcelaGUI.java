package academia.gui.pag.visualizar;

import academia.GUIConsts;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import libs.gui.tabela.TabelaM1;

public class VisualizarParcelaGUI extends JFrame implements ActionListener {
    
    private TabelaM1 parcelasTBL = new TabelaM1();               

    private DadosParcelaPNL dadosParcelaPNL = new DadosParcelaPNL();
    
    private JButton fecharBT;
    
    private VisualizarParcelaGUITO to = new VisualizarParcelaGUITO( this ); 
    
    public VisualizarParcelaGUI( Container jp ) {                          
        fecharBT = new JButton( "Fechar" );
    
        dadosParcelaPNL.getDataParcelaLV().setForeground( GUIConsts.COR_VALOR );
        dadosParcelaPNL.getSubtotalLV().setForeground( GUIConsts.COR_VALOR ); 
        dadosParcelaPNL.getTotalLV().setForeground( GUIConsts.COR_VALOR ); 
        
        parcelasTBL.getTMD().addColuna( "ID", 50 );
        parcelasTBL.getTMD().addColuna( "Descrição", 250 );
        parcelasTBL.getTMD().addColuna( "Data de contrato", 150 );
        parcelasTBL.getTMD().addColuna( "Valor", 100 );
        parcelasTBL.getTMD().addColuna( "Quant. dias", 100 );
        parcelasTBL.getTMD().addColuna( "Desconto - Aluno (%)", 170 );
        parcelasTBL.getTMD().addColuna( "Desconto (%)", 120);
        parcelasTBL.getTMD().addColuna( "Total", 100 );
        parcelasTBL.getTMD().redimensionaColunas();
                         
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesPNL.add( fecharBT );
        
        JPanel psul = new JPanel();
        psul.setLayout( new BoxLayout( psul, BoxLayout.Y_AXIS ) );
        psul.add( dadosParcelaPNL );
        psul.add( botoesPNL );
        
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setBorder( new TitledBorder( "Tabela de modalidades da parcela" ) );
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.CENTER, parcelasTBL );
        conteudoPNL.add( BorderLayout.SOUTH, psul );
        
        super.setTitle( "Parcela do aluno" ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE ); 
        super.setContentPane( conteudoPNL );        
        super.setSize( 650, 500 );
        super.setLocationRelativeTo( jp );                            
        super.setAlwaysOnTop( true ); 
        
        fecharBT.addActionListener( this ); 
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( e.getSource() == fecharBT ) {
            super.setVisible( false ); 
        }
    }
    
    public VisualizarParcelaGUITO getTO() {
        return to;
    }

    public TabelaM1 getParcelasTBL() {
        return parcelasTBL;
    }

    public DadosParcelaPNL getDadosParcelaPNL() {
        return dadosParcelaPNL;
    }
    
}
