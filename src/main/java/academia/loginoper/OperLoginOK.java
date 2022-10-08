package academia.loginoper;

import academia.bd.to.UsuarioTO;
import academia.gui.login.LoginGUITO;

public interface OperLoginOK {
    
    public void loginOK( LoginGUITO loginGUITO, UsuarioTO usuarioLogado );
    
    public int[] getPermissoes();
    
}
