package academia.gui.matricula.nova;

public class NovaMatTO {
    
    private NovaMatGUI gui;
    
    public NovaMatTO( NovaMatGUI gui ) {
        this.gui = gui;
    }
    
    public void setCopiarModsOpVisivel( boolean visivel ) {
        gui.getFormPNL().getCopiarModsCB().setVisible( visivel ); 
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }        
    
    public String getDataMat() {
        return gui.getFormPNL().getDataPagTF().getText();
    }
    
    public void setDataMat( String dataMat ) {
        gui.getFormPNL().getDataPagTF().setText( dataMat ); 
    }
    
    public boolean isCopiarModsOpSelecionada() {
        return gui.getFormPNL().getCopiarModsCB().isSelected();
    }
    
    public void setCopiarModsOpSelecionada( boolean selecionada ) {
        gui.getFormPNL().getCopiarModsCB().setSelected( selecionada ); 
    }
    
}
