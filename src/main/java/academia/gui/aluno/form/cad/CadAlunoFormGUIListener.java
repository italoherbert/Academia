package academia.gui.aluno.form.cad;

public interface CadAlunoFormGUIListener {
    
    public void finalizarBTAcionado( CadAlunoFormGUITO to );
        
    public void limparBTAcionado( CadAlunoFormGUITO to );
    
    public boolean validaAlunoForm( CadAlunoFormGUITO to );
    
    public boolean validaMedidasForm( CadAlunoFormGUITO to );
    
    public void aposMostradoPainelMedidas( CadAlunoFormGUITO to );
    
    public void aposMostradoPainelModalidades( CadAlunoFormGUITO to );
    
}
