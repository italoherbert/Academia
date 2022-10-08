package academia.bd.dao;

import academia.bd.dao.util.DAOUtil;
import academia.bd.to.UsuarioTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import libs.bd.GBD;

public class UsuarioDAO {
    
    private GBD gbd;
    private DAOUtil util;
    
    public UsuarioDAO( GBD gbd, DAOUtil util ) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public void insere( UsuarioTO usuario ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "insert into usuario ( "
                  + "nome, nome_usuario, senha, tipo_id "
              + ") values ( ?, ?, md5( ? ), ? )" 
            );
            ps.setString( 1, usuario.getNome() ); 
            ps.setString( 2, usuario.getNomeUsuario() );
            ps.setString( 3, usuario.getSenha() ); 
            ps.setInt( 4, usuario.getTipoID() ); 
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }  
    }
    
    public void altera( UsuarioTO usuario ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update usuario set "
                  + "nome=?, nome_usuario=?, tipo_id=? "
              + "where id=?" 
            );
            ps.setString( 1, usuario.getNome() ); 
            ps.setString( 2, usuario.getNomeUsuario() );
            ps.setInt( 3, usuario.getTipoID() ); 
            ps.setInt( 4, usuario.getID() ); 
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }   
    }
    
    public void alteraEstadoAtivo( int usuarioID, boolean ativo ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update usuario set ativo=? where id=?" 
            );
            ps.setBoolean( 1, ativo ); 
            ps.setInt( 2, usuarioID );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }        
    
    public void alteraSenha( int usuarioID, String novaSenha ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update usuario set senha=md5(?) where id=?" 
            );
            ps.setString( 1, novaSenha ); 
            ps.setInt( 2, usuarioID );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public void alteraSenha( String nomeUsuario, String novaSenha ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update usuario set senha=md5(?) where nome_usuario=?" 
            );
            ps.setString( 1, novaSenha); 
            ps.setString( 2, nomeUsuario );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public UsuarioTO busca( int id ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();            
            ps = c.prepareStatement( 
                "select * from usuario where id=? limit 1" 
            );
            ps.setInt( 1, id );
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                UsuarioTO u = new UsuarioTO();
                u.setID( id );
                u.setTipoID( rs.getInt( "tipo_id" ) ); 
                u.setNome( rs.getString( "nome" ) ); 
                u.setNomeUsuario( rs.getString( "nome_usuario" ) ); 
                u.setSenha( rs.getString( "senha" ) ); 
                u.setAtivo( rs.getBoolean( "ativo" ) );
                return u;
            }
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }    
    
    public UsuarioTO busca( String nomeUsuario, String senha ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();            
            ps = c.prepareStatement( 
                "select id, tipo_id, nome, ativo "
              + "from usuario "
              + "where nome_usuario=? and senha=md5(?) "
              + "limit 1" 
            );
            ps.setString( 1, nomeUsuario );
            ps.setString( 2, senha );
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                UsuarioTO u = new UsuarioTO();
                u.setID( rs.getInt( "id" ) );
                u.setTipoID( rs.getInt( "tipo_id" ) ); 
                u.setNome( rs.getString( "nome" ) ); 
                u.setNomeUsuario( nomeUsuario ); 
                u.setSenha( senha );
                u.setAtivo( rs.getBoolean( "ativo" ) );
                return u;
            }
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public String buscaNome( int usuarioID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();            
            ps = c.prepareStatement( 
                "select nome from usuario where id=? limit 1" 
            );
            ps.setInt( 1, usuarioID );
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return rs.getString( "nome" );            
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }             
    
    public String buscaNomeUsuario( int usuarioID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select nome_usuario from usuario where id=? limit 1" 
            );
            ps.setInt( 1, usuarioID );
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return rs.getString( "nome_usuario" );
            
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public String buscaTipo( int tipoID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();            
            ps = c.prepareStatement( 
                "select tipo from usuario_tipo where id=? limit 1" 
            );
            ps.setInt( 1, tipoID ); 
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return rs.getString( "tipo" );
            
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public String buscaTipoPorUsuarioID( int usuarioID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();            
            ps = c.prepareStatement( 
                "select ut.tipo "
              + "from usuario u inner join usuario_tipo ut on ut.id=u.tipo_id "
              + "where u.id=?" 
            );
            ps.setInt( 1, usuarioID ); 
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return rs.getString( "tipo" );
            
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }          
    
    public List<UsuarioTO> filtra( String nomeIni ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from usuario where nome ilike ? order by( id )" 
            );
            ps.setString( 1, nomeIni + "%" );
            
            List<UsuarioTO> usuarios = new ArrayList();
            
            ResultSet rs = ps.executeQuery();
            while( rs.next() ) {
                UsuarioTO u = new UsuarioTO();
                u.setID( rs.getInt( "id" ) );
                u.setTipoID( rs.getInt( "tipo_id" ) );                
                u.setNome( rs.getString( "nome" ) );
                u.setNomeUsuario( rs.getString( "nome_usuario" ) );
                u.setSenha( rs.getString( "senha" ) );
                u.setAtivo( rs.getBoolean( "ativo" ) );

                usuarios.add( u );                
            }
            
            return usuarios;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public List<UsuarioTO> buscaTodos() throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from usuario" 
            );
            
            List<UsuarioTO> usuarios = new ArrayList();
            
            ResultSet rs = ps.executeQuery();
            while( rs.next() ) {               
                UsuarioTO u = new UsuarioTO();
                u.setID( rs.getInt( "id" ) );
                u.setTipoID( rs.getInt( "tipo_id" ) );                
                u.setNome( rs.getString( "nome" ) );
                u.setNomeUsuario( rs.getString( "nome_usuario" ) );
                u.setSenha( rs.getString( "senha" ) );
                u.setAtivo( rs.getBoolean( "ativo" ) );

                usuarios.add( u );
            }
            
            return usuarios;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public boolean existeNomeUsuario( String nomeUsuario ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select id from usuario where nome_usuario=? limit 1" 
            );
            ps.setString( 1, nomeUsuario );
            
            ResultSet rs = ps.executeQuery();
            return ( rs.next() );
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public void remove( int usuarioID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "delete from usuario where id=?" 
            );
            ps.setInt( 1, usuarioID );
            
            ps.executeUpdate();            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }        
    }
    
}
