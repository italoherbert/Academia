package academia.controlador.usuario;

import academia.controlador.ControladorTO;
import academia.gui.GUI;
import academia.gui.usuario.alterarsenha.AlterarSenhaGUIListener;
import academia.gui.usuario.alterarsenha.AlterarSenhaGUITO;
import academia.loginoper.usuario.AlterarSenhaUsuarioOperLoginOK;
import academia.loginoper.LoginInterceptControlador;

public class AlterarSenhaControlador implements AlterarSenhaGUIListener {
    
    private ControladorTO cTO;

    public AlterarSenhaControlador(ControladorTO cTO) {
        this.cTO = cTO;
    }

    public void alterarBTAcionado(AlterarSenhaGUITO to) {
        GUI gui = cTO.getGUI();
        
        AlterarSenhaUsuarioOperLoginOK operLogOk = new AlterarSenhaUsuarioOperLoginOK( cTO, to );
        LoginInterceptControlador ctrl = new LoginInterceptControlador( cTO, operLogOk );
        
        gui.getLoginInterceptGUI().setLoginInterceptListener( ctrl );
        gui.getLoginInterceptGUI().getTO().limpar();
        
        gui.getLoginInterceptGUI().getTO().setVisivel( true );         
    }        
    
}
