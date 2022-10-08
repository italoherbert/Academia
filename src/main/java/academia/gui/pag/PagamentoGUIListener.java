package academia.gui.pag;

public interface PagamentoGUIListener {
            
    public void editarParcelaFuncAcionada( PagamentoGUITO to );
    
    public void esconderParcelasPagasCBAlterado( PagamentoGUITO to );
            
                
    public void atualizaQuitProxDeb( PagamentoGUITO to );
    
    public void atualizaQuitDebs( PagamentoGUITO to );
        
    
    public void quitarProxDebitoBTAcionado( PagamentoGUITO to );

    public void quitarDebitosBTAcionado( PagamentoGUITO to );    
    
    
    public void removerUltPagBTAcionado( PagamentoGUITO to );        
            
}
