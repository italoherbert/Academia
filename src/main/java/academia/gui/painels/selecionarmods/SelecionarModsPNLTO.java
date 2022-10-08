package academia.gui.painels.selecionarmods;

import java.util.List;
import libs.gui.listaadd.ListaAddMD;

public class SelecionarModsPNLTO {
    
    private SelecionarModsPNLTODriver drv; 
    
    public SelecionarModsPNLTO( SelecionarModsPNLTODriver drv ) {
        this.drv = drv;
    }
    
    public void limpar() {        
        drv.getSelecionarModsPNL().getListaAdd().getMD().limpar();
    }
    
    public ListaAddMD getListaAddMD() {
        return drv.getSelecionarModsPNL().getListaAdd().getMD();
    }
        
    public void setAlunoMods( List<String> mods ) {                        
        drv.getSelecionarModsPNL().getListaAdd().getMD().setItensLista1( mods );
    }
    
    public void setOutrasMods( List<String> mods ) {        
        drv.getSelecionarModsPNL().getListaAdd().getMD().setItensLista2( mods );
    }
    
    public String[] getAlunoMods() {
        return drv.getSelecionarModsPNL().getListaAdd().getMD().getItens1();
    }
    
    public String[] getOutrasMods() {
        return drv.getSelecionarModsPNL().getListaAdd().getMD().getItens2();
    }
    
    public List<String> getAlunoModsSelecionadas() {
        return drv.getSelecionarModsPNL().getListaAdd().getMD().getItens1Selecionados();
    }
    
    public List<String> getOutrasModsSelecionadas() {
        return drv.getSelecionarModsPNL().getListaAdd().getMD().getItens2Selecionados();
    }
    
}
