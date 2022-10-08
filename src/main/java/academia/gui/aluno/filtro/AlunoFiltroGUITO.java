package academia.gui.aluno.filtro;

import academia.Consts;
import libs.gui.tabela.TabelaMD;

public class AlunoFiltroGUITO {
        
    private AlunoFiltroGUI gui;
    
    private String aniverBTTexto = "Aniversariantes";
    
    public AlunoFiltroGUITO( AlunoFiltroGUI gui ) {
        this.gui = gui;
    }
    
    public void limpar() {
        this.setNome( Consts.TEXTO_VASIO );
        this.setMostrarAlunosInativos( false );             
        
        gui.getAlunosTBL().getTMD().removeTodasAsLinhas();
    }
    
    public void setAniverBTQuant( int quant ) {
        gui.getAniversariantesBT().setText( aniverBTTexto + " ("+quant+")" ); 
    }
    
    public String getNome() {
        return gui.getBuscaPNL().getNomeTF().getText();
    }
    
    public void setNome( String nome ) {
        gui.getBuscaPNL().getNomeTF().setText( nome ); 
    }
    
    public boolean isMostrarAlunosInativos() {
        return gui.getBuscaPNL().getMostrarAlunosInativos().isSelected();
    }
    
    public void setMostrarAlunosInativos( boolean b ) {
        gui.getBuscaPNL().getMostrarAlunosInativos().setSelected( b ); 
    }        
            
    public TabelaMD getAlunoTBLMD() {
        return gui.getAlunosTBL().getTMD();
    }
    
}
