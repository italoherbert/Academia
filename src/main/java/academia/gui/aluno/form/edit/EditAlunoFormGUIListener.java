package academia.gui.aluno.form.edit;

public interface EditAlunoFormGUIListener {
    
    public void salvarDadosAlunoBTAcionado( EditAlunoFormGUITO to );
    
    public void removerBTAcionado( EditAlunoFormGUITO to );
    
    public void limparDadosAlunoBTAcionado( EditAlunoFormGUITO to );
          
    public void addModalidadeBTAcionado( EditAlunoFormGUITO to );
    
    public void removeModalidadeBTAcionado( EditAlunoFormGUITO to );
    
    public void historicoModalidadesBTAcionado( EditAlunoFormGUITO to );    
    
    public void registrarNovasMedidasBTAcionado( EditAlunoFormGUITO to );
    
    public void editarMedidasFuncAcionada( EditAlunoFormGUITO to );
    
    public void compararMedidasBTAcionado( EditAlunoFormGUITO to );
        
}
