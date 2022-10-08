package academia.bd.dao;

import academia.bd.dao.util.DAOPontoSalvo;
import academia.bd.dao.util.DAOUtil;
import academia.bd.to.AlunoTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import libs.bd.GBD;
import academia.bd.BDConsts;
import academia.bd.bean.AlunoBean;
import academia.bd.to.MatriculaTO;

public class AlunoDAO {        
    
    private GBD gbd;
    private DAOUtil util;
    
    public AlunoDAO( GBD gbd, DAOUtil util ) {
        this.gbd = gbd;
        this.util = util;
    }
    
    public int insere( AlunoBean a ) throws DAOException {                    
        DAOPontoSalvo ptSalvo = new DAOPontoSalvo( gbd );
        
        PreparedStatement selIDsPS = null;
        PreparedStatement insAlunoPS = null;
        PreparedStatement insMatPS = null;
        
        PreparedStatement insDescontoPS = null;
        PreparedStatement insMedidasPS = null;
        List<PreparedStatement> insModsPS = new ArrayList();
        try {               
            Connection c = gbd.getConexao();
            ptSalvo.salvaPonto();
            
            Timestamp dataMat;
            if ( a.getDataMatricula() == null ) {
                dataMat = new Timestamp( System.currentTimeMillis() ); 
            } else {
                dataMat = a.getDataMatricula();
            }
            
            Timestamp dataDiaPag;
            if ( a.getDataDiaPag() == null ) {
                dataDiaPag = new Timestamp( System.currentTimeMillis() );
            } else {
                dataDiaPag = a.getDataDiaPag();
            }
            
            // seleciona IDs            
            selIDsPS = c.prepareStatement( 
                "select nextval( 'aluno_seq' ) as aluno_id, "
                     + "nextval( 'matricula_seq' ) as mat_id" 
            );
            ResultSet selIDsRS = selIDsPS.executeQuery();
            selIDsRS.next();
            int alunoID = selIDsRS.getInt( "aluno_id" );
            int matID = selIDsRS.getInt( "mat_id" );
            
            
            // insere aluno            
            insAlunoPS = c.prepareStatement( 
                "insert into aluno ( "
                  + "id, matricula_corrente, nome, data_nasc, obs"
              + " ) values ( ?, ?, ?, ?, ? )" 
            );             
            insAlunoPS.setInt( 1, alunoID ); 
            insAlunoPS.setInt( 2, matID );
            insAlunoPS.setString( 3, a.getNome() );
            insAlunoPS.setTimestamp( 4, a.getDataNasc() ); 
            insAlunoPS.setString( 5, a.getObs() ); 
            insAlunoPS.executeUpdate();                                   
                        
            
            // insere matricula            
            insMatPS = c.prepareStatement( 
                "insert into matricula ( id, aluno_id, data_inicio, data_dia_pag ) values ( ?, ?, ?, ? )" 
            );
            insMatPS.setInt( 1, matID );
            insMatPS.setInt( 2, alunoID ); 
            insMatPS.setTimestamp( 3, dataMat );                        
            insMatPS.setTimestamp( 4, dataDiaPag );
            insMatPS.executeUpdate();                 
            
            // insere desconto
            
            insDescontoPS = c.prepareStatement( 
                "insert into desconto ( mat_id, porcentagem, data_alter ) values ( ?, ?, ? )" 
            );
            insDescontoPS.setInt( 1, matID );
            insDescontoPS.setDouble( 2, a.getDesconto() );
            insDescontoPS.setTimestamp( 3, dataMat );
            insDescontoPS.executeUpdate();

            // insere medidas
            if ( a.getMedidas() != null ) {                
                insMedidasPS = c.prepareStatement( 
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
                insMedidasPS.setInt( 1, matID ); 
                insMedidasPS.setInt( 2, a.getMedidas().getPeso() );
                insMedidasPS.setDouble( 3, a.getMedidas().getAltura() );
                insMedidasPS.setInt( 4, a.getMedidas().getBracoEsquerdo() );
                insMedidasPS.setInt( 5, a.getMedidas().getBracoDireito() );
                insMedidasPS.setInt( 6, a.getMedidas().getAntebracoEsquerdo() );
                insMedidasPS.setInt( 7, a.getMedidas().getAntebracoDireito() ); 
                insMedidasPS.setInt( 8, a.getMedidas().getTorax() );
                insMedidasPS.setInt( 9, a.getMedidas().getCintura() );
                insMedidasPS.setInt( 10, a.getMedidas().getBumbum() );
                insMedidasPS.setInt( 11, a.getMedidas().getCoxaEsquerda() );
                insMedidasPS.setInt( 12, a.getMedidas().getCoxaDireita() );
                insMedidasPS.setInt( 13, a.getMedidas().getPanturrilhaEsquerda() );
                insMedidasPS.setInt( 14, a.getMedidas().getPanturrilhaDireita() );
                insMedidasPS.setTimestamp( 15, dataMat );                 
                insMedidasPS.executeUpdate();
            }
            
            // insere modalidades
            if ( a.getModalidadeIDs() != null ) {                
                List<Integer> modIDs = a.getModalidadeIDs();
                for( int id : modIDs ) {                                        
                    PreparedStatement ps = c.prepareStatement( 
                        "insert into matmod ( mat_id, mod_id, data_contrato ) values ( ?, ?, ? )" 
                    );
                    ps.setInt( 1, matID ); 
                    ps.setInt( 2, id );                    
                    ps.setTimestamp( 3, dataMat ); 
                    ps.executeUpdate();
                    
                    insModsPS.add( ps );                    
                }                                    
            }                        
            
            ptSalvo.commit();            
            
            return alunoID;
        } catch ( SQLException e ) {
            ptSalvo.rollback();
            throw new DAOException( e );
        } finally {
            util.fechaST( selIDsPS ); 
            util.fechaST( insAlunoPS ); 
            util.fechaST( insMatPS );
            
            util.fechaST( insMedidasPS );
            for( PreparedStatement ps : insModsPS )
                util.fechaST( ps );
            
            ptSalvo.finaliza();
        }
    }
    
    public void atualiza( AlunoTO a ) throws DAOException {
        PreparedStatement ps = null;
        
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "update aluno set "
                  + "nome=?, data_nasc=?, obs=? "
              + "where id=?" 
            );

            ps.setString( 1, a.getNome() );
            ps.setTimestamp( 2, a.getDataNasc() );
            ps.setString( 3, a.getObs() ); 
            ps.setInt( 4, a.getID() ); 

            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }                                       
    
    public AlunoTO busca( int alunoID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * from aluno where id=?" 
            );
            ps.setInt( 1, alunoID );
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() ) {
                AlunoTO a = new AlunoTO();
                a.setID( rs.getInt( "id" ) );
                a.setNome( rs.getString( "nome" ) ); 
                a.setDataNasc( rs.getTimestamp( "data_nasc" ) );
                a.setObs( rs.getString( "obs" ) );
                a.setMatriculaCorrente( rs.getInt( "matricula_corrente" ) );
                return a;
            }
            return null;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public String buscaNome( int alunoID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select nome from aluno where id=?" 
            );
            ps.setInt( 1, alunoID );
            
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
    
    public List<AlunoTO> buscaTodos( boolean mostrarInativos, int diariaAlID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            String criterioMI = "true";
            if ( !mostrarInativos ) {
                criterioMI = "matricula_corrente<>"+BDConsts.ID_NULO;
            }
            
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * "
              + "from aluno "
              + "where id<>? and " + criterioMI + " "
              + "order by ( nome )" 
            );            
            ps.setInt( 1, diariaAlID );
            
            ResultSet rs = ps.executeQuery();
            
            List<AlunoTO> lista = new ArrayList<AlunoTO>();
            while( rs.next() ) {
                AlunoTO a = new AlunoTO();
                a.setID( rs.getInt( "id" ) );
                a.setNome( rs.getString( "nome" ) ); 
                a.setDataNasc( rs.getTimestamp( "data_nasc" ) ); 
                a.setObs( rs.getString( "obs" ) );
                a.setMatriculaCorrente( rs.getInt( "matricula_corrente" ) );
                
                lista.add( a );
            }
            return lista;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }        
    
    public List<AlunoTO> filtra( String nome, boolean mostrarInativos, int diariaAlID ) throws DAOException {
        PreparedStatement ps = null;
        try {   
            String criterioMI = "true";
            if ( !mostrarInativos ) {
                criterioMI = "matricula_corrente<>"+BDConsts.ID_NULO;
            }
            
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * "
              + "from aluno "
              + "where id<>? and nome ilike ? and " + criterioMI + " "
              + "order by( nome )"
            );
            ps.setInt( 1, diariaAlID ); 
            ps.setString( 2, nome+"%" );
            
            ResultSet rs = ps.executeQuery();
            
            List<AlunoTO> lista = new ArrayList<AlunoTO>();
            while( rs.next() ) {
                AlunoTO a = new AlunoTO();
                a.setID( rs.getInt( "id" ) );
                a.setDataNasc( rs.getTimestamp( "data_nasc" ) );
                a.setNome( rs.getString( "nome" ) ); 
                a.setObs( rs.getString( "obs" ) );
                a.setMatriculaCorrente( rs.getInt( "matricula_corrente" ) );
                                
                lista.add( a );
            }            
            return lista;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );            
        }
    }
    
    public boolean existePorNome( String nome ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            
            ps = c.prepareStatement( 
                "select id from aluno where lower(nome)=lower(?) limit 1" 
            );
            ps.setString( 1, nome );
            
            ResultSet rs = ps.executeQuery();
            return rs.next();            
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public boolean verificarSeMatAtiva( int alunoID ) throws DAOException {
        PreparedStatement selMatIDPS = null;
        try {                                    
            Connection c = gbd.getConexao();            
            
            // seleciona ID da matricula corrente do aluno
            selMatIDPS = c.prepareStatement( 
                "select matricula_corrente from aluno where id=?" 
            );
            selMatIDPS.setInt( 1, alunoID );             
            ResultSet rs = selMatIDPS.executeQuery();
            
            int currMatID = BDConsts.ID_NULO;
            if ( rs.next() )
                currMatID = rs.getInt( "matricula_corrente" );

            return ( currMatID != BDConsts.ID_NULO );    
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            
        }
            
    }
        
    public void encerraMat( int alunoID ) throws DAOException {
        DAOPontoSalvo ptSalvo = new DAOPontoSalvo( gbd );
        
        PreparedStatement selMatIDPS = null;
        PreparedStatement atCurrMatPS = null;
        PreparedStatement atAlunoMatCorrPS = null;
        try {                                    
            Connection c = gbd.getConexao();            
            ptSalvo.salvaPonto();

            int currMatID = BDConsts.ID_NULO;            
            
            // seleciona ID da matricula corrente do aluno
            selMatIDPS = c.prepareStatement( 
                "select matricula_corrente from aluno where id=?" 
            );
            selMatIDPS.setInt( 1, alunoID );             
            ResultSet rs = selMatIDPS.executeQuery();
            if ( rs.next() )
                currMatID = rs.getInt( "matricula_corrente" );            
            
            if ( currMatID != BDConsts.ID_NULO ) {
                // atualiza matricula corrente do aluno
                atCurrMatPS = c.prepareStatement( 
                    "update matricula set "
                      + "data_fim=? "
                  + "where id=?" 
                );
                atCurrMatPS.setTimestamp( 1, new Timestamp( System.currentTimeMillis() ) );
                atCurrMatPS.setInt( 2, currMatID );
                atCurrMatPS.executeUpdate();


                // atualiza aluno com ID de matricula encerrada
                atAlunoMatCorrPS = c.prepareStatement( 
                    "update aluno set "
                      + "matricula_corrente=? "
                  + "where id=?" 
                );
                atAlunoMatCorrPS.setInt( 1, BDConsts.ID_NULO ); 
                atAlunoMatCorrPS.setInt( 2, alunoID );
                atAlunoMatCorrPS.executeUpdate();            
            }
            
            ptSalvo.commit();            
        } catch ( SQLException e ) {
            ptSalvo.rollback();
            
            throw new DAOException( e );
        } finally {
            util.fechaST( selMatIDPS );
            util.fechaST( atCurrMatPS ); 
            
            ptSalvo.finaliza();
        }
    }
    
    public void novaMat( int alunoID, Timestamp dataMat, Timestamp dataDiaPag, boolean copiarMods ) throws DAOException {
        DAOPontoSalvo ptSalvo = new DAOPontoSalvo( gbd );
        
        PreparedStatement encerraMatPS = null;
                
        PreparedStatement selMatIDPS = null;
        PreparedStatement selMatIDPS2 = null;
        PreparedStatement selModIDsPS = null;
        
        List<PreparedStatement> insModsPSs = new ArrayList();
                
        PreparedStatement insNovaMatPS = null;
        PreparedStatement atAlunoMatCorrPS = null;
        
        try {
            Connection c = gbd.getConexao();            
            ptSalvo.salvaPonto();
            
            int currMatID = BDConsts.ID_NULO;            
            
            // seleciona ID da matricula corrente do aluno
            selMatIDPS = c.prepareStatement( 
                "select matricula_corrente from aluno where id=?" 
            );
            selMatIDPS.setInt( 1, alunoID ); 
            
            ResultSet selMatIDRS = selMatIDPS.executeQuery();
            if ( selMatIDRS.next() )
                currMatID = selMatIDRS.getInt( "matricula_corrente" );

            
            if ( currMatID != BDConsts.ID_NULO ) {                                
                // finaliza matricula corrente do aluno
                encerraMatPS = c.prepareStatement( 
                    "update matricula set "
                      + "data_fim=current_timestamp "
                  + "where id=?" 
                );
                encerraMatPS.setInt( 1, currMatID );
                encerraMatPS.executeUpdate();                                              
            }
                
            // insere nova matricula          
            insNovaMatPS = c.prepareStatement( 
                "insert into matricula ( aluno_id, data_inicio, data_dia_pag ) values ( ?, ?, ? )" 
            );
            insNovaMatPS.setInt( 1, alunoID );
            insNovaMatPS.setTimestamp( 2, dataMat );
            insNovaMatPS.setTimestamp( 3, dataDiaPag );
            insNovaMatPS.executeUpdate();                                     
            
            // atualiza aluno com ID da nova matricula
            atAlunoMatCorrPS = c.prepareStatement( 
                "update aluno set "
                  + "matricula_corrente=currval( 'matricula_seq' ) "
              + "where id=?" 
            );
            atAlunoMatCorrPS.setInt( 1, alunoID ); 
            atAlunoMatCorrPS.executeUpdate();            
            
            
            // Copia as modalidades da matricula antiga para a nova
            if ( copiarMods && currMatID != BDConsts.ID_NULO ) {                   
                selModIDsPS = c.prepareStatement( 
                    "select m.id "
                  + "from matmod mm inner join modalidade m on m.id=mm.mod_id "
                  + "where mm.mat_id=? and mm.data_encerramento is null and m.data_fim is null" 
                );
                selModIDsPS.setInt( 1, currMatID );

                ResultSet selModIDsRS = selModIDsPS.executeQuery();
                while( selModIDsRS.next() ) {
                    int modID = selModIDsRS.getInt( "id" );

                    PreparedStatement insModPS = c.prepareStatement( 
                        "insert into matmod ( mat_id, mod_id, data_contrato ) values ( currval( 'matricula_seq' ), ?, ? )" 
                    );
                    insModPS.setInt( 1, modID );
                    insModPS.setTimestamp( 2, dataMat ); 
                    insModPS.executeUpdate();
                    
                    insModsPSs.add( insModPS );
                }
            } 
            
            ptSalvo.commit();
        } catch ( SQLException e ) {
            ptSalvo.rollback();
            
            throw new DAOException( e );
        } finally {            
            util.fechaST( selMatIDPS );
            util.fechaST( selMatIDPS2 ); 
            util.fechaST( selModIDsPS ); 
                        
            for( PreparedStatement ps : insModsPSs ) 
                util.fechaST( ps ); 
            
            util.fechaST( insNovaMatPS );
            util.fechaST( atAlunoMatCorrPS );                         
            
            util.fechaST( encerraMatPS );
            
            ptSalvo.finaliza();
        }
    }
    
    public int buscaMatCorr( int alunoID ) throws DAOException {
        PreparedStatement ps = null;
        try {            
            Connection c = gbd.getConexao();
            
            ps = c.prepareStatement( 
                "select matricula_corrente from aluno where id=?" 
            );
            ps.setInt( 1, alunoID ); 
            
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return rs.getInt( "matricula_corrente" );               
            
            return BDConsts.ID_NULO;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
    
    public List<MatriculaTO> buscaMats( int alunoID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select id, data_inicio, data_fim "
              + "from matricula "
              + "where aluno_id=? "
              + "order by ( data_inicio ) desc" 
            );
            ps.setInt( 1, alunoID );
            ResultSet rs = ps.executeQuery();
            
            List<MatriculaTO> mats = new ArrayList();
            while( rs.next() ) {
                MatriculaTO mat = new MatriculaTO();
                mat.setID( rs.getInt( "id" ) );
                mat.setAlunoID( alunoID );
                mat.setDataInicio( rs.getTimestamp( "data_inicio" ) );
                mat.setDataFim( rs.getTimestamp( "data_fim" ) );
                
                mats.add( mat );
            }
            return mats;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public int quantAlunosCompletandoAno() throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select count(*) as quant "
              + "from aluno "
              + "where date_part( 'day', data_nasc )=date_part( 'day', current_timestamp ) and "
                    + "date_part( 'month', data_nasc )=date_part( 'month', current_timestamp )" 
            );
            ResultSet rs = ps.executeQuery();
            if ( rs.next() )
                return rs.getInt( "quant" );
            
            return 0;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public List<AlunoTO> buscaAlunosCompletandoAno() throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "select * "
              + "from aluno "
              + "where date_part( 'day', data_nasc )=date_part( 'day', current_timestamp ) and "
                    + "date_part( 'month', data_nasc )=date_part( 'month', current_timestamp )"
            );
            
            List<AlunoTO> alunos = new ArrayList();
            
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                AlunoTO a = new AlunoTO();
                a.setID( rs.getInt( "id" ) );
                a.setNome( rs.getString( "nome" ) );
                a.setDataNasc( rs.getTimestamp( "data_nasc" ) );
                a.setObs( rs.getString( "obs" ) );
                a.setMatriculaCorrente( rs.getInt( "matricula_corrente" ) );
                
                alunos.add( a );
            }
            return alunos;
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps );
        }
    }
    
    public void remove( int alunoID ) throws DAOException {
        PreparedStatement ps = null;
        try {
            Connection c = gbd.getConexao();
            ps = c.prepareStatement( 
                "delete from aluno where id=?" 
            );
            ps.setInt( 1, alunoID );
            
            ps.executeUpdate();
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            util.fechaST( ps ); 
        }
    }
        
}
