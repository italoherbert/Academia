package academia.gui.relatorio;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class RelatorioGUI extends JPanel implements ActionListener {
    
    private RelatorioPNL relatorioPNL = new RelatorioPNL();
    
    private RelatorioGUITO to = new RelatorioGUITO( this );
    private RelatorioGUIListener listener;

    public RelatorioGUI() {
        super.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        super.add( relatorioPNL );
        
        relatorioPNL.getGerarRelDiaBT().addActionListener( this ); 
        relatorioPNL.getGerarRelAlunosEmDiaBT().addActionListener( this ); 
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == relatorioPNL.getGerarRelDiaBT() ) {
            listener.gerarRelatorioDiaBTAcionado( to ); 
        } else if ( e.getSource() == relatorioPNL.getGerarRelAlunosEmDiaBT() ) {
            listener.gerarRelatorioAlunosEmDiaBTAcionado( to ); 
        }
    }
    
    public void setRelatorioGUIListener( RelatorioGUIListener listener ) {
        this.listener = listener;
    }
    
    public RelatorioGUITO getTO() {
        return to;
    }      
    
    public RelatorioPNL getRelatorioPNL() {
        return relatorioPNL;
    }
    
}
