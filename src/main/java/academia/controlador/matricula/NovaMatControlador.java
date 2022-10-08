package academia.controlador.matricula;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.BDConsts;
import academia.bd.dao.DAOException;
import academia.bd.to.AlunoTO;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoControlador;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.gui.GUI;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.gui.matricula.nova.NovaMatGUIListener;
import academia.gui.matricula.nova.NovaMatTO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class NovaMatControlador implements NovaMatGUIListener {

    private ControladorTO cTO;
    
    public NovaMatControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void criarBTAcionado( NovaMatTO guiTO ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        DataUtil dutil = cTO.getDataUtil();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        MatriculaSessao matSessao = cTO.getMatSessao();
                       
        int alunoID = matSessao.getAlunoID();
            
        try {
            AlunoTO aluno = bd.getAlunoDAO().busca( alunoID );
            String nome = aluno.getNome();

            boolean criarNovaMat = false;
            if ( aluno.getMatriculaCorrente() == BDConsts.ID_NULO ) {
                criarNovaMat = true;
            } else {
                String pergunta = MSGConsts.PERGUNTA_RECRIAR_MAT.replace( "%1", nome );

                int result = msgUtil.mostraPergunta( pergunta, MSGConsts.ALUNO_TITULO );
                if ( result == JOptionPane.YES_OPTION ) {                                       
                    criarNovaMat = true;
                }
            }

            if ( criarNovaMat ) {
                boolean copiarMods;
                if ( aluno.getMatriculaCorrente() == BDConsts.ID_NULO )
                    copiarMods = false;
                else copiarMods = guiTO.isCopiarModsOpSelecionada();
                                
                String dataMatStr = guiTO.getDataMat();
                Date dataMatDT = null;
                try {
                    dataMatDT = dutil.converteData( dataMatStr );                    
                } catch (ParseException ex) {
                    dataMatDT = null;
                }
                
                if ( dataMatDT == null ) {
                    msgUtil.mostraAlerta( MSGConsts.DATA_MAT_INVALIDA, MSGConsts.ALUNO_TITULO ); 
                } else {
                    Date dataAtual = dutil.apenasData( new Date() );
                    if ( dataMatDT.compareTo( dataAtual ) < 0 ) {
                        String dataAtualStr = dutil.formataData( dataAtual );
                        String msg = MSGConsts.DATA_MENOR_QUE_ATUAL.replace( "%1", dataAtualStr );
                        
                        msgUtil.mostraAlerta( msg, MSGConsts.ALUNO_TITULO ); 
                    } else {
                        Timestamp dataMat = new Timestamp( dataMatDT.getTime() );
                        Date dataDiaPagDT = dutil.calculaDataPag( dataMat );

                        Timestamp dataDiaPag = new Timestamp( dataDiaPagDT.getTime() );
                        bd.getAlunoDAO().novaMat( alunoID, dataMat, dataDiaPag, copiarMods ); 

                        AlunoFiltroGUITO afTO = gui.getJP().getAlunoGUI().getFiltroGUI().getTO();
                        alunoCUtil.filtrar( afTO );

                        guiTO.setVisivel( false ); 

                        String msg = MSGConsts.MAT_CRIADA.replace( "%1", nome );
                        msgUtil.mostraInfo( msg, MSGConsts.ALUNO_TITULO );                                                                                 
                    }
                }
            }                
        } catch (DAOException ex) {
            Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
