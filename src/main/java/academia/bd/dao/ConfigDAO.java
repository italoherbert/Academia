package academia.bd.dao;

import academia.bd.to.ConfigTO;
import academia.bd.dao.util.DAOUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import libs.bd.GBD;

public class ConfigDAO {

    private GBD gbd;
    private DAOUtil util;

    public ConfigDAO( GBD gbd, DAOUtil util ) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public void alteraTolerancia( int novaTol ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update config set pg_tolerancia=?"
            );
            ps.setInt( 1, novaTol );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public void alteraAutoCarregarUsuarios( boolean acu ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update config set auto_carregar_usuarios=?"
            );
            ps.setBoolean( 1, acu );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public void alteraAutoCarregarModalidades( boolean acm ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update config set auto_carregar_modalidades=?"
            );
            ps.setBoolean( 1, acm );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public ConfigTO busca() throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select pg_tolerancia, "
                     + "auto_carregar_usuarios, "
                     + "auto_carregar_modalidades, "
                     + "admin_usuario_tipo_id, "
                     + "func_usuario_tipo_id, "
                     + "diaria_aluno_id "
              + "from config" 
            );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                ConfigTO cfg = new ConfigTO();
                cfg.setPGTolerancia( rs.getInt( "pg_tolerancia" ) );                
                cfg.setAutoCarregarUsuarios( rs.getBoolean( "auto_carregar_usuarios" ) ); 
                cfg.setAutoCarregarModalidades( rs.getBoolean( "auto_carregar_modalidades" ) );
                cfg.setAdminUsuarioTipoID( rs.getInt( "admin_usuario_tipo_id" ) );
                cfg.setFuncUsuarioTipoID( rs.getInt( "func_usuario_tipo_id" ) );
                cfg.setDiariaAlunoID( rs.getInt( "diaria_aluno_id" ) );                
                return cfg;
            }
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
}
