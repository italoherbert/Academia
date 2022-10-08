package academia.gui.modalidade;

import academia.gui.modalidade.filtro.ModalidadeFiltroGUI;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;
import academia.gui.modalidade.filtro.ModalidadeSelecionadaListener;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ModalidadeGUI extends JPanel 
        implements ModalidadeSelecionadaListener, ActionListener {
    
    private JButton cadastrarBT;
    private JButton editarBT;
    private JButton finalizarBT;
    
    private JButton editarMensalidadesBT;
    
    private JPanel botoesPNL;
    
    private ModalidadeFiltroGUI filtroGUI = new ModalidadeFiltroGUI();
    
    private ModalidadeGUITO to = new ModalidadeGUITO( this );
    private ModalidadeGUIListener listener;

    public ModalidadeGUI() {
        cadastrarBT = new JButton( "Cadastrar" );
        editarBT = new JButton( "Editar" );
        finalizarBT = new JButton( "Finalizar" );
        
        editarMensalidadesBT = new JButton( "Editar mensalidades" );
                
        JPanel pagsBotoesPNL = new JPanel();
        pagsBotoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        pagsBotoesPNL.add( editarMensalidadesBT );
        
        JPanel modBotoesPNL = new JPanel();
        modBotoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        modBotoesPNL.add( finalizarBT );
        modBotoesPNL.add( cadastrarBT );
        modBotoesPNL.add( editarBT );        
        
        botoesPNL = new JPanel();
        botoesPNL.setLayout( new BoxLayout( botoesPNL, BoxLayout.Y_AXIS ) );
        botoesPNL.add( pagsBotoesPNL );
        botoesPNL.add( modBotoesPNL );
        
        super.setLayout( new BorderLayout() ); 
        super.add( BorderLayout.CENTER, filtroGUI );
        super.add( BorderLayout.SOUTH, botoesPNL );
                
        cadastrarBT.addActionListener( this );
        editarBT.addActionListener( this );
        finalizarBT.addActionListener( this );
        
        editarMensalidadesBT.addActionListener( this ); 
        
        filtroGUI.setProdutoSelecionadoListener( this );
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == cadastrarBT ) {
            listener.cadastrarBTAcionado( to ); 
        } else if ( e.getSource() == editarBT ) {
            listener.funcEditarAcionada( to ); 
        } else if ( e.getSource() == finalizarBT ) {
            listener.finalizarBTAcionado( to ); 
        } else if ( e.getSource() == editarMensalidadesBT ) {
            listener.editarMensalidadesBTAcionado( to ); 
        }
    }

    public void modalidadeSelecionada( ModalidadeFiltroGUITO mfTO ) {
        if ( listener == null )
            return;
        
        listener.funcEditarAcionada( to ); 
    }
    
    public ModalidadeGUITO getTO() {
        return to;
    }
    
    public void setModalidadeListener( ModalidadeGUIListener listener ) {
        this.listener = listener;
    }

    public ModalidadeFiltroGUI getFiltroGUI() {
        return filtroGUI;
    }

    public JPanel getBotoesPNL() {
        return botoesPNL;
    }

}
