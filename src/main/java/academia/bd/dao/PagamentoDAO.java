package academia.bd.dao;

import academia.bd.to.PagamentoTO;
import academia.bd.dao.util.DAOUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import libs.bd.GBD;

public class PagamentoDAO {
    
    private GBD gbd; 
    private DAOUtil util;

    public PagamentoDAO( GBD gbd, DAOUtil util ) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public void insere( PagamentoTO pag ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "insert into pagamento ( mat_id, usuario_id, valor, desconto, data_pag ) values ( ?, ?, ?, ?, ? )" 
            );
            ps.setInt( 1, pag.getMatID() );
            ps.setInt( 2, pag.getUsuarioID() ); 
            ps.setDouble( 3, pag.getValor() );            
            ps.setDouble( 4, pag.getDesconto() ); 
            ps.setTimestamp( 5, pag.getDataPag() ); 
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }        
    
    public void remove( int id ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "delete from pagamento where id=?"
            );
            ps.setInt( 1, id );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public List<PagamentoTO> buscaPagsPorDataDia( Timestamp dataDia ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from pagamento where date( data_pag ) = date( ? )" 
            );
            ps.setTimestamp( 1, dataDia );
                                    
            List<PagamentoTO> pags = new ArrayList();

            ResultSet rs = ps.executeQuery();            
            while( rs.next() ) {
                PagamentoTO pag = new PagamentoTO();
                pag.setID( rs.getInt( "id" ) );
                pag.setUsuarioID( rs.getInt( "usuario_id" ) );
                pag.setMatID( rs.getInt( "mat_id" ) );
                pag.setValor( rs.getDouble( "valor" ) );
                pag.setDesconto( rs.getDouble( "desconto" ) );
                pag.setDataPag( rs.getTimestamp( "data_pag" ) ); 
                pags.add( pag );
            }
            
            return pags;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public boolean pagamentoRegistradoPorUsuarioID( int usuarioID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();            
            ps = c.prepareStatement( 
                "select id from pagamento where usuario_id=? limit 1" 
            );
            ps.setInt( 1, usuarioID ); 
            
            ResultSet rs = ps.executeQuery();
            
            return ( rs.next() );
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
}
