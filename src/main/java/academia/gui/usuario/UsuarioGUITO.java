package academia.gui.usuario;

import academia.gui.usuario.filtro.UsuarioFiltroGUITO;

public class UsuarioGUITO {
    
    private UsuarioGUI gui;
    
    public UsuarioGUITO( UsuarioGUI gui ) {
        this.gui = gui;
    }
    
    public UsuarioFiltroGUITO getFiltroTO() {
        return gui.getFiltroGUI().getTO();
    }
    
}
