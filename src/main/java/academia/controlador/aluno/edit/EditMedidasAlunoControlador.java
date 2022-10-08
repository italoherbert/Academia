package academia.controlador.aluno.edit;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.MedidasTO;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoCtrlUtil;
import academia.controlador.aluno.AlunoSessao;
import academia.gui.GUI;
import academia.gui.aluno.form.edit.medidas.EditMedidasGUITO;
import academia.gui.medidas.MedidasFormGUIListener;
import academia.gui.medidas.MedidasFormGUITO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.NumeroFormatador;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class EditMedidasAlunoControlador implements MedidasFormGUIListener {
    
    private ControladorTO cTO;
    
    public EditMedidasAlunoControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void salvarBTAcionado( MedidasFormGUITO to ) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        NumeroFormatador nf = cTO.getNumeroFormatador();        
        DataUtil df = cTO.getDataUtil();
                
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        AlunoSessao ac = cTO.getAlunoSessao();        
                            
        try {            
            if ( ac.getMatricula() == null ) {
                msgUtil.mostraErro( MSGConsts.ID_NAO_CARREGADO, MSGConsts.ALUNO_TITULO );
            } else {
                List<String> erros = alunoCUtil.validaCadMedidasForm( to );
                if ( erros.isEmpty() ) {
                    int matID = ac.getMatricula().getID();
                    Date dataReg = df.converteData( to.getDataReg() );
                    
                    MedidasTO medidas = new MedidasTO();
                    medidas.setMatID( matID ); 
                    medidas.setPeso( Integer.parseInt( to.getPeso() ));
                    medidas.setAltura( nf.valorFlutuante( to.getAltura() ) );
                    medidas.setBracoEsquerdo( Integer.parseInt( to.getBracoEsquerdo() ) );
                    medidas.setAntebracoEsquerdo( Integer.parseInt( to.getAntebracoEsquerdo() ) );
                    medidas.setBracoDireito( Integer.parseInt( to.getBracoDireito() ) );
                    medidas.setAntebracoDireito( Integer.parseInt( to.getAntebracoDireito() ) ); 
                    medidas.setTorax( Integer.parseInt( to.getTorax() ) );
                    medidas.setCintura( Integer.parseInt( to.getCintura() ) );
                    medidas.setBumbum( Integer.parseInt( to.getBumbum() ) );
                    medidas.setCoxaEsquerda( Integer.parseInt( to.getCoxaEsquerda() ) );
                    medidas.setPanturrilhaEsquerda( Integer.parseInt( to.getPanturrilhaEsquerda() ) ); 
                    medidas.setCoxaDireita( Integer.parseInt( to.getCoxaDireita() ) );
                    medidas.setPanturrilhaDireita( Integer.parseInt( to.getPanturrilhaDireita() ) ); 
                    medidas.setDataReg( new Timestamp( dataReg.getTime() ) );              

                    if ( to.getID().isEmpty() ) {
                        bd.getMedidasDAO().insere( medidas ); 
                    } else {
                        int medsID = Integer.parseInt( to.getID() );

                        medidas.setID( medsID ); 
                        bd.getMedidasDAO().atualiza( medidas ); 
                    }    
                    
                    EditMedidasGUITO eMedsTO = gui.getEditAlunoGUI().getTO().getMedidasTO();
                    alunoCUtil.carregarTabelaMedidas( eMedsTO, matID );
                    
                    msgUtil.mostraInfo( MSGConsts.DADOS_SALVOS, MSGConsts.ALUNO_TITULO ); 
                    to.setVisivel( false ); 
                } else {
                    String msg = msgUtil.constroiMSG( erros );
                    msgUtil.mostraAlerta( msg, MSGConsts.ALUNO_TITULO );
                }
            }
        } catch ( ParseException ex ) {
            msgUtil.mostraErro( MSGConsts.INCONSISTENCIA_CAD_EDIT_MEDIDAS, MSGConsts.ALUNO_TITULO ); 
        } catch (DAOException ex) {
            Logger.getLogger(EditMedidasAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerBTAcionado(MedidasFormGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        AlunoCtrlUtil alunoCUtil = cTO.getAlunoCUtil();
        AlunoSessao as = cTO.getAlunoSessao();
        
        if( to.getID().isEmpty() ) {
            msgUtil.mostraErro( MSGConsts.ID_NAO_CARREGADO, MSGConsts.ALUNO_TITULO );
        } else {
            if ( as.getMatricula() == null ) {
                msgUtil.mostraErro( MSGConsts.ID_NAO_CARREGADO, MSGConsts.ALUNO_TITULO ); 
            } else {
                int matID = as.getMatricula().getID();
                int medidasID = Integer.parseInt( to.getID() );

                int result = msgUtil.mostraPergunta( MSGConsts.PERGUNTA_REMOVER_MEDIDAS, MSGConsts.ALUNO_TITULO );
                if ( result == JOptionPane.YES_OPTION ) {
                    try { 
                        bd.getMedidasDAO().remove( medidasID );

                        EditMedidasGUITO eMedsTO = gui.getEditAlunoGUI().getTO().getMedidasTO();                        
                        alunoCUtil.carregarTabelaMedidas( eMedsTO, matID );
                                                
                        msgUtil.mostraInfo( MSGConsts.MEDIDAS_REMOVIDAS, MSGConsts.ALUNO_TITULO ); 
                                                                        
                        to.setVisivel( false ); 
                    } catch (DAOException ex) {
                        Logger.getLogger(EditMedidasAlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void limparBTAcionado( MedidasFormGUITO to ) {
        Timestamp dataMatricula = new Timestamp( System.currentTimeMillis() );
        to.limpar(new EditLimparAlunoCB( cTO, dataMatricula ) );
    }
            
}
