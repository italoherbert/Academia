package academia.controlador.modalidade;

import academia.Consts;
import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.to.ModalidadeTO;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.controlador.mensalidade.MensalidadeCtrlUtil;
import academia.controlador.modalidade.limpar.CadastrarModFormLimparDrv;
import academia.controlador.modalidade.limpar.EditarModFormLimparDrv;
import academia.gui.GUI;
import academia.gui.mensalidade.MensalidadeGUITO;
import academia.gui.modalidade.ModalidadeGUIListener;
import academia.gui.modalidade.ModalidadeGUITO;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;
import academia.gui.modalidade.form.ModalidadeFormGUITO;
import academia.util.DataUtil;
import academia.util.MSGUtil;
import academia.util.NumeroFormatador;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import libs.gui.tabela.TabelaMD;

public class ModalidadeControlador implements ModalidadeGUIListener {

    private ControladorTO cTO;
    
    public ModalidadeControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void cadastrarBTAcionado(ModalidadeGUITO to) {
        GUI gui = cTO.getGUI();
                
        CadastrarModFormLimparDrv cadMFLimparDrv = new CadastrarModFormLimparDrv( cTO );

        ModalidadeFormGUITO formTO = gui.getModalidadeFormGUI().getTO();        
        formTO.limpar( cadMFLimparDrv );
        formTO.setID( Consts.TEXTO_VASIO );
        
        formTO.setAbilitarPorDataFim( true );
        formTO.setDataFimEditavel( false ); 
        formTO.setValorInicialEditavel( true ); 
        formTO.setRemoverBTAbilitado( false ); 
        formTO.setVisivel( true ); 
    }

    public void funcEditarAcionada(ModalidadeGUITO to) {        
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
                
        NumeroFormatador nf = cTO.getNumeroFormatador();
        DataUtil df = cTO.getDataUtil();
        
        ModSessao modSessao = cTO.getModSessao();
                
        TabelaMD modsTBLMD = to.getFiltroTO().getModsTBLMD();
        
        int linhaI = modsTBLMD.getIndiceLinhaSelecionada();
        if ( linhaI == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta(MSGConsts.NENHUMA_MOD_SELECIONADA, MSGConsts.MOD_TITULO );     
        } else {
            String idS = modsTBLMD.getCelulaValor( linhaI, 0 ); 
            int modID = Integer.parseInt( idS );
            
            try {
                ModalidadeTO mod = bd.getModalidadeDAO().busca( modID );                
                String descricao = mod.getDescricao();
                Timestamp dataInicio = mod.getDataInicio();
                double valorInicial = mod.getValorInicial();
                double valorDiaria = mod.getValorDiaria();
                
                String valorDiariaS = Consts.TEXTO_VASIO;
                if ( valorDiaria > 0 )
                    valorDiariaS = nf.formatoFlutuante( valorDiaria );
                
                EditarModFormLimparDrv editMFLimparDrv = new EditarModFormLimparDrv( cTO, dataInicio, valorInicial ); 
                
                ModalidadeFormGUITO formTO = gui.getModalidadeFormGUI().getTO();
                formTO.limpar( editMFLimparDrv );  
                
                formTO.setID( String.valueOf( modID ) );
                formTO.setDescricao( descricao );
                formTO.setDataRegistro( df.formataData( dataInicio ) );
                formTO.setValorInicial( nf.formatoFlutuante( valorInicial ) ); 
                formTO.setValorDiaria( valorDiariaS );
                
                Timestamp dataFim = mod.getDataFim();                
                if ( dataFim == null ) {
                    formTO.setDataFim( Consts.TEXTO_VASIO );
                    formTO.setAbilitarPorDataFim( true ); 
                } else {
                    formTO.setDataFim( df.formataData( dataFim ) ); 
                    formTO.setAbilitarPorDataFim( false ); 
                }
                
                modSessao.setModID( modID );
                modSessao.setDataCriacao( dataInicio );  
                modSessao.setValorInicial( valorInicial );
                
                formTO.setDataCriacaoEditavel( false );
                formTO.setValorInicialEditavel( false ); 
                formTO.setRemoverBTAbilitado( true ); 
                formTO.setVisivel( true );                              
            } catch (DAOException ex) {
                Logger.getLogger(ModalidadeControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void finalizarBTAcionado(ModalidadeGUITO to) {
        BD bd = cTO.getBD();
        MSGUtil msgUtil = cTO.getMSGUtil();
                
        ModalidadeCtrlUtil modCUtil = cTO.getModCUtil();
                
        ModalidadeFiltroGUITO filtroTO = to.getFiltroTO();
        TabelaMD modsTBLMD = filtroTO.getModsTBLMD();
                    
        int linhaI = modsTBLMD.getIndiceLinhaSelecionada();
        if ( linhaI == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta(MSGConsts.NENHUMA_MOD_SELECIONADA, MSGConsts.MOD_TITULO );     
        } else {
            String idS = modsTBLMD.getCelulaValor( linhaI, 0 ); 
            int modID = Integer.parseInt( idS );

            try {
                ModalidadeTO mod = bd.getModalidadeDAO().busca( modID );
                String descricao = mod.getDescricao();
                
                String pergunta = MSGConsts.PERGUNTA_FINALIZAR_MOD.replace( "%1", descricao );
                int result = msgUtil.mostraPergunta( pergunta, MSGConsts.MOD_TITULO );
                if ( result == JOptionPane.YES_OPTION ) {                    
                    if ( mod.getDataFim() == null ) {
                        bd.getModalidadeDAO().finaliza( modID, MSGConsts.MARCAR_COMO_INATIVA );               
                        
                        int cont = modCUtil.filtra( filtroTO ); 
                        if ( cont > 0 ) {
                            msgUtil.mostraInfo( MSGConsts.MOD_FINALIZADA, MSGConsts.MOD_TITULO );     
                        } else {
                            msgUtil.mostraAlerta( MSGConsts.NENHUMA_MOD_ENCONTRADA, MSGConsts.MOD_TITULO ); 
                        }
                    } else {
                        msgUtil.mostraAlerta( MSGConsts.MOD_JA_FINALIZADA, MSGConsts.MOD_TITULO );     
                    }                    
                } else {
                    msgUtil.mostraInfo( MSGConsts.FINALIZACAO_CANCELADA, MSGConsts.MOD_TITULO ); 
                }                            
            } catch (DAOException ex) {
                Logger.getLogger(ModalidadeControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void editarMensalidadesBTAcionado(ModalidadeGUITO to) {
        GUI gui = cTO.getGUI();
        MSGUtil msgUtil = cTO.getMSGUtil();
                     
        MensalidadeCtrlUtil mensCUtil = cTO.getMensCUtil();
        
        ModSessao modSessao = cTO.getModSessao();        
        
        ModalidadeFiltroGUITO filtroTO = to.getFiltroTO();
        TabelaMD modsTBLMD = filtroTO.getModsTBLMD();
                    
        int linhaI = modsTBLMD.getIndiceLinhaSelecionada();
        if ( linhaI == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta(MSGConsts.NENHUMA_MOD_SELECIONADA, MSGConsts.MOD_TITULO );     
        } else {
            String idS = modsTBLMD.getCelulaValor( linhaI, 0 ); 
            int modID = Integer.parseInt( idS );
            
            try {              
                modSessao.setModID( modID );
                
                MensalidadeGUITO mensTO = gui.getMensalidadeGUI().getTO();
                mensCUtil.carregarMensalidades( mensTO );
                
                mensTO.setVisivel( true ); 
            } catch (DAOException ex) {
                Logger.getLogger(ModalidadeControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
       
}
