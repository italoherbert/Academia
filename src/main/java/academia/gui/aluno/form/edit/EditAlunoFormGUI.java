package academia.gui.aluno.form.edit;

import academia.gui.GUIConfig;
import academia.gui.aluno.form.edit.aluno.EditAlunoGUI;
import academia.gui.aluno.form.edit.medidas.EditMedidasGUI;
import academia.gui.aluno.form.edit.mod.EditModalidadeGUI;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import libs.gui.listaadd.ListaAddListener;
import libs.gui.listaadd.ListaAddMD;

public class EditAlunoFormGUI extends JFrame implements ActionListener, MouseListener, ListaAddListener {
        
    private EditAlunoGUI editAlunoGUI = new EditAlunoGUI(); 
    private EditMedidasGUI editMedsGUI = new EditMedidasGUI();
    private EditModalidadeGUI editModGUI = new EditModalidadeGUI();
    
    private EditAlunoBotoesPNL botoesPNL = new EditAlunoBotoesPNL();
        
    private JLabel tituloL;
    private JTabbedPane tabPNL;    
        
    private EditAlunoFormGUITO to = new EditAlunoFormGUITO( this ); 
    private EditAlunoFormGUIListener listener;
    
    public EditAlunoFormGUI( Container jp, GUIConfig config ) {               
        tituloL = new JLabel( "Formulario de Alunos" );
        tituloL.setFont( new Font( "Times New Roman", Font.PLAIN, 24 ) );                               
        
        JPanel tituloPNL = new JPanel();
        tituloPNL.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        tituloPNL.add( tituloL );
           
        tabPNL = new JTabbedPane();
        tabPNL.add( "Aluno", editAlunoGUI );
        
        if ( config.isCarregarFuncMedidas() )
            tabPNL.add( "Medidas", editMedsGUI );
        
        tabPNL.add( "Modalidades", editModGUI );
        
        JPanel painel = new JPanel();
        painel.setLayout( new BorderLayout() );
        painel.add( BorderLayout.NORTH , tituloPNL );
        painel.add( BorderLayout.CENTER, tabPNL );
        painel.add( BorderLayout.SOUTH , botoesPNL );
        
        super.setTitle( "Formulario de alunos" ); 
        super.setDefaultCloseOperation( JDialog.HIDE_ON_CLOSE );                
        super.setContentPane( painel );        
        super.pack();
        super.setLocationRelativeTo( jp );                          
        
        editAlunoGUI.getAlunoFormGUI().getFormPNL().getIDTF().setEditable( false ); 
        editAlunoGUI.getAlunoFormGUI().getFormPNL().getDataMatriculaTF().setEditable( false );         
        
        botoesPNL.getRemoverAlunoBT().addActionListener( this );
        botoesPNL.getFecharBT().addActionListener( this );
        
        editAlunoGUI.getSalvarBT().addActionListener( this );
        editAlunoGUI.getLimparBT().addActionListener( this );        
        
        editModGUI.getHistoricoBT().addActionListener( this );
        
        editMedsGUI.getRegistrarNovaBT().addActionListener( this );
        editMedsGUI.getEditarBT().addActionListener( this ); 
        editMedsGUI.getCompararBT().addActionListener( this );
        
        editMedsGUI.getMedidasTBL().getJTable().addMouseListener( this ); 
        
        editModGUI.getSelecionarModsPNL().getListaAdd().setListaAddListener( this );
    }

    public void addBTAcionado( ListaAddMD la ) {
        if ( listener != null )                        
            listener.addModalidadeBTAcionado( to );  
    }

    public void removeBTAcionado( ListaAddMD la ) {
        if ( listener != null )
            listener.removeModalidadeBTAcionado( to );                     
    }
            
    public void actionPerformed( ActionEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == botoesPNL.getFecharBT() ) {
            super.setVisible( false ); 
        } else if ( e.getSource() == botoesPNL.getRemoverAlunoBT() ) {
            listener.removerBTAcionado( to ); 
        } else if ( e.getSource() == editAlunoGUI.getSalvarBT() ) {
            listener.salvarDadosAlunoBTAcionado( to );
        } else if ( e.getSource() == editAlunoGUI.getLimparBT() ) {
            listener.limparDadosAlunoBTAcionado( to );
        } else if ( e.getSource() == editModGUI.getHistoricoBT() ) {
            listener.historicoModalidadesBTAcionado( to );
        } else if ( e.getSource() == editMedsGUI.getRegistrarNovaBT() ) {
            listener.registrarNovasMedidasBTAcionado( to );
        } else if ( e.getSource() == editMedsGUI.getEditarBT() ) {
            listener.editarMedidasFuncAcionada( to );
        } else if ( e.getSource() == editMedsGUI.getCompararBT() ) {
            listener.compararMedidasBTAcionado( to ); 
        }
    }
    
    public void mouseClicked( MouseEvent e ) {
        if ( listener == null )
            return;
        
        if ( e.getSource() == editMedsGUI.getMedidasTBL().getJTable() ) {
            if ( e.getClickCount() == 2 )
               listener.editarMedidasFuncAcionada( to ); 
        }
    }
        
    public void mousePressed(MouseEvent me) {}
    public void mouseReleased(MouseEvent me) {}
    public void mouseEntered(MouseEvent me) {}
    public void mouseExited(MouseEvent me) {}           

    
    
    public void setEditAlunoFormListener( EditAlunoFormGUIListener listener ) {
        this.listener = listener;
    }
    
    public EditAlunoFormGUITO getTO() {
        return to;
    }

    public EditAlunoGUI getEditAlunoGUI() {
        return editAlunoGUI;
    }    

    public EditModalidadeGUI getEditModGUI() {
        return editModGUI;
    }
    
    public EditMedidasGUI getEditMedsGUI() {
        return editMedsGUI;
    }

    public EditAlunoBotoesPNL getBotoesPNL() {
        return botoesPNL;
    }
    
    
    
}
