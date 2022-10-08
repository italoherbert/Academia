package academia.gui.config;

public class ConfigGUITO {
    
    private ConfigGUI gui;
    
    public ConfigGUITO( ConfigGUI gui ) {
        this.gui = gui;
    }    
    
    public String getToleranciaPag() {
        return gui.getConfigPNL().getToleranciaPagTF().getText();
    }

    public boolean isSelecAutoCarregarUsuarios() {
        return gui.getConfigPNL().getAutoCarregarUsuariosCB().isSelected();
    }
    
    public boolean isSelecAutoCarregarModalidades() {
        return gui.getConfigPNL().getAutoCarregarModsCB().isSelected();
    }
    
    public void setToleranciaPag( String tolerancia ) {
        gui.getConfigPNL().getToleranciaPagTF().setText( tolerancia );
    }        
    
    public void setSelecAutoCarregarModalidades( boolean b ) {
        gui.getConfigPNL().getAutoCarregarModsCB().setSelected( b ); 
    }
    
    public void setSelecAutoCarregarUsuarios( boolean b ) {
        gui.getConfigPNL().getAutoCarregarUsuariosCB().setSelected( b ); 
    }

}
