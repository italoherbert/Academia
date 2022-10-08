package academia.gui.aluno;

import academia.gui.aluno.filtro.AlunoFiltroGUI;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import academia.gui.aluno.filtro.AlunoTBLDuploClickListener;
import academia.gui.aluno.filtro.FiltrarAlunoFuncListener;
import javax.swing.BoxLayout;

public class AlunoGUI extends JPanel 
        implements AlunoTBLDuploClickListener, FiltrarAlunoFuncListener, 
            ActionListener {
    
    private AlunoFiltroGUI filtroGUI = new AlunoFiltroGUI();
    
    private JButton cadastrarBT;
    private JButton editarBT;
    private JButton pagamentoParcelasBT;
    private JButton pagamentoDiariaBT;
        
    private JButton listarMatBT;
    private JButton encerrarMatBT;
    private JButton novaMatBT;
        
    private AlunoGUITO to = new AlunoGUITO( this );
    private AlunoGUIListener listener;
    
    public AlunoGUI() {
        cadastrarBT = new JButton( "Cadastrar" );
        editarBT = new JButton( "Editar" );
        pagamentoParcelasBT = new JButton( "Pag. parcelas" );
        pagamentoDiariaBT = new JButton( "Pag. diaria" );
        
        listarMatBT = new JButton( "Listar matriculas" );
        encerrarMatBT = new JButton( "Encerrar matricula" );
        novaMatBT = new JButton( "Nova matricula" );
                        
        JPanel botoesAlunoPNL = new JPanel();
        botoesAlunoPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesAlunoPNL.add( pagamentoDiariaBT );
        botoesAlunoPNL.add( pagamentoParcelasBT );
        botoesAlunoPNL.add( cadastrarBT );
        botoesAlunoPNL.add( editarBT );
        
        JPanel botoesMatPNL = new JPanel();
        botoesMatPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesMatPNL.add( listarMatBT );
        botoesMatPNL.add( encerrarMatBT );
        botoesMatPNL.add( novaMatBT );        
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new BoxLayout( botoesPNL, BoxLayout.Y_AXIS ) );
        botoesPNL.add( botoesMatPNL );
        botoesPNL.add( botoesAlunoPNL );
        
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.CENTER, filtroGUI );
        super.add(BorderLayout.SOUTH , botoesPNL );
        
        cadastrarBT.addActionListener( this );
        editarBT.addActionListener( this ); 
        pagamentoParcelasBT.addActionListener( this ); 
        pagamentoDiariaBT.addActionListener( this );
        
        listarMatBT.addActionListener( this ); 
        encerrarMatBT.addActionListener( this );
        novaMatBT.addActionListener( this ); 
                        
        filtroGUI.setAlunoTBLDuploClickListener( this ); 
        filtroGUI.setFiltrarAlunoFuncListener( this );
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == cadastrarBT ) {
            listener.cadastrarBTAcionado( to );
        } else if ( e.getSource() == editarBT ) {
            listener.editarBTAcionado( to ); 
        } else if ( e.getSource() == pagamentoParcelasBT ) {
            listener.pagamentoParcelasBTAcionado( to );
        } else if ( e.getSource() == pagamentoDiariaBT ) {
            listener.pagamentoDiariaBTAcionado( to );
        } else if ( e.getSource() == listarMatBT ) {
            listener.listarMatsBTAcionado( to );
        } else if ( e.getSource() == encerrarMatBT ) {
            listener.encerrarMatBTAcionado( to );
        } else if ( e.getSource() == novaMatBT ) {
            listener.novaMatBTAcionado( to ); 
        }
    }

    public void duploClick( AlunoFiltroGUITO afTO ) {
        if ( listener == null )
            return;
        
        listener.editarBTAcionado( to ); 
    }

    public void filtrarFuncAcionada( AlunoFiltroGUITO afTO ) {
        /*
        int qLinhas = filtroGUI.getAlunosTBL().getTMD().getQuantLinhas();
        if ( qLinhas > 0 )
            filtroGUI.getAlunosTBL().getTMD().selecionaLinha( 0, 0 );        
        
        pagamentoParcelasBT.requestFocusInWindow();
        */
    }
            
    public void setAlunoListener( AlunoGUIListener listener ) {
        this.listener = listener;
    }
    
    public AlunoGUITO getTO() {
        return to;
    }
    
    public AlunoFiltroGUI getFiltroGUI() {
        return filtroGUI;
    }
    
}
