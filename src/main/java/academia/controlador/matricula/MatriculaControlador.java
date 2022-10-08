package academia.controlador.matricula;

import academia.MSGConsts;
import academia.bd.dao.DAOException;
import academia.controlador.ControladorTO;
import academia.controlador.aluno.AlunoControlador;
import academia.controlador.pag.GeraParcelaException;
import academia.controlador.pag.PagCtrlUtil;
import academia.controlador.pag.PagSessao;
import academia.gui.GUI;
import academia.gui.matricula.MatriculaGUIListener;
import academia.gui.matricula.MatriculaGUITO;
import academia.gui.pag.PagamentoGUITO;
import academia.loginoper.LoginInterceptControlador;
import academia.loginoper.aluno.RemoverMatOperLoginOK;
import academia.util.MSGUtil;
import java.util.logging.Level;
import java.util.logging.Logger;
import libs.gui.tabela.TabelaMD;

public class MatriculaControlador implements MatriculaGUIListener {
    
    private ControladorTO cTO;

    public MatriculaControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public void carregarParcelasBTAcionado( MatriculaGUITO to ) {
        GUI gui = cTO.getGUI();                        
        MSGUtil msgUtil = cTO.getMSGUtil();
        
        PagCtrlUtil pagCUtil = cTO.getPagCUtil();
        PagSessao pagSessao = cTO.getPagSessao();        
        
        TabelaMD matTMD = to.getMatsTBL();
        
        int linhaS = matTMD.getIndiceLinhaSelecionada();
        if ( linhaS == TabelaMD.ID_LINHA_NULA ) {
            msgUtil.mostraAlerta( MSGConsts.NENHUMA_MAT_SEL, MSGConsts.ALUNO_TITULO ); 
        } else {
            String sID = matTMD.getCelulaValor( linhaS, 0 );
            int matID = Integer.parseInt( sID );
            
            try {                 
                // CARREGA JANELA DE PAGAMENTOS POR MATRICULA DO ALUNO
                // seta ID da matricula
                pagSessao.setMatID( matID );

                // calcula parcelas por matrícula do aluno
                PagamentoGUITO pagTO = gui.getPagamentoGUI().getTO();
                pagCUtil.calculaParcelas( pagTO ); 

                pagTO.setVisivel( true );                 
            } catch ( GeraParcelaException e ) {
                msgUtil.mostraErro( e.getMessage(), MSGConsts.PAG_TITULO ); 
            } catch (DAOException ex) {
                Logger.getLogger(AlunoControlador.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
    }        

    public void removerBTAcionado(MatriculaGUITO to) {
        GUI gui = cTO.getGUI();

        RemoverMatOperLoginOK oper = new RemoverMatOperLoginOK( cTO, to );
        LoginInterceptControlador rlCtrl = new LoginInterceptControlador( cTO, oper );

        gui.getLoginInterceptGUI().setLoginInterceptListener( rlCtrl );                         
        gui.getLoginInterceptGUI().getTO().limpar();
        gui.getLoginInterceptGUI().getTO().setVisivel( true ); 
    }
    
}
