package academia.controlador.aluno;

import academia.MSGConsts;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.gui.aluno.filtro.AlunoFiltroGUIListener;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FiltroAlunoControlador implements AlunoFiltroGUIListener {
    
    private ControladorTO cTO;

    public FiltroAlunoControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void filtrarBTAcionado( AlunoFiltroGUITO to ) {        
        MSGUtil msgUtil = cTO.getMSGUtil();
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        try {
            alunoCUtil.setFiltrarApenasAniversariantes( false ); 
            int cont = alunoCUtil.filtrar( to ); 
            if ( cont <= 0 )
                msgUtil.mostraAlerta( MSGConsts.NENHUM_ALUNO_ENCONTRADO, MSGConsts.ALUNO_TITULO );
            
        } catch (DAOException ex) {
            Logger.getLogger(FiltroAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public void mostrarAlunosInativosCBAlterado( AlunoFiltroGUITO to ) {
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        
        try {
            alunoCUtil.setFiltrarApenasAniversariantes( false ); 
            alunoCUtil.filtrar( to ); 
        } catch (DAOException ex) {
            Logger.getLogger(FiltroAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

    public void aniverBTAcionado(AlunoFiltroGUITO to) {        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
                
        try {
            alunoCUtil.setFiltrarApenasAniversariantes( true ); 
            alunoCUtil.filtrar( to );
        } catch (DAOException ex) {
            Logger.getLogger(FiltroAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
