package academia.controlador.jp;

import academia.Consts;
import academia.bd.BD;
import academia.bd.to.ConfigTO;
import academia.bd.dao.DAOException;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.VisualizacaoMGR;
import academia.controlador.modalidade.ModalidadeCtrlUtil;
import academia.controlador.usuario.UsuarioCtrlUtil;
import academia.gui.GUI;
import academia.gui.config.ConfigGUITO;
import academia.gui.jp.JPGUIListener;
import academia.gui.jp.JPGUITO;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;
import academia.gui.usuario.filtro.UsuarioFiltroGUITO;
import academia.util.DataUtil;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.bd.GBDException;

public class JPControlador implements JPGUIListener {

    private ControladorTO cTO;
    
    public JPControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void usuariosBTAcionado(JPGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        
        UsuarioCtrlUtil usuarioCUtil = cTO.getUsuarioCUtil();
        
        try {
            UsuarioFiltroGUITO ufTO = gui.getJP().getUsuarioGUI().getFiltroGUI().getTO();
            
            ConfigTO config = bd.getConfigDAO().busca();
            if ( config.isAutoCarregarUsuarios() ) {
                ufTO.setNome( Consts.CARACTER_BUSCA_TODOS );
                usuarioCUtil.filtra( ufTO );
            } else {
                ufTO.limpar();
            }
        } catch (DAOException ex) {
            Logger.getLogger(JPControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void modalidadesBTAcionado(JPGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        VisualizacaoMGR visuMGR = cTO.getVisuMGR();
        
        ModalidadeCtrlUtil modCUtil = cTO.getModCUtil();
        UsuarioTO usuarioLogado = cTO.getUsuarioLogado();
        
        try {
            ModalidadeFiltroGUITO mfTO = gui.getJP().getModalidadeGUI().getFiltroGUI().getTO();                

            ConfigTO config = bd.getConfigDAO().busca();
            if ( config.isAutoCarregarModalidades() ) {
                mfTO.setDescricao( Consts.CARACTER_BUSCA_TODOS );               
                modCUtil.filtra( mfTO );                
            } else {
                mfTO.limpar();
            }
            
            visuMGR.configVisuJMod( usuarioLogado.getTipoID() );
        } catch ( DAOException ex ) {
            Logger.getLogger(JPControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void relatoriosBTAcionado( JPGUITO to ) {
        GUI gui = cTO.getGUI();
        DataUtil df = cTO.getDataUtil();
        
        Timestamp dataTS = new Timestamp( System.currentTimeMillis() );
        String dataDia = df.formataData( dataTS );
        
        gui.getJP().getRelatorioGUI().getTO().setDataDia( dataDia );
    }

    public void configBTAcionado(JPGUITO to) {
        BD bd = cTO.getBD();        
        
        ConfigGUITO cfgTO = to.getConfigTO();
        
        try {
            ConfigTO cfg = bd.getConfigDAO().busca();
                        
            cfgTO.setToleranciaPag( String.valueOf( cfg.getPGTolerancia() ) ); 
            cfgTO.setSelecAutoCarregarUsuarios( cfg.isAutoCarregarUsuarios() ); 
            cfgTO.setSelecAutoCarregarModalidades( cfg.isAutoCarregarModalidades() ); 
        } catch (DAOException ex) {
            Logger.getLogger(JPControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sairBTAcionado( JPGUITO to ) {
        GUI gui = cTO.getGUI();
        
        to.setVisivel( false );
        
        gui.getLoginInicialGUI().getTO().limpar();
        gui.getLoginInicialGUI().getTO().setVisivel( true );         
    }
    
    public void fecharBTAcionado( JPGUITO to ) {
        try {
            cTO.getSistemaCtrlUtil().finalizar();
        } catch ( GBDException ex ) {
            Logger.getLogger(JPControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
