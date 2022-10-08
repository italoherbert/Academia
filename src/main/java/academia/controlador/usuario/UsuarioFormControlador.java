package academia.controlador.usuario;

import academia.controlador.ControladorTO;
import academia.gui.GUI;
import academia.gui.usuario.form.UsuarioFormGUIListener;
import academia.gui.usuario.form.UsuarioFormGUITO;
import academia.loginoper.LoginInterceptControlador;
import academia.loginoper.usuario.RemoverUsuarioOperLoginOK;
import academia.loginoper.usuario.SalvarUsuarioOperLoginOK;

public class UsuarioFormControlador implements UsuarioFormGUIListener {

    private ControladorTO cTO;
    
    public UsuarioFormControlador( ControladorTO cTO ) {
        this.cTO = cTO;
    }
    
    public void salvarBTAcionado( UsuarioFormGUITO to ) {
        GUI gui = cTO.getGUI();              
        
        SalvarUsuarioOperLoginOK opLogOK = new SalvarUsuarioOperLoginOK( cTO, to );
        LoginInterceptControlador logInter = new LoginInterceptControlador( cTO, opLogOK );
        
        gui.getLoginInterceptGUI().setLoginInterceptListener( logInter );
        gui.getLoginInterceptGUI().getTO().limpar();
        gui.getLoginInterceptGUI().getTO().setVisivel( true ); 
    }    

    public void removerBTAcionado( UsuarioFormGUITO to ) {        
        GUI gui = cTO.getGUI();
        
        RemoverUsuarioOperLoginOK oper = new RemoverUsuarioOperLoginOK( cTO, to );
        LoginInterceptControlador logInter = new LoginInterceptControlador( cTO, oper );
        
        gui.getLoginInterceptGUI().setLoginInterceptListener( logInter );
        gui.getLoginInterceptGUI().getTO().limpar();
        gui.getLoginInterceptGUI().getTO().setVisivel( true ); 
    }
            
}
