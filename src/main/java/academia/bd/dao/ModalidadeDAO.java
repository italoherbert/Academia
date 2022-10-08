package academia.bd.dao;

import academia.bd.BDConsts;
import academia.bd.to.MensalidadeTO;
import academia.bd.to.ModalidadeTO;
import academia.bd.dao.util.DAOPontoSalvo;
import academia.bd.dao.util.DAOUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import libs.bd.GBD;

public class ModalidadeDAO {
    
    private GBD gbd;
    private DAOUtil util;

    public ModalidadeDAO(GBD gbd, DAOUtil util) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public void insere( ModalidadeTO mod ) throws DAOException {
        PreparedStatement insModPS = null;
        PreparedStatement insMensPS = null;
        
        DAOPontoSalvo pontoSalvo = new DAOPontoSalvo( gbd ); 
        try {
            Connection c = gbd.getConexao();
            pontoSalvo.salvaPonto();
            
            Timestamp dataInicio;
            if ( mod.getDataInicio() == null )
                dataInicio = new Timestamp( System.currentTimeMillis() );
            else dataInicio = mod.getDataInicio();             
            
            // INSERE MODALIDADE            
            insModPS = c.prepareStatement( 
                "insert into modalidade ( descricao, valor_inicial, valor_diaria, data_inicio ) values ( ?, ?, ?, ? ) "                                 
            );
            insModPS.setString( 1, mod.getDescricao() ); 
            insModPS.setDouble( 2, mod.getValorInicial() ); 
            insModPS.setDouble( 3, mod.getValorDiaria() );
            insModPS.setTimestamp( 4, dataInicio );                        
            insModPS.executeUpdate();
            
            // INSERE MENSALIDADE ADICIONANDO A MODALIDADE
            insMensPS = c.prepareStatement(
                "insert into mensalidade ( mod_id, valor, data_alter ) "
              + "values ( currval( 'modalidade_seq' ), ?, ? )"
            );
            insMensPS.setDouble( 1, mod.getValorInicial() );
            insMensPS.setTimestamp( 2, dataInicio );             
            insMensPS.executeUpdate();
            
            pontoSalvo.commit();
        } catch ( SQLException e ) {
            pontoSalvo.rollback();
            
            throw new DAOException( e );
        } finally {
            util.fechaST( insModPS );
            
            pontoSalvo.finaliza();
        }
    }
    
    public void atualiza( ModalidadeTO mod ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update modalidade set "
                  + "descricao=?, data_inicio=?, valor_diaria=? "
              + "where id=?"
            );
            ps.setString( 1, mod.getDescricao() ); 
            
            if ( mod.getDataInicio() == null )
                ps.setTimestamp( 2, new Timestamp( System.currentTimeMillis() ) );
            else ps.setTimestamp( 2, mod.getDataInicio() ); 
            
            ps.setDouble( 3, mod.getValorDiaria() ); 
            ps.setInt( 4, mod.getID() );
                        
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
            
    public void adicionaModalidadeAMatricula( int matID, int modID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "insert into matmod ( mat_id, mod_id ) values ( ?, ? )" 
            );
            ps.setInt( 1, matID );
            ps.setInt( 2, modID );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public void encerraModalidadeDoAluno( int mmID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update matmod set "
                  + "data_encerramento=current_timestamp "
              + "where id=?" 
            );
            ps.setInt( 1, mmID );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }           
    }    
    
    public ModalidadeTO busca( int modID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from modalidade where id=?" 
            );
            ps.setInt( 1, modID );
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return this.carregaModalidade( rs );             
       
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public List<ModalidadeTO> filtra( String descricao ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from modalidade where descricao ilike ? order by( id )" 
            );
            ps.setString( 1, descricao+"%" );
            
            ResultSet rs = ps.executeQuery();
            
            List<ModalidadeTO> mods = new ArrayList();
            while ( rs.next() ) {                               
                mods.add( this.carregaModalidade( rs ) );                 
            }
            return mods;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public void finaliza( int modID, String marcInativaRotulo ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            
            ps = c.prepareStatement( 
                "update modalidade set "
                 +  "descricao=descricao || ?, "     
                 +  "data_fim=current_timestamp "
              + "where id=?" 
            );
            ps.setString( 1, marcInativaRotulo );
            ps.setInt( 2, modID );            
            ps.executeUpdate();            
        } catch ( SQLException e ) {            
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );            
        }
    }
        
    public List<ModalidadeTO> buscaTodas() throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from modalidade order by( id )" 
            );
            ResultSet rs = ps.executeQuery();
            
            List<ModalidadeTO> mods = new ArrayList();
            while( rs.next() )                                
                mods.add( this.carregaModalidade( rs ) );                
                      
            return mods;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public List<ModalidadeTO> buscaTodasComDiaria() throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * "
              + "from modalidade "
              + "where data_fim is null and valor_diaria<>?"               
            );
            ps.setDouble( 1, BDConsts.VALOR_DIARIA_NULO ); 
            
            List<ModalidadeTO> mods = new ArrayList();
            
            ResultSet rs = ps.executeQuery();
            while( rs.next() )                
                mods.add( this.carregaModalidade( rs ) );
            
            return mods;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }

    public MensalidadeTO buscaMensalidadeAtual( int modID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select id, valor, data_alter "
              + "from mensalidade "
              + "where mod_id=? "
              + "order by ( data_alter ) desc "
              + "limit 1"                    
            );
            ps.setInt( 1, modID );
                        
            ResultSet rs = ps.executeQuery();
            if( rs.next() ) {
                MensalidadeTO men = new MensalidadeTO();
                men.setID( rs.getInt( "id" ) );
                men.setModID( modID );
                men.setValor( rs.getDouble( "valor" ) );
                men.setDataAlter( rs.getTimestamp( "data_alter" ) );
                        
                return men;
            }
            
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public List<MensalidadeTO> buscaMensalidades( int modID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select id, valor, data_alter "
              + "from mensalidade "
              + "where mod_id=? "
              + "order by ( data_alter ) desc"                    
            );
            ps.setInt( 1, modID );
            
            List<MensalidadeTO> mensalidades = new ArrayList();
            
            ResultSet rs = ps.executeQuery();
            while( rs.next() ) {
                MensalidadeTO men = new MensalidadeTO();
                men.setID( rs.getInt( "id" ) );
                men.setModID( modID );
                men.setValor( rs.getDouble( "valor" ) );
                men.setDataAlter( rs.getTimestamp( "data_alter" ) );
                
                mensalidades.add( men );
            }
            
            return mensalidades;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public boolean existeModalidadeAtivaPorDesc( String desc ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select id "
              + "from modalidade "
              + "where data_fim is null and descricao=?" 
            );
            ps.setString( 1, desc );
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public boolean existeModAdicionadaAoAluno( int modID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select mod_id from matmod where mod_id=?" 
            );
            ps.setInt( 1, modID );
            
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public boolean existeAlgumaModalidade() throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select id from modalidade limit 1" 
            );
            ResultSet rs = ps.executeQuery();
            
            return rs.next();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public void remove( int modID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "delete from modalidade where id=?" 
            );
            ps.setInt( 1, modID );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    private ModalidadeTO carregaModalidade( ResultSet rs ) throws SQLException {
        ModalidadeTO m = new ModalidadeTO();
        m.setID( rs.getInt( "id" ) ); 
        m.setDescricao( rs.getString( "descricao" ) );
        m.setValorInicial( rs.getDouble( "valor_inicial" ) ); 
        m.setValorDiaria( rs.getDouble( "valor_diaria" ) );
        m.setDataInicio( rs.getTimestamp( "data_inicio" ) );
        m.setDataFim( rs.getTimestamp( "data_fim" ) );
        
        return m;

    }
    
}
