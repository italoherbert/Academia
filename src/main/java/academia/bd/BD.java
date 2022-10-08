package academia.bd;

import academia.bd.bo.PagamentoBO;
import academia.bd.bo.PagamentoBODriver;
import academia.bd.dao.util.DAOUtil;
import academia.bd.dao.AlunoDAO;
import academia.bd.dao.ConfigDAO;
import academia.bd.dao.DescontoDAO;
import academia.bd.dao.MatriculaDAO;
import academia.bd.dao.MedidasDAO;
import academia.bd.dao.MensalidadeDAO;
import academia.bd.dao.ModalidadeDAO;
import academia.bd.dao.PagamentoDAO;
import academia.bd.dao.UsuarioDAO;
import libs.bd.GBD;
import libs.bd.GBDArquivoConfig;
import libs.bd.GBDConfig;
import libs.bd.GBDException;

public class BD {
    
    private GBD gbd = new GBD();
    private DAOUtil daoUtil = new DAOUtil();
    
    private AlunoDAO alunoDAO = new AlunoDAO( gbd, daoUtil );
    private MatriculaDAO matDAO = new MatriculaDAO( gbd, daoUtil );        
    private PagamentoDAO pagDAO = new PagamentoDAO( gbd, daoUtil );
    private ConfigDAO configDAO = new ConfigDAO( gbd, daoUtil );
    private ModalidadeDAO modalidadeDAO = new ModalidadeDAO( gbd, daoUtil );
    private MensalidadeDAO mensalidadeDAO = new MensalidadeDAO( gbd, daoUtil );                    
    private MedidasDAO medidasDAO = new MedidasDAO( gbd, daoUtil );
    private DescontoDAO descontoDAO = new DescontoDAO( gbd, daoUtil );
    private UsuarioDAO usuarioDAO = new UsuarioDAO( gbd, daoUtil );
            
    private PagamentoBO pagBO;
    
    public BD( PagamentoBODriver drv ) {
        pagBO = new PagamentoBO( drv, pagDAO, matDAO, modalidadeDAO, configDAO );
    }
    
    public void inicializa( GBDConfig cfg ) throws GBDException {
        gbd.abreConexao( cfg ); 
    }
    
    public void inicializa( GBDArquivoConfig cfg ) throws GBDException {
        gbd.abreConexao( cfg ); 
    }
    
    public void finaliza() throws GBDException {
        gbd.fechaConexao();
    }
    
    public PagamentoBO getPagamentoBO() {
        return pagBO;
    }
    
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }
    
    public AlunoDAO getAlunoDAO() {
        return alunoDAO;
    }
    
    public MatriculaDAO getMatriculaDAO() {
        return matDAO;
    }
    
    public PagamentoDAO getPagamentoDAO() {
        return pagDAO;
    }
    
    public ModalidadeDAO getModalidadeDAO() {
        return modalidadeDAO;
    }
    
    public MedidasDAO getMedidasDAO() {
        return medidasDAO;
    }
    
    public DescontoDAO getDescontoDAO() {
        return descontoDAO;
    }
    
    public MensalidadeDAO getMensalidadeDAO() {
        return mensalidadeDAO;
    }
    
    public ConfigDAO getConfigDAO() {
        return configDAO;
    }
    
    public GBD getGBD() {
        return gbd;
    }

    public DAOUtil getDAOUtil() {
        return daoUtil;
    }
    
}
