package academia.gui.config;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JPanel;

public class ConfigGUI extends JPanel implements ActionListener, ItemListener {
    
    private ConfigPNL configsPNL = new ConfigPNL();
    
    private ConfigGUITO to = new ConfigGUITO( this );
    private ConfigGUIListener listener;
    
    public ConfigGUI() {
        super.setLayout( new BorderLayout() );
        super.add( configsPNL );
        
        configsPNL.getAlterarToleranciaPagBT().addActionListener( this );
        configsPNL.getAutoCarregarUsuariosCB().addItemListener( this );
        configsPNL.getAutoCarregarModsCB().addItemListener( this ); 
    }
    
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null ) 
            return;
        
        if ( e.getSource() == configsPNL.getAlterarToleranciaPagBT() ) {
            listener.alterarToleranciaPagBTAcionado( to );
        }
    }

    public void itemStateChanged( ItemEvent e ) {
        if ( listener == null )
            return;

        if ( e.getSource() == configsPNL.getAutoCarregarUsuariosCB() ) {
            listener.autoCarregarUsuariosCBAcionado( to ); 
        } else if ( e.getSource() == configsPNL.getAutoCarregarModsCB() ) {
            listener.autoCarregarModsCBAcionado( to ); 
        }
    }
    
    public ConfigGUITO getTO() {
        return to;
    }
    
    public void setConfigListener( ConfigGUIListener listener ) {
        this.listener = listener;
    }
    
    public ConfigPNL getConfigPNL() {
        return configsPNL;
    }
    
}
