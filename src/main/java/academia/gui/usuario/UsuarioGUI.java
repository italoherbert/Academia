package academia.gui.usuario;

import academia.gui.usuario.filtro.UsuarioFiltroGUI;
import academia.gui.usuario.filtro.UsuarioFiltroGUITO;
import academia.gui.usuario.filtro.UsuarioSelecionadoListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class UsuarioGUI extends JPanel implements ActionListener, UsuarioSelecionadoListener {
 
    private UsuarioFiltroGUI filtroGUI = new UsuarioFiltroGUI();
    
    private JButton alterarSenhaBT;
    private JButton ativarDesativarBT;
    private JButton editarBT;
    private JButton cadastrarBT;
    
    private UsuarioGUITO to = new UsuarioGUITO( this );
    private UsuarioGUIListener listener;
    
    public UsuarioGUI() {
        cadastrarBT = new JButton( "Cadastrar" );
        editarBT = new JButton( "Editar" );        
        ativarDesativarBT = new JButton( "Ativar/Desativar" );
        alterarSenhaBT = new JButton( "Alterar senha" );
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) ); 
        botoesPNL.add( alterarSenhaBT );
        botoesPNL.add( ativarDesativarBT );
        botoesPNL.add( cadastrarBT );
        botoesPNL.add( editarBT );
        
        super.setLayout( new BorderLayout() );
        super.add( BorderLayout.CENTER, filtroGUI );
        super.add( BorderLayout.SOUTH , botoesPNL );
        
        cadastrarBT.addActionListener( this );
        editarBT.addActionListener( this );
        ativarDesativarBT.addActionListener( this ); 
        alterarSenhaBT.addActionListener( this ); 
        
        filtroGUI.setUsuarioSelecionadoListener( this ); 
    }

    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == cadastrarBT ) {
            listener.cadastrarBTAcionado( to );
        } else if ( e.getSource() == editarBT ) {
            listener.editarFuncAcionada( to ); 
        } else if ( e.getSource() == ativarDesativarBT ) {
            listener.ativarDesativarBTAcionado( to ); 
        } else if ( e.getSource() == alterarSenhaBT ) {
            listener.alterarSenhaBTAcionado( to ); 
        }
    }

    public void usuarioSelecionado( UsuarioFiltroGUITO ufTO ) {
        if ( listener == null )
            return;
        
        listener.editarFuncAcionada( to ); 
    }
    
    public void setUsuarioListener( UsuarioGUIListener listener ) {
        this.listener = listener;
    }
    
    public UsuarioGUITO getTO() {
        return to;
    }

    public UsuarioFiltroGUI getFiltroGUI() {
        return filtroGUI;
    }        
    
}
