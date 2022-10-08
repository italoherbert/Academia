package academia.gui.modalidade.filtro;

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

public class ModalidadeFiltroGUI extends JPanel 
        implements ActionListener, MouseListener, KeyListener, ItemListener {     
    
    private TabelaM1 modsTBL = new TabelaM1();
    private ModalidadeFiltroPNL filtroPNL = new ModalidadeFiltroPNL();
        
    private ModalidadeFiltroGUITO to = new ModalidadeFiltroGUITO( this );    
    private ModalidadeFiltroGUIListener listener;
    private ModalidadeSelecionadaListener msListener;
    
    private boolean carregarModalidade = true;
    
    public ModalidadeFiltroGUI() {                    
        modsTBL.getTMD().addColuna( "ID", 50 );
        modsTBL.getTMD().addColuna( "Descrição", 250 );        
        modsTBL.getTMD().addColuna( "Valor", 100 ); 
        modsTBL.getTMD().addColuna( "Valor diária", 120 );
        modsTBL.getTMD().addColuna( "Data de início", 120 );
        modsTBL.getTMD().addColuna( "Data de fim", 120 );
        modsTBL.getTMD().redimensionaColunas();
        
        super.setLayout( new BorderLayout() ); 
        super.setBorder( new TitledBorder( "Tabela de modalidades" ) );
        super.add( BorderLayout.CENTER, modsTBL );
        super.add( BorderLayout.SOUTH, filtroPNL );        
                
        filtroPNL.getFiltrarBT().addActionListener( this ); 
        filtroPNL.getLimparBT().addActionListener( this ); 
        filtroPNL.getDescricaoTF().addKeyListener( this );  
        filtroPNL.getMostrarModsInativasCB().addItemListener( this );
        
        modsTBL.getJTable().addMouseListener( this );         
    }
        
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
            
        if ( e.getSource() == filtroPNL.getFiltrarBT() ) {
            this.funcFiltrarAcionada();
        } else if ( e.getSource() == filtroPNL.getLimparBT() ) {
            to.limpar();
        }
    }

    public void itemStateChanged( ItemEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == filtroPNL.getMostrarModsInativasCB() ) {
            listener.mostrarInativasCBAlterado( to ); 
        }
    }

    public void mouseClicked( MouseEvent e ) {
        if ( msListener == null )
            return;
        
        if ( e.getSource() == modsTBL.getJTable() ) {
            if ( e.getClickCount() == 2 )
                if ( carregarModalidade )
                    msListener.modalidadeSelecionada( to );  
        }        
    }

    public void keyPressed( KeyEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == filtroPNL.getDescricaoTF() ) {
            if ( e.getKeyChar() == KeyEvent.VK_ENTER ) 
                this.funcFiltrarAcionada();
        }
    }
    
    public void funcFiltrarAcionada() {
        if ( listener == null )
            return;
        
        listener.filtrarBTAcionado( to ); 
    }

    public void keyTyped(KeyEvent ke) {}
    public void keyReleased(KeyEvent ke) {}

    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}

    
    
    public ModalidadeFiltroGUITO getTO() {
        return to;
    }
    
    public void setModalidadeFiltroListener( ModalidadeFiltroGUIListener listener ) {
        this.listener = listener;
    }    
    
    public void setProdutoSelecionadoListener( ModalidadeSelecionadaListener listener ) {
        this.msListener = listener;
    }

    public TabelaM1 getModsTBL() {
        return modsTBL;
    }

    public ModalidadeFiltroPNL getFiltroPNL() {
        return filtroPNL;
    }     

    public boolean isCarregarModalidade() {
        return carregarModalidade;
    }

    public void setCarregarModalidade(boolean carregarModalidade) {
        this.carregarModalidade = carregarModalidade;
    }
    
}
