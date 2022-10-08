package academia.gui.aluno.filtro;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import libs.gui.tabela.TabelaM1;

public class AlunoFiltroGUI extends JPanel 
        implements ActionListener, ItemListener, MouseListener, KeyListener {
    
    private TabelaM1 alunosTBL = new TabelaM1();
    private AlunoBuscaPNL buscaPNL = new AlunoBuscaPNL();
    
    private JButton aniverBT;
    
    private AlunoFiltroGUITO to = new AlunoFiltroGUITO( this );
    private AlunoFiltroGUIListener listener;
    private AlunoTBLDuploClickListener dcListener;
    private FiltrarAlunoFuncListener filtrarFuncListener;
            
    public AlunoFiltroGUI() {
        aniverBT = new JButton();
        
        buscaPNL.getMostrarAlunosInativos().setSelected( false ); 
                
        alunosTBL.getTMD().addColuna( "ID", 50 );
        alunosTBL.getTMD().addColuna( "Nome", 150 );
        alunosTBL.getTMD().addColuna( "Idade", 100 );
        alunosTBL.getTMD().addColuna( "Estado", 120 );
        alunosTBL.getTMD().addColuna( "Ativo", 100 ); 
        alunosTBL.getTMD().addColuna( "Observações", 120 );
        alunosTBL.getTMD().redimensionaColunas();
        
        JPanel anivPNL = new JPanel();
        anivPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        anivPNL.add( aniverBT );
        
        JPanel sulPNL = new JPanel();
        sulPNL.setLayout( new BorderLayout() );
        sulPNL.add( BorderLayout.WEST, buscaPNL );
        sulPNL.add(BorderLayout.EAST, anivPNL );
                
        super.setBorder( new TitledBorder( "Tabela de alunos" ) ); 
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.CENTER, alunosTBL );
        super.add( BorderLayout.SOUTH, sulPNL );        
     
        aniverBT.addActionListener( this );
        
        buscaPNL.getNomeTF().addKeyListener( this );         
        buscaPNL.getFiltrarBT().addActionListener( this ); 
        buscaPNL.getLimparBT().addActionListener( this ); 
        
        buscaPNL.getMostrarAlunosInativos().addItemListener( this );
        
        alunosTBL.getJTable().addMouseListener( this );                 
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null ) 
            return;
        
        if ( e.getSource() == aniverBT ) {
            listener.aniverBTAcionado( to );  
        } else if ( e.getSource() == buscaPNL.getFiltrarBT() ) {
            this.filtrarFunc( to ); 
        } else if ( e.getSource() == buscaPNL.getLimparBT() ) {
            to.limpar();
        }
    }

    public void itemStateChanged( ItemEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == buscaPNL.getMostrarAlunosInativos() ) {
            listener.mostrarAlunosInativosCBAlterado( to ); 
        }
    }

    public void keyTyped( KeyEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == buscaPNL.getNomeTF() ) {
            if ( e.getKeyChar() == KeyEvent.VK_ENTER ) {
                this.filtrarFunc( to ); 
            }
        }
    }
    
    public void mouseClicked( MouseEvent e ) {
        if ( dcListener == null )
            return;
        
        if ( e.getSource() == alunosTBL.getJTable() ) {
            if ( e.getClickCount() == 2 )
                dcListener.duploClick( to ); 
        }
    }
            
    public void keyPressed(KeyEvent ke) {};
    public void keyReleased(KeyEvent ke) {};

    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}
    
    private void filtrarFunc( AlunoFiltroGUITO to ) {
        if ( listener != null )
            listener.filtrarBTAcionado( to );
        
        if ( filtrarFuncListener != null )
            filtrarFuncListener.filtrarFuncAcionada( to ); 
    }
        
    public void setAlunoFiltroListener( AlunoFiltroGUIListener listener ) {
        this.listener = listener;
    }
    
    public void setFiltrarAlunoFuncListener( FiltrarAlunoFuncListener listener ) {
        this.filtrarFuncListener = listener;
    }
    
    public void setAlunoTBLDuploClickListener( AlunoTBLDuploClickListener listener ) {
        this.dcListener = listener;
    }
    
    public AlunoFiltroGUITO getTO() {
        return to;
    }
    
    public TabelaM1 getAlunosTBL() {
        return alunosTBL;
    }
    
    public AlunoBuscaPNL getBuscaPNL() {
        return buscaPNL;
    }

    public JButton getAniversariantesBT() {
        return aniverBT;
    }

}
