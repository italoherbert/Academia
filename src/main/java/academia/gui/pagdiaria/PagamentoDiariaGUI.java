package academia.gui.pagdiaria;

import academia.GUIConsts;
import academia.gui.painels.selecionarmods.SelecionarModsPNL;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTO;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTODriver;
import libs.gui.listaadd.ListaAdd;
import libs.gui.listaadd.ListaAddListener;
import libs.gui.listaadd.ListaAddMD;

public class PagamentoDiariaGUI extends JFrame 
        implements SelecionarModsPNLTODriver, ListaAddListener, ActionListener {
    
    private SelecionarModsPNL selecionarModsPNL = new SelecionarModsPNL();
    
    private PagamentoDiariaGUITO to = new PagamentoDiariaGUITO( this );
    private PagamentoDiariaGUIListener listener;
    
    private JLabel totalL;
    private JLabel totalLV;
    
    private JButton registrarBT;
    private JButton fecharBT;
    
    public PagamentoDiariaGUI( Container jp ) {
        totalL = new JLabel( "Total: " );
        totalLV = new JLabel();
        
        registrarBT = new JButton( "Registrar" );
        fecharBT = new JButton( "Fechar" );              
        
        totalLV.setForeground( GUIConsts.COR_VALOR );                 
        
        JPanel modsPNL = new JPanel();
        modsPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) ); 
        modsPNL.add( selecionarModsPNL );
        
        JPanel infoPNL = new JPanel();
        infoPNL.setLayout( new FlowLayout( FlowLayout.LEFT ) );
        infoPNL.add( totalL );
        infoPNL.add( totalLV );
        
        JPanel botoesPNL = new JPanel();
        botoesPNL.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
        botoesPNL.add( registrarBT );
        botoesPNL.add( fecharBT );
        
        JPanel centroPNL = new JPanel();
        centroPNL.setLayout( new BorderLayout() );
        centroPNL.add( BorderLayout.CENTER, modsPNL );
        centroPNL.add( BorderLayout.SOUTH, infoPNL );
        
        JPanel conteudoPNL = new JPanel();
        conteudoPNL.setLayout( new BorderLayout() );
        conteudoPNL.add( BorderLayout.CENTER, centroPNL );
        conteudoPNL.add( BorderLayout.SOUTH, botoesPNL );
        
        super.setTitle( "Pagamento de diária" ); 
        super.setContentPane( conteudoPNL );
        super.setDefaultCloseOperation( JFrame.HIDE_ON_CLOSE ); 
        super.pack();
        super.setLocationRelativeTo( jp );
        super.setAlwaysOnTop( true );        
        
        fecharBT.addActionListener( this );
        registrarBT.addActionListener( this );         
        
        selecionarModsPNL.getListaAdd().setListaAddListener( this ); 
    }

    public void addBTAcionado( ListaAddMD la ) {
        if ( listener != null )
            listener.addModBTAcionado( to );
    }

    public void removeBTAcionado( ListaAddMD la ) {
        if ( listener != null )
            listener.removeModBTAcionado( to ); 
    }

    public void actionPerformed(ActionEvent e) {
        if ( listener == null )
            return;
            
        if ( e.getSource() == fecharBT ) {
            super.setVisible( false );
        } else if ( e.getSource() == registrarBT ) {
            listener.registrarBTAcionado( to ); 
        }
    }
    
    public void setPagDiariaListener( PagamentoDiariaGUIListener listener ) {
        this.listener = listener;
    }
    
    public PagamentoDiariaGUITO getTO() {
        return to;
    }

    public SelecionarModsPNLTO getEditModPNLTO() {
        return to;
    }
    
    public SelecionarModsPNL getSelecionarModsPNL() {
        return selecionarModsPNL;
    }

    public JLabel getTotalLV() {
        return totalLV;
    }
    
}
