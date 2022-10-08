package academia.loginoper.usuario;

import academia.Consts;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalvarUsuarioOperLoginOK implements OperLoginOK {

    private ControladorTO cTO;
    private UsuarioFormGUITO uFormGUITO;
            
    private int[] permissoes;
    
    public SalvarUsuarioOperLoginOK( ControladorTO cTO, UsuarioFormGUITO guiTO ) {
        this.cTO = cTO;
        this.uFormGUITO = guiTO;
        this.permissoes = new int[] { cTO.getBDConfig().getAdminUsuarioTipoID() };
    }
    
    public void loginOK( LoginGUITO loginGUITO, UsuarioTO usuarioLogado ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        
        MSGUtil msgUtil = cTO.getMSGUtil();
        UsuarioCtrlUtil usuarioCUtil = cTO.getUsuarioCUtil();
        
        try {
            List<String> erros = this.validaForm( uFormGUITO );
            if ( erros.isEmpty() ) {
                UsuarioTO usuario = new UsuarioTO();
                usuario.setNome( uFormGUITO.getNome() );
                usuario.setNomeUsuario( uFormGUITO.getNomeUsuario() );

                if ( uFormGUITO.isAdminRBSelecionado() ) {
                    usuario.setTipoID( cTO.getBDConfig().getAdminUsuarioTipoID() );
                } else if ( uFormGUITO.isFuncRBSelecionado() ) {
                    usuario.setTipoID( cTO.getBDConfig().getFuncUsuarioTipoID() );
                } else {
                    throw new RuntimeException( "Tipo de usuário inválido" ) ;
                }

                if ( uFormGUITO.getID().isEmpty() ) {
                    usuario.setSenha( new String( uFormGUITO.getSenha() ) ); 
                    
                    bd.getUsuarioDAO().insere( usuario );                           
                } else {
                    int usuarioID = Integer.parseInt( uFormGUITO.getID() );

                    usuario.setID( usuarioID );
                    bd.getUsuarioDAO().altera( usuario );                
                }

                UsuarioFiltroGUITO ufTO = gui.getJP().getUsuarioGUI().getFiltroGUI().getTO();
                usuarioCUtil.filtra( ufTO );

                uFormGUITO.setVisivel( false ); 

                msgUtil.mostraInfo( MSGConsts.DADOS_SALVOS, MSGConsts.USUARIO_TITULO );            
            } else {
                String msg = msgUtil.constroiMSG( erros );
            
                msgUtil.mostraAlerta( msg, MSGConsts.USUARIO_TITULO ); 
            }            
        } catch (DAOException ex) {
            Logger.getLogger(UsuarioFormControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int[] getPermissoes() {
        return permissoes;
    }
    
    private List<String> validaForm( UsuarioFormGUITO to ) throws DAOException {
        BD bd = cTO.getBD();
        
        List<String> erros = new ArrayList();
        
        if( to.getNome().isEmpty() )
            erros.add( MSGConsts.NOME_OBRIGATORIO );
        
        if ( to.getNomeUsuario().isEmpty() ) {
            erros.add( MSGConsts.NOME_USUARIO_OBRIGATORIO );        
        } else {
            String nomeUsuario = to.getNomeUsuario();   
            
            if ( to.getID().isEmpty() ) {
                boolean existe = bd.getUsuarioDAO().existeNomeUsuario( nomeUsuario );
                if ( existe ) {
                    String msg = MSGConsts.USUARIO_JA_EXISTE.replace( "%1", nomeUsuario );
                    erros.add( msg );
                }
            } else {
                int usuarioID = Integer.parseInt( to.getID() );
                
                String nomeUsuarioAtual = bd.getUsuarioDAO().buscaNomeUsuario( usuarioID );
                if ( !nomeUsuario.equalsIgnoreCase( nomeUsuarioAtual ) ) {
                    boolean existe = bd.getUsuarioDAO().existeNomeUsuario( nomeUsuario );
                    if ( existe ) {
                        String msg = MSGConsts.USUARIO_JA_EXISTE.replace( "%1", nomeUsuario );
                        erros.add( msg );
                    }
                }
            }
        }
        
        if ( to.getID().isEmpty() ) {
            if ( to.getSenha().length == 0 )
                erros.add( MSGConsts.SENHA_OBRIGATORIA );
            if ( to.getSenha2().length == 0 )
                erros.add( MSGConsts.SENHA_REPETIDA_OBRIGATORIA );
        
            char[] senha = to.getSenha();
            char[] senha2 = to.getSenha2();
            boolean iguais = senha.length == senha2.length;
            for( int i = 0; iguais && i < senha.length; i++ )
                if ( senha[i] != senha2[i] )
                    iguais = false;
            
            if ( !iguais ) 
                erros.add( MSGConsts.SENHAS_DESIGUAIS );
        }                        
        
        return erros;
    }
    
}
