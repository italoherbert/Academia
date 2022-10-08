package academia.bd.dao;

import academia.bd.dao.util.DAOUtil;
import academia.bd.to.MedidasTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import libs.bd.GBD;

public class MedidasDAO {
    
    private GBD gbd;
    private DAOUtil util;

    public MedidasDAO(GBD gbd, DAOUtil util) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public void insere( MedidasTO meds ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "insert into medidas ( "
                  + "mat_id, "
                  + "peso, altura, "
                  + "braco_esquerdo, braco_direito, "
                  + "antebraco_esquerdo, antebraco_direito, "
                  + "torax, cintura, bumbum, "
                  + "coxa_esquerda, coxa_direita, "
                  + "panturrilha_esquerda, panturrilha_direita, "
                  + "data_reg "
              + ") values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" 
            );
            ps.setInt( 1, meds.getMatID() ); 
            ps.setInt( 2, meds.getPeso() );
            ps.setDouble( 3, meds.getAltura() );
            ps.setInt( 4, meds.getBracoEsquerdo() );
            ps.setInt( 5, meds.getBracoDireito() );
            ps.setInt( 6, meds.getAntebracoEsquerdo() );
            ps.setInt( 7, meds.getAntebracoDireito() ); 
            ps.setInt( 8, meds.getTorax() );
            ps.setInt( 9, meds.getCintura() );
            ps.setInt( 10, meds.getBumbum() );
            ps.setInt( 11, meds.getCoxaEsquerda() );
            ps.setInt( 12, meds.getCoxaDireita() );
            ps.setInt( 13, meds.getPanturrilhaEsquerda() );
            ps.setInt( 14, meds.getPanturrilhaDireita() );
            ps.setTimestamp( 15, meds.getDataReg() );                 
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public void atualiza( MedidasTO meds ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update medidas set "
                  + "mat_id=?, "
                  + "peso=?, altura=?, "
                  + "braco_esquerdo=?, braco_direito=?, "
                  + "antebraco_esquerdo=?, antebraco_direito=?, "
                  + "torax=?, cintura=?, bumbum=?, "
                  + "coxa_esquerda=?, coxa_direita=?, "
                  + "panturrilha_esquerda=?, panturrilha_direita=?, "
                  + "data_reg=? "
                  + "where id=?"
            );
            ps.setInt( 1, meds.getMatID() ); 
            ps.setInt( 2, meds.getPeso() );
            ps.setDouble( 3, meds.getAltura() );
            ps.setInt( 4, meds.getBracoEsquerdo() );
            ps.setInt( 5, meds.getBracoDireito() );
            ps.setInt( 6, meds.getAntebracoEsquerdo() );
            ps.setInt( 7, meds.getAntebracoDireito() ); 
            ps.setInt( 8, meds.getTorax() );
            ps.setInt( 9, meds.getCintura() );
            ps.setInt( 10, meds.getBumbum() );
            ps.setInt( 11, meds.getCoxaEsquerda() );
            ps.setInt( 12, meds.getCoxaDireita() );
            ps.setInt( 13, meds.getPanturrilhaEsquerda() );
            ps.setInt( 14, meds.getPanturrilhaDireita() );
            ps.setTimestamp( 15, meds.getDataReg() );                 
            ps.setInt( 16, meds.getID() ); 
                    
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public MedidasTO busca( int medidasID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from medidas where id=? limit 1" 
            );
            ps.setInt( 1, medidasID );
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                MedidasTO medsTO = new MedidasTO();
                medsTO.setID( medidasID );
                medsTO.setMatID( rs.getInt( "mat_id" ) );
                medsTO.setPeso( rs.getInt( "peso" ) );
                medsTO.setAltura( rs.getDouble( "altura" ) );
                medsTO.setBracoEsquerdo( rs.getInt( "braco_esquerdo" ) );
                medsTO.setBracoDireito( rs.getInt( "braco_direito" ) );
                medsTO.setAntebracoEsquerdo( rs.getInt( "antebraco_esquerdo" ) );
                medsTO.setAntebracoDireito( rs.getInt( "antebraco_direito" ) );
                medsTO.setTorax( rs.getInt( "torax" ) );
                medsTO.setCintura( rs.getInt( "cintura" ) );
                medsTO.setBumbum( rs.getInt( "bumbum" ) );
                medsTO.setCoxaEsquerda( rs.getInt( "coxa_esquerda" ) );
                medsTO.setCoxaDireita( rs.getInt( "coxa_direita" ) );
                medsTO.setPanturrilhaEsquerda( rs.getInt( "panturrilha_esquerda" ) );
                medsTO.setPanturrilhaDireita( rs.getInt( "panturrilha_direita" ) );
                medsTO.setDataReg( rs.getTimestamp( "data_reg" ) ); 
                return medsTO;
            }
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }

    public void remove( int medidasID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "delete from medidas where id=?" 
            );
            ps.setInt( 1, medidasID );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
}
