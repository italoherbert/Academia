package academia.loginoper.usuario;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.usuario.AlterarSenhaControlador;
import academia.controlador.usuario.UsuarioCtrlUtil;
import academia.gui.GUI;
import academia.gui.login.LoginGUITO;
import academia.gui.usuario.alterarsenha.AlterarSenhaGUITO;
import academia.gui.usuario.filtro.UsuarioFiltroGUITO;
import academia.loginoper.OperLoginOK;
import academia.util.MSGUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AlterarSenhaUsuarioOperLoginOK implements OperLoginOK {

    private ControladorTO cTO;
    private AlterarSenhaGUITO altSenhaGUITO;
    private int[] permissoes;
    
    public AlterarSenhaUsuarioOperLoginOK( ControladorTO cTO, AlterarSenhaGUITO altSenhaGUITO ) {
        this.cTO = cTO;
        this.altSenhaGUITO = altSenhaGUITO;
        this.permissoes = new int[] { cTO.getBDConfig().getAdminUsuarioTipoID() };
    }
    
    public void loginOK( LoginGUITO loginGUITO, UsuarioTO usuarioLogado ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        UsuarioCtrlUtil usuarioCUtil = cTO.getUsuarioCUtil();
        
        String nomeUsuario = altSenhaGUITO.getNomeUsuario();
        String senha = new String( altSenhaGUITO.getSenha() );
        String senha2 = new String( altSenhaGUITO.getSenha2() );
        
        List<String> erros = this.validaForm( altSenhaGUITO );
        if ( erros.isEmpty() ) {
            if ( senha.equals( senha2 ) ) {
                try { 
                    bd.getUsuarioDAO().alteraSenha( nomeUsuario, senha );

                    UsuarioFiltroGUITO ufTO = gui.getJP().getUsuarioGUI().getFiltroGUI().getTO();
                    usuarioCUtil.filtra( ufTO );
                    
                    altSenhaGUITO.setVisivel( false ); 

                    msgUtil.mostraInfo( MSGConsts.SENHA_ALTERADA, MSGConsts.USUARIO_TITULO ); 
                } catch (DAOException ex) {
                    Logger.getLogger(AlterarSenhaControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                msgUtil.mostraAlerta( MSGConsts.SENHAS_DESIGUAIS, MSGConsts.USUARIO_TITULO ); 
            }
        } else {
            String msg = msgUtil.constroiMSG( erros );
            msgUtil.mostraAlerta( msg, MSGConsts.USUARIO_TITULO ); 
        }
    }

    public int[] getPermissoes() {
        return permissoes;
    }
    
    private List<String> validaForm( AlterarSenhaGUITO to ) {        
        List<String> erros = new ArrayList();
        
        if ( to.getSenha().length == 0 )
            erros.add( MSGConsts.SENHA_OBRIGATORIA );        
        
        if ( to.getSenha2().length == 0 )
            erros.add( MSGConsts.SENHA_REPETIDA_OBRIGATORIA );
        
        return erros;
    }
    
}
