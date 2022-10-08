package academia.gui;

import academia.gui.aluno.form.cad.CadAlunoFormGUI;
import academia.gui.aluno.form.edit.EditAlunoFormGUI;
import academia.gui.historicomods.HistoricoModsGUI;
import academia.gui.medidas.MedidasFormGUI;
import academia.gui.medidas.comparar.CompararMedidasGUI;
import academia.gui.jp.JPGUI;
import academia.gui.login.LoginInicialGUI;
import academia.gui.login.LoginInterceptGUI;
import academia.gui.matricula.MatriculaGUI;
import academia.gui.matricula.nova.NovaMatGUI;
import academia.gui.mensalidade.MensalidadeGUI;
import academia.gui.mensalidade.novovalor.NovoValorMensalidadeGUI;
import academia.gui.modalidade.form.ModalidadeFormGUI;
import academia.gui.pag.PagamentoGUI;
import academia.gui.pag.visualizar.VisualizarParcelaGUI;
import academia.gui.pagdiaria.PagamentoDiariaGUI;
import academia.gui.usuario.alterarsenha.AlterarSenhaGUI;
import academia.gui.usuario.form.UsuarioFormGUI;

public class GUI {
    
    private JPGUI jp = new JPGUI();
    
    private LoginInicialGUI loginInicialGUI = new LoginInicialGUI();
    private LoginInterceptGUI LoginInterceptGUI = new LoginInterceptGUI();
    
    private CadAlunoFormGUI cadAlunoFormGUI;
    private EditAlunoFormGUI editAlunoFormGUI;
    private ModalidadeFormGUI modFormGUI = new ModalidadeFormGUI( jp );
    private MedidasFormGUI medidasFormGUI = new MedidasFormGUI( jp );
    private UsuarioFormGUI usuarioFormGUI = new UsuarioFormGUI( jp );

    private PagamentoGUI pagGUI = new PagamentoGUI( jp );
    private PagamentoDiariaGUI pagDiariaGUI = new PagamentoDiariaGUI( jp );
    private VisualizarParcelaGUI visualizarParcelaGUI = new VisualizarParcelaGUI( jp );
    private MatriculaGUI matGUI = new MatriculaGUI( jp );
    private HistoricoModsGUI historicoModsGUI = new HistoricoModsGUI( jp );
    private MensalidadeGUI mensalidadeGUI = new MensalidadeGUI( jp );
    private NovoValorMensalidadeGUI novoValMensGUI = new NovoValorMensalidadeGUI( jp );
    private CompararMedidasGUI compararMedidasGUI = new CompararMedidasGUI( jp );
    private AlterarSenhaGUI alterarSenhaGUI = new AlterarSenhaGUI( jp );
    
    private NovaMatGUI novaMatGUI = new NovaMatGUI( jp );
        
    public GUI( GUIConfig config ) {
        this.cadAlunoFormGUI = new CadAlunoFormGUI( jp, config );
        this.editAlunoFormGUI = new EditAlunoFormGUI( jp, config );    
    }
    
    public JPGUI getJP() {
        return jp;
    }
        
    public LoginInicialGUI getLoginInicialGUI() {
        return loginInicialGUI;
    }
    
    public LoginInterceptGUI getLoginInterceptGUI() {
        return LoginInterceptGUI;
    }
        
    public CadAlunoFormGUI getCadAlunoGUI() {
        return cadAlunoFormGUI;
    }
    
    public EditAlunoFormGUI getEditAlunoGUI() {
        return editAlunoFormGUI;
    }        
    
    public MedidasFormGUI getMedidasFormGUI() {
        return medidasFormGUI;
    }
    
    public ModalidadeFormGUI getModalidadeFormGUI() {
        return modFormGUI;
    }
    
    public UsuarioFormGUI getUsuarioFormGUI() {
        return usuarioFormGUI;
    }
    
    public PagamentoGUI getPagamentoGUI() {
        return pagGUI;
    }
    
    public PagamentoDiariaGUI getPagamentoDiariaGUI() {
        return pagDiariaGUI;
    }
    
    public MatriculaGUI getMatriculaGUI() {
        return matGUI;
    }
        
    public VisualizarParcelaGUI getVisualizarParcelaGUI() {
        return visualizarParcelaGUI;
    }
    
    public HistoricoModsGUI getHistoricoModsGUI() {
        return historicoModsGUI;
    }
    
    public MensalidadeGUI getMensalidadeGUI() {
        return mensalidadeGUI;    
    }
    
    public NovoValorMensalidadeGUI getNovoValMensGUI() {
        return novoValMensGUI;
    }  
    
    public CompararMedidasGUI getCompararMedidasGUI() {
        return compararMedidasGUI;
    }
    
    public AlterarSenhaGUI getAlterarSenhaGUI() {
        return alterarSenhaGUI;
    }
    
    public NovaMatGUI getNovaMatGUI() {
        return novaMatGUI;
    }
    
}
