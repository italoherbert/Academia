
package libs.bd;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class GBD {

    private GBDConfig cfg;
    
    private Connection c = null;
    
    public void abreConexao( GBDArquivoConfig arqCFG ) throws GBDException {
        try {
            Properties p = new Properties();
            p.load( new FileInputStream( arqCFG.getArquivo() ) ); 
            
            GBDConfigImpl impl = new GBDConfigImpl();
            impl.setDriver( p.getProperty( arqCFG.getPropriedadeDriver() ) );
            impl.setURL( p.getProperty( arqCFG.getPropriedadeURL() ) );
            impl.setUsuario( p.getProperty( arqCFG.getPropriedadeUsuario() ) );
            impl.setSenha( p.getProperty( arqCFG.getPropriedadeSenha() ) );
                        
            this.cfg = impl;
            this.abreConexao( impl );  
        } catch ( IOException e ) {
            throw new GBDException( e );
        }
    }
    
    public void abreConexao() throws GBDException {
        this.abreConexao( this.cfg ); 
    }
        
    public void abreConexao( GBDConfig cfg ) throws GBDException {
        try {            
            this.cfg = cfg;
            
            Class.forName( cfg.getDriver() );
            c = DriverManager.getConnection( cfg.getURL(), cfg.getUsuario(), cfg.getSenha() );
        } catch (ClassNotFoundException e) {
            throw new GBDException( "GBD - Não foi possivel carregar o driver: "+cfg.getDriver(), e );
        } catch (SQLException e) {
            throw new GBDException( "GBD - Não foi possível abrir conexão com SGBD", e );
        }
    }
        
    public GBDPontoSalvo criarPontoSalvo() {
        return new GBDPontoSalvo( this );
    }
    
    
    public Connection getConexao() {
        return c;
    }
    
    public void fechaConexao() throws GBDException {
        try {
            if ( c != null )
                if( !c.isClosed() )
                    c.close();
        } catch (SQLException e) {
            throw new GBDException( "DB - Não foi possível fechar a conexão.", e );
        }
    }
        
    public GBDConfig getGBDConfig() {
        return cfg;
    }
          
}
