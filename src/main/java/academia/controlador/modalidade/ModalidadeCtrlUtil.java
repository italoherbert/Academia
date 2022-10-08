package academia.controlador.modalidade;

import academia.Consts;
import academia.GUIConsts;
import academia.bd.BD;
import academia.bd.BDConsts;
import academia.bd.to.ModalidadeTO;
import academia.bd.dao.DAOException;
import academia.bd.to.MensalidadeTO;
import academia.controlador.ControladorTO;
import academia.gui.modalidade.filtro.ModalidadeFiltroGUITO;
import academia.util.DataUtil;
import academia.util.NumeroFormatador;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import libs.gui.tabela.TabelaMD;

public class ModalidadeCtrlUtil {
    
    private ControladorTO cTO;
    
    public ModalidadeCtrlUtil( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public int filtra( ModalidadeFiltroGUITO to ) throws DAOException {                
        BD bd = cTO.getBD();        

        NumeroFormatador nf = cTO.getNumeroFormatador();
        DataUtil df = cTO.getDataUtil();               

        TabelaMD modsTBLMD = to.getModsTBLMD();
        String desc = to.getDescricao();       
        boolean mostrarInativas = to.isMostrarModsInativas();        
        
        List<ModalidadeTO> mods = new ArrayList( 0 );
        if ( !desc.isEmpty() ) {
            if ( desc.trim().equals( Consts.CARACTER_BUSCA_TODOS ) ) {
                mods = bd.getModalidadeDAO().buscaTodas();
            } else {
                mods = bd.getModalidadeDAO().filtra( desc );
            }
        }
        
        modsTBLMD.removeTodasAsLinhas();

        Map<Integer, Color> cores = new HashMap<Integer, Color>();
        
        int i = 0;
        for ( ModalidadeTO m : mods ) {                              
            if ( m.getDataFim() == null || mostrarInativas ) {                    
                String dataFimS = Consts.NAO_FINALIZADA_TEXTO;
                if ( m.getDataFim() != null )
                    dataFimS = df.formataData( m.getDataFim() );                

                double valorDiaria = m.getValorDiaria();

                String valorDiariaS;
                if ( valorDiaria == BDConsts.VALOR_DIARIA_NULO )
                    valorDiariaS = Consts.VALOR_DIARIA_NULO_TXT;
                else valorDiariaS = nf.formatoReal( valorDiaria );
                
                int modID = m.getID();
                MensalidadeTO mens = bd.getModalidadeDAO().buscaMensalidadeAtual( modID );
                
                if ( m.getDataFim() != null )
                    cores.put( i, GUIConsts.COR_INATIVO );

                modsTBLMD.addLinha( new String[] {
                    String.valueOf( modID ),
                    m.getDescricao(),
                    nf.formatoReal( mens.getValor() ),
                    valorDiariaS,
                    df.formataData( m.getDataInicio() ),
                    dataFimS
                } );  
            }     
            i++;
        }        
        modsTBLMD.pintaTabela( cores );
        
        return mods.size();        
    }
    
}
