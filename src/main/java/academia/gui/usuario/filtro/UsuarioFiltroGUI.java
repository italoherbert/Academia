package academia.gui.usuario.filtro;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import libs.gui.tabela.TabelaM1;

public class UsuarioFiltroGUI extends JPanel implements 
        ActionListener, MouseListener, KeyListener, ItemListener {

    private TabelaM1 usuarioTBL = new TabelaM1();
    private UsuarioBuscaPNL buscaPNL = new UsuarioBuscaPNL();
    
    private UsuarioFiltroGUITO to = new UsuarioFiltroGUITO( this );
    private UsuarioFiltroGUIListener listener;
    private UsuarioSelecionadoListener usListener;
    
    public UsuarioFiltroGUI() {
        usuarioTBL.getTMD().addColuna( "ID", 50 );
        usuarioTBL.getTMD().addColuna( "Nome", 200 );
        usuarioTBL.getTMD().addColuna( "Tipo", 120 );
        usuarioTBL.getTMD().addColuna( "Estado", 80 );
        usuarioTBL.getTMD().addColuna( "Nome de usuário", 150 );
        usuarioTBL.getTMD().addColuna( "Senha", 150 );
        usuarioTBL.getTMD().redimensionaColunas();
        
        super.setBorder( new TitledBorder( "Tabela de usuários" ) );
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.CENTER, usuarioTBL );
        super.add( BorderLayout.SOUTH, buscaPNL );        
        
        buscaPNL.getFiltrarBT().addActionListener( this );
        buscaPNL.getLimparBT().addActionListener( this );
        
        buscaPNL.getMostrarUsuariosInativosCB().addItemListener( this );
        buscaPNL.getNomeTF().addKeyListener( this ); 
        
        usuarioTBL.getJTable().addMouseListener( this ); 
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
            
        if ( e.getSource() == buscaPNL.getFiltrarBT() ) {
            listener.filtrarBTAcionado( to ); 
        } else if ( e.getSource() == buscaPNL.getLimparBT() ) {
            to.limpar();
        }
    }

    public void itemStateChanged( ItemEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == buscaPNL.getMostrarUsuariosInativosCB() ) {
            listener.mostrarInativosCBAlterado( to ); 
        }
    }

    public void mouseClicked( MouseEvent e ) {
        if ( usListener == null )
            return;
        
        if ( e.getSource() == usuarioTBL.getJTable() ) {
            if ( e.getClickCount() == 2 )
                usListener.usuarioSelecionado( to );  
        }
    }

    public void keyPressed( KeyEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == buscaPNL.getNomeTF() ) {
            if ( e.getKeyChar() == KeyEvent.VK_ENTER ) 
                listener.filtrarBTAcionado( to ); 
        }
    }
            
    public void keyTyped(KeyEvent ke) {}
    public void keyReleased(KeyEvent ke) {}

    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

        
    public void setUsuarioFiltroListener( UsuarioFiltroGUIListener listener ) {
        this.listener = listener;
    }
    
    public void setUsuarioSelecionadoListener( UsuarioSelecionadoListener listener ) {
        usListener = listener;
    }
    
    public UsuarioFiltroGUITO getTO() {
        return to;
    }

    public TabelaM1 getUsuarioTBL() {
        return usuarioTBL;
    }

    public UsuarioBuscaPNL getBuscaPNL() {
        return buscaPNL;
    }
    
}
