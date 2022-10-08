package academia.controlador.pagdiaria;

import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.dao.DAOException;
import academia.bd.to.BDConfigTO;
import academia.bd.to.ModalidadeTO;
import academia.bd.to.PagamentoTO;
import academia.bd.to.UsuarioTO;
import academia.controlador.ControladorTO;
import academia.gui.pagdiaria.PagamentoDiariaGUIListener;
import academia.gui.pagdiaria.PagamentoDiariaGUITO;
import academia.util.MSGUtil;
import academia.util.ModalidadeFormatador;
import academia.util.NumeroFormatador;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PagDiariaControlador implements PagamentoDiariaGUIListener {

    private ControladorTO cTO;
    
    public PagDiariaControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void registrarBTAcionado(PagamentoDiariaGUITO to) {
        BD bd = cTO.getBD();
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        UsuarioTO usuarioLogado = cTO.getUsuarioLogado();
        BDConfigTO bdConfig = cTO.getBDConfig();
        
        if ( to.getAlunoMods().length < 1 ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUMA_MOD_ADICIONADA, MSGConsts.PAG_TITULO ); 
        } else {        
            String[] mods = to.getAlunoMods();

            try {
                double total = 0;
                for( String modStr : mods ) {
                    int modID = mf.extraiModalidadeID( modStr );
                    ModalidadeTO mod = bd.getModalidadeDAO().busca( modID );                

                    total += mod.getValorDiaria();
                }

                int diariaAlunoID = bdConfig.getDiariaAlunoID();
                int matCorr = bd.getAlunoDAO().buscaMatCorr( diariaAlunoID );

                PagamentoTO pag = new PagamentoTO();
                pag.setMatID( matCorr );
                pag.setUsuarioID( usuarioLogado.getID() );
                pag.setValor( total );
                pag.setDataPag( new Timestamp( System.currentTimeMillis() ) ); 
                pag.setDesconto( 0 ); 

                bd.getPagamentoDAO().insere( pag );

                to.setVisivel( false ); 
                msgUtil.mostraInfo( MSGConsts.PAG_REALIZADO, MSGConsts.PAG_TITULO );             
            } catch ( DAOException ex ) {
                Logger.getLogger(PagDiariaControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void addModBTAcionado(PagamentoDiariaGUITO to) {
        this.atualizaTotal( to );
    }

    public void removeModBTAcionado(PagamentoDiariaGUITO to) {
        this.atualizaTotal( to ); 
    }
    
    private void atualizaTotal( PagamentoDiariaGUITO to ) {
        BD bd = cTO.getBD();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
        
        String[] mods = to.getAlunoMods();
        
        try {
            double total = 0;
            for( String modStr : mods ) {
                int modID = mf.extraiModalidadeID( modStr );
                ModalidadeTO mod = bd.getModalidadeDAO().busca( modID );                
                
                total += mod.getValorDiaria();
            }
            
            to.setTotal( nf.formatoReal( total ) ); 
        } catch ( DAOException ex ) {
            Logger.getLogger(PagDiariaControlador.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
