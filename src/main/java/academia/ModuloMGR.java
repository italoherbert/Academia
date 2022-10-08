package academia;

import academia.bd.BD;
import academia.bd.bo.PagamentoBODriver;
import academia.bd.to.BDConfigTO;
import academia.bd.to.UsuarioTO;
import academia.config.Config;
import academia.controlador.ControladorTO;
import academia.controlador.SistemaCtrlUtil;
import academia.controlador.VisualizacaoMGR;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.controlador.aluno.AlunoSessao;
import academia.controlador.matricula.MatriculaSessao;
import academia.controlador.matricula.MatriculaCtrlUtil;
import academia.controlador.mensalidade.MensalidadeCtrlUtil;
import academia.controlador.modalidade.ModSessao;
import academia.controlador.modalidade.ModalidadeCtrlUtil;
import academia.controlador.pag.PagCtrlUtil;
import academia.bd.to.parcela.ParcelasBean;
import academia.controlador.pag.PagSessao;
import academia.controlador.usuario.UsuarioCtrlUtil;
import academia.gui.GUI;
import academia.relatorio.GerenciadorRelatorio;
import academia.relatorio.RelatorioTO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.ModalidadeFormatador;
import academia.util.NumeroFormatador;
import academia.util.PagamentoUtil;
import java.util.Date;

public class ModuloMGR implements ControladorTO, RelatorioTO, PagamentoBODriver {
    
    private Config config = new Config();
    private BD bd = new BD( this );   
            
    private MSGUtil msgUtil = new MSGUtil();                
    private PagamentoUtil pagamentoUtil = new PagamentoUtil();                
    private NumeroFormatador nf = new NumeroFormatador();                
    private DataUtil df = new DataUtil();                
    private ModalidadeFormatador mf = new ModalidadeFormatador();
    
    private AlunoCtrlUtil alunoCUtil = new AlunoCtrlUtil( this );
    private MatriculaCtrlUtil matCUtil = new MatriculaCtrlUtil( this );
    private ModalidadeCtrlUtil modCUtil = new ModalidadeCtrlUtil( this );
    private MensalidadeCtrlUtil mensCUtil = new MensalidadeCtrlUtil( this );
    private PagCtrlUtil pagCUtil = new PagCtrlUtil( this );
    private UsuarioCtrlUtil usuarioCUtil = new UsuarioCtrlUtil( this );
    private SistemaCtrlUtil sistemaCtrlUtil = new SistemaCtrlUtil( this );    
    
    private VisualizacaoMGR visuMGR = new VisualizacaoMGR( this );
    private GerenciadorRelatorio gerenciadorRelatiorio = new GerenciadorRelatorio( this );     
    
    private AlunoSessao alunoSessao = new AlunoSessao();
    private PagSessao pagSessao = new PagSessao();
    private ModSessao modSessao = new ModSessao();
    private MatriculaSessao lstMatsSessao = new MatriculaSessao();
    
    private UsuarioTO usuarioLogado = new UsuarioTO();
    private BDConfigTO bdConfig;

    private GUI gui;
    
    public void inicializa() throws Exception {
        config.carrega();
        bd.inicializa( config );                 
        
        bdConfig = bd.getConfigDAO().busca();
        
        gui = new GUI( config );
    }

    public Date apenasData( Date data ) {
        return df.apenasData( data );
    }
    
    public Config getConfig() {
        System.out.println( config.getURL() );
        return config;
    }
        
    public BD getBD() {
        return bd;
    }
    
    public GUI getGUI() {
        return gui;
    }
    
    public GerenciadorRelatorio getGerenciadorRelatorio() {
        return gerenciadorRelatiorio;
    }

    public VisualizacaoMGR getVisuMGR() {
        return visuMGR;
    }
    
    public MSGUtil getMSGUtil() {
        return msgUtil;
    }

    public NumeroFormatador getNumeroFormatador() {
        return nf;
    }

    public DataUtil getDataUtil() {
        return df;
    }        
    
    public ModalidadeFormatador getModalidadeFormatador() {
        return mf;
    }
    
    public PagamentoUtil getPGUtil() {
        return pagamentoUtil;
    }  
    
    // utilitarios de controladores
    
    public SistemaCtrlUtil getSistemaCtrlUtil() {
        return sistemaCtrlUtil;
    }
    
    public AlunoCtrlUtil getAlunoCUtil() {
        return alunoCUtil;
    }
    
    public MatriculaCtrlUtil getMatCUtil() {
        return matCUtil;
    }
    
    public ModalidadeCtrlUtil getModCUtil() {
        return modCUtil;
    }
    
    public MensalidadeCtrlUtil getMensCUtil() {
        return mensCUtil;
    }
    
    public PagCtrlUtil getPagCUtil() {
        return pagCUtil;
    }
    
    public UsuarioCtrlUtil getUsuarioCUtil() {
        return usuarioCUtil;
    }

    // objetos de sessão
    
    public UsuarioTO getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public BDConfigTO getBDConfig() {
        return bdConfig;
    }
    
    public AlunoSessao getAlunoSessao() {
        return alunoSessao;
    }
    
    public PagSessao getPagSessao() {
        return pagSessao;
    }
    
    public ModSessao getModSessao() {
        return modSessao;
    }
    
    public MatriculaSessao getMatSessao() {
        return lstMatsSessao;
    }
    
    public void setUsuarioLogado( UsuarioTO usuario ) {
        this.usuarioLogado = usuario;
    }
    
}
