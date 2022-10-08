package academia.bd.dao.util;

import academia.bd.dao.DAOException;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOUtil {
    
    public void fechaST( Statement st ) throws DAOException {
        try {
            if ( st != null )
                if ( st.isClosed() )
                    st.close();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        }
    }        
        
}
