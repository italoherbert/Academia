package academia.loginoper.aluno;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.controlador.matricula.MatriculaControlador;
import academia.controlador.matricula.MatriculaCtrlUtil;
import academia.gui.GUI;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.gui.login.LoginGUITO;
import academia.gui.matricula.MatriculaGUITO;
import academia.loginoper.OperLoginOK;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import libs.gui.tabela.TabelaMD;

public class RemoverMatOperLoginOK implements OperLoginOK {
    
    private ControladorTO cTO;
    private MatriculaGUITO matGUITO;
    
    private int[] permissoes;    

    public RemoverMatOperLoginOK( ControladorTO cTO, MatriculaGUITO matGUITO ) {
        this.cTO = cTO;
        this.matGUITO = matGUITO;                
        this.permissoes = new int[] { cTO.getBDConfig().getAdminUsuarioTipoID() };
    }
    
    public void loginOK( LoginGUITO loginGUITO, UsuarioTO usuarioLogado ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();        
        
        MatriculaCtrlUtil matCUtil = cTO.getMatCUtil();
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        TabelaMD matTMD = matGUITO.getMatsTBL();
        
        int linhaS = matTMD.getIndiceLinhaSelecionada();
        if ( linhaS == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUMA_MAT_SEL, MSGConsts.ALUNO_TITULO ); 
        } else {
            String sID = matTMD.getCelulaValor( linhaS, 0 );
            int matID = Integer.parseInt( sID );
            
            int resposta = msgUtil.mostraPergunta( MSGConsts.PERGUNTA_REMOVER_MAT, MSGConsts.ALUNO_TITULO );
            if ( resposta == JOptionPane.YES_OPTION ) {                
                try {                     
                    bd.getMatriculaDAO().remove( matID );

                    matCUtil.carregarMats( matGUITO );
                    
                    AlunoFiltroGUITO alunoFtrGUITO = gui.getJP().getAlunoGUI().getFiltroGUI().getTO();
                    alunoCUtil.filtrar( alunoFtrGUITO );
                    
                    msgUtil.mostraInfo( MSGConsts.REMOCAO_CONCLUIDA, MSGConsts.ALUNO_TITULO ); 
                } catch (DAOException ex) {
                    Logger.getLogger(MatriculaControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public int[] getPermissoes() {
        return permissoes;
    }
    
}
