package academia.gui.matricula;

import libs.gui.tabela.TabelaMD;

public class MatriculaGUITO {
        
    private MatriculaGUI gui;  
    private String titulo = "Matriculas do aluno: "; 

    public MatriculaGUITO( MatriculaGUI gui ) {
        this.gui = gui;
    }
    
    public void setVisivel( boolean b ) {
        gui.setVisible( b ); 
    }
    
    public void setNomeTitulo( String nome ) {
        gui.setTitle( titulo + nome ); 
    }
    
    public TabelaMD getMatsTBL() {
        return gui.getMatsTBL().getTMD();
    }
    
}
