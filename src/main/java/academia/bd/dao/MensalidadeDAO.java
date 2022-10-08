package academia.bd.dao;

import academia.bd.to.MensalidadeTO;
import academia.bd.dao.util.DAOUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import libs.bd.GBD;

public class MensalidadeDAO {
    
    private GBD gbd;
    private DAOUtil util;
    
    public MensalidadeDAO( GBD gbd, DAOUtil util ) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public void insere( MensalidadeTO men ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "insert into mensalidade ( mod_id, valor ) values ( ?, ? )" 
            );
            ps.setInt( 1, men.getModID() );
            ps.setDouble( 2, men.getValor() ); 
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
}
