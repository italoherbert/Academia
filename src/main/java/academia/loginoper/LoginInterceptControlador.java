package academia.loginoper;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.gui.login.LoginGUIListener;
import academia.gui.login.LoginGUITO;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginInterceptControlador implements LoginGUIListener {
    
    private ControladorTO cTO;
    private OperLoginOK operacao;
    
    public LoginInterceptControlador( ControladorTO cTO, OperLoginOK oper ) {
        this.cTO = cTO;
        this.operacao = oper;
    }
    
    public void logarBTAcionado( LoginGUITO to ) {
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        String nomeUsuario = to.getUsuario();
        String senha = new String( to.getSenha() );
        
        try {
            UsuarioTO usuario = bd.getUsuarioDAO().busca( nomeUsuario, senha );
            if ( usuario == null ) {
                String msg = MSGConsts.LOGIN_INVALIDO.replace( "%1", nomeUsuario );
                msgUtil.mostraAlerta( msg, MSGConsts.LOGIN_TITULO ); 
            } else {
                int tipoID = usuario.getTipoID();
                
                boolean temPerm = this.temPermissao( tipoID );
                if ( temPerm ) {                    
                    to.setVisivel( false ); 
                    
                    operacao.loginOK( to, usuario );
                } else {                                
                    to.setVisivel( false ); 
                    
                    String msg = MSGConsts.SEM_PERMISSAO.replace( "%1", nomeUsuario );
                    msgUtil.mostraAlerta( msg, MSGConsts.LOGIN_TITULO );                     
                }
            }
        } catch (DAOException ex) {
            Logger.getLogger(LoginInterceptControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private boolean temPermissao( int tipoID ) {
        int[] permissoes = operacao.getPermissoes();
        for( int i = 0; i < permissoes.length; i++ )
            if ( tipoID == permissoes[i] )
                return true;
        
        return false;
    }
    
}
