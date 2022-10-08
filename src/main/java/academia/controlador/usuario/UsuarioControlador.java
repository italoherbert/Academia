package academia.controlador.usuario;

import academia.Consts;
import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.BDConfigTO;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.gui.GUI;
import academia.gui.usuario.UsuarioGUIListener;
import academia.gui.usuario.UsuarioGUITO;
import academia.gui.usuario.alterarsenha.AlterarSenhaGUITO;
import academia.gui.usuario.form.UsuarioFormGUITO;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.gui.tabela.TabelaMD;

public class UsuarioControlador implements UsuarioGUIListener {
    
    private ControladorTO cTO;

    public UsuarioControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void editarFuncAcionada( UsuarioGUITO to ) {
        GUI gui = cTO.getGUI();
        BDConfigTO bdConfig = cTO.getBDConfig();
        
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
                
        TabelaMD usuarioTBLMD = to.getFiltroTO().getUsuarioTBLMD();
        
        int indice = usuarioTBLMD.getIndiceLinhaSelecionada();
        if ( indice == TabelaMD.ID_LINHA_NULA ) {            
            msgUtil.mostraAlerta( MSGConsts.NENHUM_USUARIO_SELECIONADO, MSGConsts.USUARIO_TITULO ); 
        } else {
            String idS = usuarioTBLMD.getCelulaValor( indice, 0 );
            int usuarioID = Integer.parseInt( idS );
        
            try {
                UsuarioTO usuario = bd.getUsuarioDAO().busca( usuarioID );
                
                UsuarioFormGUITO uFrmTO = gui.getUsuarioFormGUI().getTO();
                uFrmTO.setID( String.valueOf( usuario.getID() ) );
                uFrmTO.setNome( usuario.getNome() );
                uFrmTO.setNomeUsuario( usuario.getNomeUsuario() ); 
                
                if ( usuario.getTipoID() == bdConfig.getAdminUsuarioTipoID() ) {
                    uFrmTO.setAdminRBSelecionado( true );
                } else if ( usuario.getTipoID() == bdConfig.getFuncUsuarioTipoID() ) {
                    uFrmTO.setFuncRBSelecionado( true ); 
                }
                
                uFrmTO.editConfigVisu();
                uFrmTO.setVisivel( true );
            } catch (DAOException ex) {
                Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
                        
        }
    }

    public void cadastrarBTAcionado( UsuarioGUITO to ) {
        GUI gui = cTO.getGUI();
        
        UsuarioFormGUITO uFrmTO = gui.getUsuarioFormGUI().getTO();
        uFrmTO.limpar();        
        uFrmTO.setID( Consts.TEXTO_VASIO ); 
        
        uFrmTO.cadConfigVisu();        
        uFrmTO.setVisivel( true );        
    }

    public void ativarDesativarBTAcionado( UsuarioGUITO to ) {
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        UsuarioCtrlUtil usuarioCUtil = cTO.getUsuarioCUtil();
        
        TabelaMD usuarioTBLMD = to.getFiltroTO().getUsuarioTBLMD();
        
        int indice = usuarioTBLMD.getIndiceLinhaSelecionada();
        if ( indice == TabelaMD.ID_LINHA_NULA ) {            
            msgUtil.mostraAlerta( MSGConsts.NENHUM_USUARIO_SELECIONADO, MSGConsts.USUARIO_TITULO ); 
        } else {
            String idS = usuarioTBLMD.getCelulaValor( indice, 0 );
            int usuarioID = Integer.parseInt( idS );
            
            try {
                UsuarioTO usuario = bd.getUsuarioDAO().busca( usuarioID );
                boolean ativo = usuario.isAtivo();
                
                boolean ativoNovoValor = !ativo;                
                bd.getUsuarioDAO().alteraEstadoAtivo( usuarioID, ativoNovoValor );
                
                usuarioCUtil.filtra( to.getFiltroTO() );                
            } catch (DAOException ex) {
                Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void alterarSenhaBTAcionado(UsuarioGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
                
        TabelaMD usuarioTBLMD = to.getFiltroTO().getUsuarioTBLMD();
        
        int indice = usuarioTBLMD.getIndiceLinhaSelecionada();
        if ( indice == TabelaMD.ID_LINHA_NULA ) {            
            msgUtil.mostraAlerta( MSGConsts.NENHUM_USUARIO_SELECIONADO, MSGConsts.USUARIO_TITULO ); 
        } else {
            String idS = usuarioTBLMD.getCelulaValor( indice, 0 );
            int usuarioID = Integer.parseInt( idS );
            
            try {
                UsuarioTO usuario = bd.getUsuarioDAO().busca( usuarioID );
                
                AlterarSenhaGUITO asTO = gui.getAlterarSenhaGUI().getTO();
                
                asTO.limpar();
                asTO.setNomeUsuario( usuario.getNomeUsuario() );
                asTO.setVisivel( true );                
            } catch (DAOException ex) {
                Logger.getLogger(UsuarioControlador.class.getName()).log(Level.SEVERE, null, ex);
            }                        
        }
    }
    
}
