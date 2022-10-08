package academia.controlador;

import academia.bd.BD;
import academia.bd.to.BDConfigTO;
import academia.bd.to.UsuarioTO;
import academia.config.Config;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.controlador.aluno.AlunoSessao;
import academia.controlador.matricula.MatriculaSessao;
import academia.controlador.matricula.MatriculaCtrlUtil;
import academia.controlador.mensalidade.MensalidadeCtrlUtil;
import academia.controlador.modalidade.ModSessao;
import academia.controlador.modalidade.ModalidadeCtrlUtil;
import academia.controlador.pag.PagCtrlUtil;
import academia.controlador.pag.PagSessao;
import academia.controlador.usuario.UsuarioCtrlUtil;
import academia.gui.GUI;
import academia.relatorio.GerenciadorRelatorio;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.ModalidadeFormatador;
import academia.util.NumeroFormatador;
import academia.util.PagamentoUtil;

public interface ControladorTO {
        
    public Config getConfig();
        
    public BD getBD();
    
    public GUI getGUI();
    
    public VisualizacaoMGR getVisuMGR();
    
    public GerenciadorRelatorio getGerenciadorRelatorio();
    
    public MSGUtil getMSGUtil();

    public NumeroFormatador getNumeroFormatador();

    public DataUtil getDataUtil();
    
    public ModalidadeFormatador getModalidadeFormatador();
    
    public PagamentoUtil getPGUtil();
    
    // utilitarios de controladores
    
    public SistemaCtrlUtil getSistemaCtrlUtil();
    
    public AlunoCtrlUtil getAlunoCUtil();
    
    public MatriculaCtrlUtil getMatCUtil();
    
    public ModalidadeCtrlUtil getModCUtil();
    
    public MensalidadeCtrlUtil getMensCUtil();
    
    public PagCtrlUtil getPagCUtil();
    
    public UsuarioCtrlUtil getUsuarioCUtil();

    // objetos de sessão
    
    public UsuarioTO getUsuarioLogado();
    
    public BDConfigTO getBDConfig();
    
    public AlunoSessao getAlunoSessao();
    
    public MatriculaSessao getMatSessao();
    
    public PagSessao getPagSessao();
    
    public ModSessao getModSessao();
    
    public void setUsuarioLogado( UsuarioTO usuario );    
    
}
