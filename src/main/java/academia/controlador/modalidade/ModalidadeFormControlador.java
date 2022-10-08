package academia.controlador.modalidade;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.BDConsts;
import academia.bd.to.ModalidadeTO;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.controlador.modalidade.limpar.CadastrarModFormLimparDrv;
import academia.controlador.modalidade.limpar.EditarModFormLimparDrv;
import academia.gui.GUI;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;
import academia.gui.modalidade.form.ModalidadeFormGUIListener;
import academia.gui.modalidade.form.ModalidadeFormGUITO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.NumeroFormatador;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ModalidadeFormControlador implements ModalidadeFormGUIListener {
 
    private ControladorTO cTO;
    
    public ModalidadeFormControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void salvarBTAcionado(ModalidadeFormGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        ModalidadeCtrlUtil modCUtil = cTO.getModCUtil();
        
        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();

        List<String> erros = this.validaForm( to );
        if ( erros.isEmpty() ) {
            String desc = to.getDescricao();
            try {            
                Date dataReg = df.converteData( to.getDataRegistro() );
                Timestamp dataInicio = new Timestamp( dataReg.getTime() );
                
                double valorInicial = nf.valorFlutuante( to.getValorInicial() );
                double valorDiaria = BDConsts.VALOR_DIARIA_NULO;                
                try {
                    double vd = nf.valorFlutuante( to.getValorDiaria() );
                    if ( vd > 0 )
                        valorDiaria = vd;
                } catch ( ParseException e ) {
                    
                }

                ModalidadeTO mod = new ModalidadeTO();
                mod.setDescricao( desc );
                mod.setDataInicio( dataInicio );
                mod.setValorInicial( valorInicial );
                mod.setValorDiaria( valorDiaria ); 

                boolean inserir = false;
                boolean alterar = false;

                if ( to.getID().isEmpty() ) {
                    boolean existeEAtiva = bd.getModalidadeDAO().existeModalidadeAtivaPorDesc( desc );
                    if ( existeEAtiva ) {
                        String msg = MSGConsts.EXISTE_MOD_ATIVA.replace( "%1", desc );
                        msgUtil.mostraAlerta( msg, MSGConsts.MOD_TITULO ); 
                    } else {
                        inserir = true; 
                    }
                } else {
                    int modID = Integer.parseInt( to.getID() );
                    mod.setID( modID );

                    ModalidadeTO modAtual = bd.getModalidadeDAO().busca( modID );

                    if ( desc.equalsIgnoreCase( modAtual.getDescricao() ) ) {
                        alterar = true;
                    } else {
                        boolean existeEAtiva = bd.getModalidadeDAO().existeModalidadeAtivaPorDesc( desc );
                        if ( existeEAtiva ) {
                            String msg = MSGConsts.EXISTE_MOD_ATIVA.replace( "%1", desc );
                            msgUtil.mostraAlerta( msg, MSGConsts.MOD_TITULO ); 
                        } else {
                            alterar = true;
                        } 
                    }                                
                }            

                if ( inserir ) {
                    bd.getModalidadeDAO().insere( mod ); 
                } else if ( alterar ) {
                    bd.getModalidadeDAO().atualiza( mod );                                         
                }

                if ( inserir || alterar ) {
                    ModalidadeFiltroGUITO filtroTO = gui.getJP().getModalidadeGUI().getFiltroGUI().getTO();
                    modCUtil.filtra( filtroTO );
                                                                                
                    to.setVisivel( false );

                    msgUtil.mostraInfo( MSGConsts.DADOS_SALVOS, MSGConsts.MOD_TITULO );                                                                
                }
            } catch ( ParseException e ) {
                msgUtil.mostraAlerta( MSGConsts.INCONSISTENCIA_CAD_MOD, MSGConsts.MOD_TITULO ); 
            } catch ( DAOException ex ) {
                Logger.getLogger(ModalidadeFormControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String msg = msgUtil.constroiMSG( erros );
            
            msgUtil.mostraAlerta( msg, MSGConsts.MOD_TITULO );
        }
    }

    public void removerBTAcionado(ModalidadeFormGUITO to) {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        ModalidadeCtrlUtil modCUtil = cTO.getModCUtil();
        
        if ( to.getID().isEmpty() ) {
            msgUtil.mostraErro( MSGConsts.ID_NAO_CARREGADO, MSGConsts.MOD_TITULO ); 
        } else {            
            int modID = Integer.parseInt( to.getID() );
            
            try {
                boolean existeMod = bd.getModalidadeDAO().existeModAdicionadaAoAluno( modID );
                if ( existeMod ) {
                    msgUtil.mostraAlerta( MSGConsts.EXISTE_MOD_ADD_AO_ALUNO, MSGConsts.MOD_TITULO ); 
                } else {                
                    ModalidadeTO mod = bd.getModalidadeDAO().busca( modID );
                    String desc = mod.getDescricao();
                    String pergunta = MSGConsts.PERGUNTA_REMOVER_MOD.replace( "%1", desc );
                    int result = msgUtil.mostraPergunta( pergunta, MSGConsts.MOD_TITULO );
                    if ( result == JOptionPane.YES_OPTION ) {
                        bd.getModalidadeDAO().remove( modID );   

                        ModalidadeFiltroGUITO filtroTO = gui.getJP().getModalidadeGUI().getFiltroGUI().getTO();
                        modCUtil.filtra( filtroTO );

                        msgUtil.mostraInfo( MSGConsts.REMOCAO_CONCLUIDA, MSGConsts.MOD_TITULO ); 
                                                    
                        to.setVisivel( false );
                    }
                }
            } catch (DAOException ex) {
                Logger.getLogger(ModalidadeFormControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void limparBTAcionado(ModalidadeFormGUITO to) {
        ModSessao modSessao = cTO.getModSessao();
        
        if ( to.getID().isEmpty() ) {
            to.limpar( new CadastrarModFormLimparDrv( cTO ) );
        } else {
            Timestamp dataCriacao = modSessao.getDataCriacao();
            double valorInicial = modSessao.getValorInicial();
            to.limpar( new EditarModFormLimparDrv( cTO, dataCriacao, valorInicial ) );
        }
    }
        
    public List<String> validaForm( ModalidadeFormGUITO to ) {
        NumeroFormatador nf = cTO.getNumeroFormatador();
        DataUtil df = cTO.getDataUtil();
                
        
        List<String> erros = new ArrayList();        
        
        if( to.getDescricao().isEmpty() )
            erros.add( MSGConsts.DESCRICAO_CAMPO_OBRIGATORIO );
        
        try {
            double valor = nf.valorFlutuante( to.getValorInicial() );
            if ( valor < 0 )
                erros.add( MSGConsts.VALOR_INICIAL_MOD_INVALIDO );
        } catch ( ParseException e ) {
            erros.add( MSGConsts.VALOR_INICIAL_MOD_INVALIDO );
        }
        
        
        
        try {
            df.converteData( to.getDataRegistro() );
        } catch ( ParseException e ) {
            erros.add(MSGConsts.DATA_REGISTRO_MOD_INVALIDA );
        }
        
        return erros;
    }
    
}
