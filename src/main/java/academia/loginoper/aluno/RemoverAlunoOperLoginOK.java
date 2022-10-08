package academia.loginoper.aluno;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.AlunoTO;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.controlador.aluno.edit.EditAlunoControlador;
import academia.gui.GUI;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.gui.aluno.form.edit.EditAlunoFormGUITO;
import academia.gui.login.LoginGUITO;
import academia.loginoper.OperLoginOK;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class RemoverAlunoOperLoginOK implements OperLoginOK {
    
    private ControladorTO cTO;
    private EditAlunoFormGUITO editAlFormGUITO;
    
    private int[] permissoes;    

    public RemoverAlunoOperLoginOK( ControladorTO cTO, EditAlunoFormGUITO editAlFormGUITO ) {
        this.cTO = cTO;
        this.editAlFormGUITO = editAlFormGUITO;
        this.permissoes = new int[] { cTO.getBDConfig().getAdminUsuarioTipoID() };
    }
    
    public void loginOK( LoginGUITO loginGUITO, UsuarioTO usuarioLogado ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();                
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        if ( editAlFormGUITO.getID().isEmpty() ) {
            msgUtil.mostraErro( MSGConsts.ID_NAO_CARREGADO, MSGConsts.ALUNO_TITULO ); 
        } else { 
            int alunoID = Integer.parseInt(editAlFormGUITO.getID() );
            try {
                AlunoTO aluno = bd.getAlunoDAO().busca( alunoID );
                String nome = aluno.getNome();
                String pergunta = MSGConsts.PERGUNTA_REMOVER_ALUNO.replace( "%1", nome );
                int result = msgUtil.mostraPergunta( pergunta, MSGConsts.ALUNO_TITULO ); 
                if ( result == JOptionPane.YES_OPTION ) {                
                    bd.getAlunoDAO().remove( alunoID );
                    
                    AlunoFiltroGUITO filtroTO = gui.getJP().getAlunoGUI().getFiltroGUI().getTO();
                    alunoCUtil.filtrar( filtroTO ); 
                    alunoCUtil.atualizaQuantAnivers();
                    
                    editAlFormGUITO.setVisivel( false ); 
                    
                    msgUtil.mostraInfo( MSGConsts.REMOCAO_CONCLUIDA, MSGConsts.ALUNO_TITULO );                 
                }        
            } catch (DAOException ex) {
                Logger.getLogger(EditAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int[] getPermissoes() {
        return permissoes;
    }
    
}
