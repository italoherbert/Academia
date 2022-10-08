package academia.bd.dao.util;

import academia.bd.dao.DAOException;
import java.sql.SQLException;
import libs.bd.GBD;
import libs.bd.GBDPontoSalvo;

public class DAOPontoSalvo {
    
    private GBDPontoSalvo ptSalvo;
    
    public DAOPontoSalvo( GBD gbd ) {
        this.ptSalvo = gbd.criarPontoSalvo();
    }
    
    public void salvaPonto() throws DAOException {
        try {
            ptSalvo.salvaPonto();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }
    
    public void finaliza() throws DAOException {
        try {
            ptSalvo.finalizaPontoSalvo();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }
    
    public void commit() throws DAOException {
        try {
            ptSalvo.commit();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }
    
    public void rollback() throws DAOException {
        try {
            ptSalvo.rollback();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }
    
}
