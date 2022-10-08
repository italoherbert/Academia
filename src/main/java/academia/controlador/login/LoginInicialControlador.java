package academia.controlador.login;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.VisualizacaoMGR;
import academia.gui.GUI;
import academia.gui.login.LoginGUIListener;
import academia.gui.login.LoginGUITO;
import academia.gui.login.LoginInicialGUIListener;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.bd.GBDException;

public class LoginInicialControlador implements LoginGUIListener, LoginInicialGUIListener {

    private ControladorTO cTO;
    
    public LoginInicialControlador( ControladorTO cTO ) {
        this.cTO = cTO;         
    }
    
    public void logarBTAcionado( LoginGUITO to ) {                
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        VisualizacaoMGR visuMGR = cTO.getVisuMGR();
        
        String usuarioNome = to.getUsuario();
        String senha = new String( to.getSenha() );
        
        try {
            UsuarioTO usuario = bd.getUsuarioDAO().busca( usuarioNome, senha );
            if ( usuario == null ) {
                String msg = MSGConsts.LOGIN_INVALIDO.replace( "%1", usuarioNome );
                msgUtil.mostraAlerta( msg, MSGConsts.LOGIN_TITULO ); 
            } else {
                if ( usuario.isAtivo() ) {
                    cTO.setUsuarioLogado( usuario ); 

                    String nome = usuario.getNome();
                    int tipoID = usuario.getTipoID();
                    String tipo = bd.getUsuarioDAO().buscaTipo( tipoID );

                    visuMGR.configVisuJP( tipoID ); 
                                        
                    gui.getJP().getTO().selecionarEMostrarAlunoPNL(); 
                    gui.getJP().getTO().setUsuarioLogado( tipo, nome ); 
                    
                    int quant = bd.getAlunoDAO().quantAlunosCompletandoAno();                    
                    gui.getJP().getAlunoGUI().getFiltroGUI().getTO().setAniverBTQuant( quant );                     
                    
                    to.setVisivel( false );                     
                    gui.getJP().getTO().setVisivel( true );
                } else {
                    String nome = usuario.getNome();
                    
                    String msg = MSGConsts.USUARIO_INATIVO.replace( "%1", nome );
                    msgUtil.mostraAlerta( msg, MSGConsts.LOGIN_TITULO ); 
                }
            }
        } catch ( DAOException ex ) {
            Logger.getLogger(LoginInicialControlador.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }                  
    
    public void fecharBTAcionado() {        
        try {
            cTO.getSistemaCtrlUtil().finalizar();
        } catch ( GBDException ex ) {
            Logger.getLogger(LoginInicialControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
}
