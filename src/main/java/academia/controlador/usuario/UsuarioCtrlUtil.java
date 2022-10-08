package academia.controlador.usuario;

import academia.Consts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.gui.usuario.filtro.UsuarioFiltroGUITO;
import java.util.ArrayList;
import java.util.List;
import libs.gui.tabela.TabelaMD;

public class UsuarioCtrlUtil {
    
    private ControladorTO cTO;

    public UsuarioCtrlUtil(ControladorTO cTO) {
        this.cTO = cTO;
    }
    
    public int filtra( UsuarioFiltroGUITO to ) throws DAOException {
        BD bd = cTO.getBD();
        
        TabelaMD usuariosTBLMD = to.getUsuarioTBLMD();
        boolean mostrarInativos = to.isMostrarUsuariosInativos();
        String nome = to.getNome();
        
        List<UsuarioTO> usuarios = new ArrayList( 0 );
        
        if ( !nome.isEmpty() ) {
            if ( nome.equals( "*" ) ) {
                usuarios = bd.getUsuarioDAO().buscaTodos();
            } else {
                usuarios = bd.getUsuarioDAO().filtra( nome );
            }
        }
        
        usuariosTBLMD.removeTodasAsLinhas();
        
        for( UsuarioTO u : usuarios ) {
            int tipoID = u.getTipoID();

            if( u.isAtivo() || mostrarInativos ) {                
                String tipo = bd.getUsuarioDAO().buscaTipo( tipoID );                
                String estado = ( u.isAtivo() ? Consts.ATIVIDADE_ATIVO : Consts.ATIVIDADE_INATIVO );

                usuariosTBLMD.addLinha( new String[] {
                    String.valueOf( u.getID() ),
                    u.getNome(),
                    tipo,
                    estado,
                    u.getNomeUsuario(),
                    u.getSenha()
                } );
            }
        }
        
        return usuarios.size();
        
    }
    
}
