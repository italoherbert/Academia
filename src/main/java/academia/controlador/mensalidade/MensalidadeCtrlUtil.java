package academia.controlador.mensalidade;

import academia.Consts;
import academia.bd.BD;
import academia.bd.to.MensalidadeTO;
import academia.bd.to.ModalidadeTO;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.controlador.modalidade.ModSessao;
import academia.gui.GUI;
import academia.gui.mensalidade.MensalidadeGUITO;
import academia.util.DataUtil;
import academia.util.NumeroFormatador;
import java.util.List;
import libs.gui.tabela.TabelaMD;

public class MensalidadeCtrlUtil {

    private ControladorTO cTO;
    
    public MensalidadeCtrlUtil( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void carregarMensalidades( MensalidadeGUITO to ) throws DAOException {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();                

        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        ModSessao modSessao = cTO.getModSessao();
                
        int modID = modSessao.getModID();
        
        
        MensalidadeGUITO mensTO = gui.getMensalidadeGUI().getTO();
        TabelaMD mensTBLMD = mensTO.getMensTBLMD();

        List<MensalidadeTO> mens = bd.getModalidadeDAO().buscaMensalidades( modID );
        ModalidadeTO mod = bd.getModalidadeDAO().busca( modID );

        mensTBLMD.removeTodasAsLinhas();
        for( MensalidadeTO men : mens ) {
            mensTBLMD.addLinha( new String[]{ 
                String.valueOf( men.getID() ),
                df.formataData( men.getDataAlter() ), 
                nf.formatoReal( men.getValor() )
            } );
        }
        mensTO.setModDesc( mod.getDescricao() ); 

        if ( mens.isEmpty() ) {
            mensTO.setValorAtual( Consts.TEXTO_VASIO ); 
        } else {
            MensalidadeTO ult = mens.get( 0 );
            mensTO.setValorAtual( nf.formatoReal( ult.getValor() ) ); 
        }
                
    }
    
}
