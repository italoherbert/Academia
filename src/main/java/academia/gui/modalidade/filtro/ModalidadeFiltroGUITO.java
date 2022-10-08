package academia.gui.modalidade.filtro;

import academia.Consts;
import libs.gui.tabela.TabelaMD;

public class ModalidadeFiltroGUITO {
           
    private ModalidadeFiltroGUI gui;
    
    public ModalidadeFiltroGUITO(ModalidadeFiltroGUI gui) {
        this.gui = gui;
    }
    
    public void limpar() {
        this.setDescricao( Consts.TEXTO_VASIO ); 
        
        this.getModsTBLMD().removeTodasAsLinhas();
    }
    
    public TabelaMD getModsTBLMD() {
        return gui.getModsTBL().getTMD();
    }
    
    public String getDescricao() {
        return gui.getFiltroPNL().getDescricaoTF().getText();
    }
    
    public void setDescricao( String desc ) {
        gui.getFiltroPNL().getDescricaoTF().setText( desc ); 
    }
    
    public boolean isMostrarModsInativas() {
        return gui.getFiltroPNL().getMostrarModsInativasCB().isSelected();
    }
    
    public void setMostrarModsInativas( boolean b ) {
        gui.getFiltroPNL().getMostrarModsInativasCB().setSelected( b ); 
    }
    
    public boolean isCarregarModalidade() {
        return gui.isCarregarModalidade();
    }
    
    public void setCarregarModalidade( boolean b ) {
        gui.setCarregarModalidade( b ); 
    }
    
}
