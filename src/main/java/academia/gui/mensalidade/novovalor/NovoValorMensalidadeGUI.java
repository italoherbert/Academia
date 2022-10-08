package academia.gui.mensalidade.novovalor;

import academia.GUIConsts;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class NovoValorMensalidadeGUI extends JDialog implements ActionListener {
    
    private NovoValorMensalidadePNL nvPNL = new NovoValorMensalidadePNL();
    
    private NovoValorMensalidadeGUITO to = new NovoValorMensalidadeGUITO( this );
    private NovoValorMensalidadeGUIListener listener;        
    
    public NovoValorMensalidadeGUI( Container jp ) {        
        nvPNL.getValorAtualLV().setForeground( GUIConsts.COR_VALOR );
        
        super.setTitle( "Alteração de valor da mensalidade" ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );
        super.setContentPane( nvPNL ); 
        super.pack();
        super.setLocationRelativeTo( jp );                      
        super.setAlwaysOnTop( true ); 
        super.setModal( false );
        super.setModalityType( ModalityType.APPLICATION_MODAL );            
        
        nvPNL.getAlterarNovoValorBT().addActionListener( this );
        nvPNL.getLimparBT().addActionListener( this );
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        if( e.getSource() == nvPNL.getAlterarNovoValorBT() ) {
            listener.alterarBTAcionado( to ); 
        } else if ( e.getSource() == nvPNL.getLimparBT() ) {
            listener.limparBTAcionado( to ); 
        }
    }

    public NovoValorMensalidadeGUITO getTO() {
        return to;
    }
    
    public void setNovoValorMensListener( NovoValorMensalidadeGUIListener listener ) {
        this.listener = listener;
    }
        
    public NovoValorMensalidadePNL getNovoValorPNL() {
        return nvPNL;
    }
           
}
