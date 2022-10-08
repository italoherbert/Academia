package academia.bd.dao;

import academia.bd.BDConsts;
import academia.bd.bean.DataDiaPagPGQuantBean;
import academia.bd.dao.util.DAOUtil;
import academia.bd.to.MatriculaTO;
import academia.bd.to.MensalidadeTO;
import academia.bd.bean.ModalidadeBean;
import academia.bd.dao.util.DAOPontoSalvo;
import academia.bd.to.DescontoTO;
import academia.bd.to.MedidasTO;
import academia.bd.to.PagamentoTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import libs.bd.GBD;

public class MatriculaDAO {

    private GBD gbd;
    private DAOUtil util;

    public MatriculaDAO(GBD gbd, DAOUtil util) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public MatriculaTO busca( int matID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from matricula where id=?" 
            );
            ps.setInt( 1, matID );
                        
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                MatriculaTO m = new MatriculaTO();
                m.setID( matID );
                m.setAlunoID( rs.getInt( "aluno_id" ) );
                m.setDataInicio( rs.getTimestamp( "data_inicio" ) );
                m.setDataFim( rs.getTimestamp( "data_fim" ) );
                m.setDataDiaPag( rs.getTimestamp( "data_dia_pag" ) ); 
                
                return m;
            }
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public List<ModalidadeBean> buscaModalidades( int matID ) throws DAOException {                
        PreparedStatement modsPS = null;                
        try {            
            Connection c = gbd.getConexao();

            List<ModalidadeBean> modalidades = new ArrayList();
            
            // seleciona modalidades por ID da matricula
            modsPS = c.prepareStatement( 
                "select m.id as mid, mm.id as mmid, m.descricao, m.valor_inicial, m.valor_diaria, m.data_inicio, m.data_fim, "
                        + "mm.data_contrato, mm.data_encerramento "
              + "from modalidade m inner join matmod mm on m.id=mm.mod_id "
              + "where mm.mat_id=? "
              + "order by ( mm.data_contrato )"    
            );
            modsPS.setInt( 1, matID );
            
            ResultSet modsRS = modsPS.executeQuery();
            while( modsRS.next() ) {
                int modID = modsRS.getInt( "mid" );
                
                ModalidadeBean mod = new ModalidadeBean();
                mod.setID( modID );
                mod.setMatID( matID );
                mod.setModID( modID );
                mod.setMatModID( modsRS.getInt( "mmid" ) ); 
                mod.setDescricao( modsRS.getString( "descricao" ) );
                mod.setValorInicial( modsRS.getDouble( "valor_inicial" ) );
                mod.setValorDiaria( modsRS.getDouble( "valor_diaria" ) );
                mod.setDataInicio( modsRS.getTimestamp( "data_inicio" ) );
                mod.setDataFim( modsRS.getTimestamp( "data_fim" ) );
                
                mod.setDataContrato( modsRS.getTimestamp( "data_contrato" ) );
                mod.setDataEncerramento( modsRS.getTimestamp( "data_encerramento" ) );
                
                                
                modalidades.add( mod );
            }
            
            for( ModalidadeBean mod : modalidades ) {
                PreparedStatement mensPS = null;
                                
                int modID = mod.getID();
                List<MensalidadeTO> mens = new ArrayList();
                try {                        
                    // seleciona mensalidades por ID da modalidade                    
                    mensPS = c.prepareStatement( 
                        "select id, valor, data_alter "
                      + "from mensalidade "
                      + "where mod_id=? "
                      + "order by ( data_alter ) asc"
                    );
                    mensPS.setInt( 1, modID );
                    ResultSet mensRS = mensPS.executeQuery();
                    while( mensRS.next() ) {
                        MensalidadeTO m = new MensalidadeTO();
                        m.setID( mensRS.getInt( "id" ) );
                        m.setModID( modID );
                        m.setValor( mensRS.getDouble( "valor" ) );
                        m.setDataAlter( mensRS.getTimestamp( "data_alter" ) ); 
                        
                        mens.add( m );
                    }                                                            
                } catch ( SQLException e ) {
                    throw new DAOException( e );
                } finally {
                    util.fechaST( mensPS );
                }
                
                mod.setMensalidades( mens ); 
            }                                    
            
            return modalidades;
        } catch ( SQLException e ) {            
            throw new DAOException( e );
        } finally {
            util.fechaST( modsPS );             
        }
    }        
    
    public List<DescontoTO> buscaDescontos( int matID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * "
              + "from desconto "
              + "where mat_id=? "
              + "order by ( data_alter )" 
            );
            ps.setInt( 1, matID );
            
            ResultSet rs = ps.executeQuery();
            
            List<DescontoTO> descontos = new ArrayList();
            while( rs.next() ) {
                DescontoTO d = new DescontoTO();
                d.setID( rs.getInt( "id" ) );
                d.setMatID( matID );
                d.setPorcentagem( rs.getDouble( "porcentagem" ) );
                d.setDataAlter( rs.getTimestamp( "data_alter" ) );
                
                descontos.add( d );
            }
            return descontos;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public List<PagamentoTO> buscaPagamentos( int matID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select id, usuario_id, valor, desconto, data_pag "
              + "from pagamento "
              + "where mat_id=?" 
            );
            ps.setInt( 1, matID );
            
            ResultSet rs = ps.executeQuery();
            
            List<PagamentoTO> pags = new ArrayList();
            while( rs.next() ) {
                PagamentoTO p = new PagamentoTO();
                p.setID( rs.getInt( "id" ) );
                p.setUsuarioID( rs.getInt( "usuario_id" ) ); 
                p.setMatID( matID );
                p.setValor( rs.getDouble( "valor" ) );
                p.setDesconto( rs.getDouble( "desconto" ) );
                p.setDataPag( rs.getTimestamp( "data_pag" ) ); 
                
                pags.add( p ); 
            }
            return pags;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public List<MedidasTO> buscaMedidas( int matID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from medidas where mat_id=? order by ( id ) desc" 
            );
            ps.setInt( 1, matID );
            
            ResultSet rs = ps.executeQuery();
    
            List<MedidasTO> listaMedidas = new ArrayList();
            while ( rs.next() ) {
                MedidasTO meds = new MedidasTO();                
                meds.setID( rs.getInt( "id" ) );
                meds.setMatID( matID );
                meds.setPeso( rs.getInt( "peso" ) );
                meds.setAltura( rs.getDouble( "altura" ) );
                
                meds.setBracoEsquerdo( rs.getInt( "braco_esquerdo" ) );
                meds.setBracoDireito( rs.getInt( "braco_direito" ) );
                meds.setAntebracoEsquerdo( rs.getInt( "antebraco_esquerdo") );
                meds.setAntebracoDireito( rs.getInt( "antebraco_direito" ) );
                
                meds.setTorax( rs.getInt( "torax" ) );
                meds.setCintura( rs.getInt( "cintura" ) );
                meds.setBumbum( rs.getInt( "bumbum" ) );
                
                meds.setCoxaEsquerda( rs.getInt( "coxa_esquerda" ) );
                meds.setCoxaDireita( rs.getInt( "coxa_direita" ) );                 
                meds.setPanturrilhaEsquerda( rs.getInt( "panturrilha_esquerda" ) );
                meds.setPanturrilhaDireita( rs.getInt( "panturrilha_direita" ) ); 
                
                meds.setDataReg( rs.getTimestamp( "data_reg" ) );
                
                listaMedidas.add( meds );
            }
            return listaMedidas;
        } catch ( SQLException e ) {
            throw new DAOException();
        } finally {
            util.fechaST( ps );
        }
    }
    
    public String buscaNomeAluno( int matID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select a.nome "
              + "from aluno a inner join matricula m on m.aluno_id=a.id "
              + "where m.id=?" 
            );            
            ps.setInt( 1, matID );
            
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
    
    
    public double buscaDescontoAtual( int matID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select porcentagem "
              + "from desconto "
              + "where mat_id=? "
              + "order by ( id ) desc "
              + "limit 1" 
            );
            ps.setInt( 1, matID );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return rs.getDouble( "porcentagem" );            
            return 0;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public DataDiaPagPGQuantBean buscaDataDiaPagPGQuant( int matID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select data_dia_pag, " +    
                       "( select count(*) from pagamento where mat_id=? ) as quant "
              + "from matricula "
              + "where id=?" 
            );            
            ps.setInt( 1, matID );
            ps.setInt( 2, matID ); 
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                DataDiaPagPGQuantBean mpg = new DataDiaPagPGQuantBean();
                mpg.setDataDiaPag( rs.getTimestamp( "data_dia_pag" ) );
                mpg.setQuantPagamentos( rs.getInt( "quant" ) );
                return mpg;
            }
            
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }        
    }        
    
    public void remove( int matID ) throws DAOException {                
        DAOPontoSalvo ptSalvo = new DAOPontoSalvo( gbd );

        PreparedStatement deleteMatPS = null;
        PreparedStatement selAlunoIDECurrMatIDPS = null;
        PreparedStatement atualizaAlunoMatPS = null;
        try {
            int alunoID = BDConsts.ID_NULO;
            int matCorrID = BDConsts.ID_NULO;                  
            
            Connection c = gbd.getConexao();
            ptSalvo.salvaPonto();
            
            selAlunoIDECurrMatIDPS = c.prepareStatement( 
                "select a.id, a.matricula_corrente "
              + "from aluno a inner join matricula m on m.aluno_id=a.id "
              + "where m.id=?" 
            );
            selAlunoIDECurrMatIDPS.setInt( 1, matID ); 
            
            ResultSet selAlunoIDECurrMatIDRS = selAlunoIDECurrMatIDPS.executeQuery();
            if( selAlunoIDECurrMatIDRS.next() ) {
                alunoID = selAlunoIDECurrMatIDRS.getInt( "id" );
                matCorrID = selAlunoIDECurrMatIDRS.getInt( "matricula_corrente" );
            }
                        
            if ( matCorrID == matID && matCorrID != BDConsts.ID_NULO ) {
                atualizaAlunoMatPS = c.prepareStatement( 
                    "update aluno set "
                      + "matricula_corrente=? "
                  + "where id=?" 
                );
                atualizaAlunoMatPS.setInt(1, BDConsts.ID_NULO );
                atualizaAlunoMatPS.setInt( 2, alunoID );                
                atualizaAlunoMatPS.executeUpdate();
            }
            
            deleteMatPS = c.prepareStatement( 
                "delete from matricula where id=?" 
            );
            deleteMatPS.setInt( 1, matID );            
            deleteMatPS.executeUpdate();
            
            ptSalvo.commit();
        } catch ( SQLException e ) {
            ptSalvo.rollback();
            throw new DAOException( e );
        } finally {
            util.fechaST( selAlunoIDECurrMatIDPS );            
            if ( atualizaAlunoMatPS != null )
                util.fechaST( atualizaAlunoMatPS );             
            util.fechaST( deleteMatPS );
        }
    }
    
}
