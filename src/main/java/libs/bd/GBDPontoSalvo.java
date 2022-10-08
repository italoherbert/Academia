package libs.bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class GBDPontoSalvo {
    
    private GBD gbd;
    private Savepoint sp;

    public GBDPontoSalvo( GBD gbd ) {
        this.gbd = gbd;
    }
    
    public void salvaPonto() throws SQLException {
        Connection c = gbd.getConexao();
        if ( c != null )
            c.setAutoCommit( false ); 
        sp = c.setSavepoint();        
    }
    
    public void finalizaPontoSalvo() throws SQLException {
        Connection c = gbd.getConexao();
        if ( c != null )
            c.setAutoCommit( true );
    }
    
    public void commit() throws SQLException {
        Connection c = gbd.getConexao();
        if ( c != null )
            c.commit();        
    }
    
    public void rollback() throws SQLException {
        Connection c = gbd.getConexao();
        if ( c != null )
            c.rollback( sp );        
    }
    
}
