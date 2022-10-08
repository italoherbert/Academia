package academia.loginoper.usuario;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.usuario.UsuarioCtrlUtil;
import academia.controlador.usuario.UsuarioFormControlador;
import academia.gui.GUI;
import academia.gui.login.LoginGUITO;
import academia.gui.usuario.filtro.UsuarioFiltroGUITO;
import academia.gui.usuario.form.UsuarioFormGUITO;
import academia.loginoper.OperLoginOK;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RemoverUsuarioOperLoginOK implements OperLoginOK {
    
    private ControladorTO cTO;
    private UsuarioFormGUITO uFormGUITO;
            
    private int[] permissoes;
    
    public RemoverUsuarioOperLoginOK( ControladorTO cTO, UsuarioFormGUITO uFormTO ) {
        this.cTO = cTO;
        this.uFormGUITO = uFormTO;
        this.permissoes = new int[] { cTO.getBDConfig().getAdminUsuarioTipoID() };
    }
    
    public void loginOK( LoginGUITO loginGUITO, UsuarioTO usuarioLogado ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        UsuarioCtrlUtil usuarioCUtil = cTO.getUsuarioCUtil();
        
        if ( uFormGUITO.getID().isEmpty() ) {
            msgUtil.mostraErro( MSGConsts.ID_NAO_CARREGADO, MSGConsts.MOD_TITULO ); 
        } else { 
            int usuarioID = Integer.parseInt( uFormGUITO.getID() );
            
            try {
                String nome = bd.getUsuarioDAO().buscaNome( usuarioID );

                boolean pagReg = bd.getPagamentoDAO().pagamentoRegistradoPorUsuarioID( usuarioID );
                if ( pagReg ) {
                    String msg = MSGConsts.USUARIO_NAO_REMOVIDO.replace( "%1", nome );
                    msgUtil.mostraAlerta( msg, MSGConsts.USUARIO_TITULO ); 
                } else {
                    String pergunta = MSGConsts.PERGUNTA_REMOVER_USUARIO.replace( "%1", nome );
                    int result = msgUtil.mostraPergunta( pergunta, MSGConsts.USUARIO_TITULO );
                    if ( result == JOptionPane.YES_OPTION ) {
                        bd.getUsuarioDAO().remove( usuarioID );

                        UsuarioFiltroGUITO ufTO = gui.getJP().getUsuarioGUI().getFiltroGUI().getTO();
                        usuarioCUtil.filtra( ufTO );

                        uFormGUITO.setVisivel( false ); 
                        
                        msgUtil.mostraInfo( MSGConsts.USUARIO_REMOVIDO, MSGConsts.USUARIO_TITULO );
                    }
                }
                                                
            } catch (DAOException ex) {
                Logger.getLogger(UsuarioFormControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int[] getPermissoes() {
        return permissoes;
    }
    
}
