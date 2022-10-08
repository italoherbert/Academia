package academia.bd.dao;

import academia.bd.dao.util.DAOUtil;
import academia.bd.to.DescontoTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import libs.bd.GBD;

public class DescontoDAO {
    
    private GBD gbd;
    private DAOUtil util;
    
    public DescontoDAO( GBD gbd, DAOUtil util ) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public void insere( DescontoTO d ) throws DAOException {
        PreparedStatement ps = null;
        
        try {
            Timestamp dataAlter;
            if ( d.getDataAlter() == null )
                dataAlter = new Timestamp( System.currentTimeMillis() );
            else dataAlter = d.getDataAlter();
            
            
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "insert into desconto ( mat_id, porcentagem, data_alter ) values ( ?, ?, ? )"
            );
            ps.setInt( 1, d.getMatID() );
            ps.setDouble( 2, d.getPorcentagem() );
            ps.setTimestamp( 3, dataAlter );
            
            ps.executeUpdate();            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }        
    
}
