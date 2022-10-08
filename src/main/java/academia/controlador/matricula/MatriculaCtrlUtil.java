package academia.controlador.matricula;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.MatriculaTO;
import academia.controlador.ControladorTO;
import academia.gui.matricula.MatriculaGUITO;
import academia.util.DataUtil;
import java.util.List;
import libs.gui.tabela.TabelaMD;

public class MatriculaCtrlUtil {

    private ControladorTO cTO;
    
    public MatriculaCtrlUtil( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void carregarMats( MatriculaGUITO matGUITO ) throws DAOException {
        BD bd = cTO.getBD();        
        DataUtil dutil = cTO.getDataUtil();
        
        MatriculaSessao lstMatsSessao = cTO.getMatSessao();
                        
        int alunoID = lstMatsSessao.getAlunoID();        
        String nomeAluno = bd.getAlunoDAO().buscaNome( alunoID );

        TabelaMD matTBLMD = matGUITO.getMatsTBL();
        matTBLMD.removeTodasAsLinhas();
        
        List<MatriculaTO> mats = bd.getAlunoDAO().buscaMats( alunoID );
        for( MatriculaTO mat : mats ) {
            String idS = String.valueOf( mat.getID() );
            String dataInicioS = dutil.formataData( mat.getDataInicio() );
            String dataFimS = MSGConsts.DATA_FIM_NULA;
            if ( mat.getDataFim() != null )
                dataFimS = dutil.formataData( mat.getDataFim() );                    

            matGUITO.getMatsTBL().addLinha( new String[]{
                idS, dataInicioS, dataFimS                        
            } );
        }
        
        matGUITO.setNomeTitulo( nomeAluno ); 
    }
    
}
