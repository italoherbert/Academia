package academia.gui.mensalidade.novovalor;

import academia.Consts;

public class NovoValorMensalidadeGUITO {
    
    private NovoValorMensalidadeGUI gui;
    
    public NovoValorMensalidadeGUITO( NovoValorMensalidadeGUI gui ) {
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
    public void limpar( NovoValorMensLimparForm drv ) {
        this.setNovoValor( Consts.TEXTO_VASIO );

        this.setDataRegistro( drv.getDataRegistro() );
    }
    
    public String getValorAtual() {
        return gui.getNovoValorPNL().getValorAtualLV().getText();
    }
    
    public void setValorAtual( String valor ) {
        gui.getNovoValorPNL().getValorAtualLV().setText( valor ); 
    }
    
    public String getNovoValor() {
        return gui.getNovoValorPNL().getNovoValorTF().getText();
    }
    
    public void setNovoValor( String valor ) {
        gui.getNovoValorPNL().getNovoValorTF().setText( valor ); 
    }
    
    public String getDataRegistro() {
        return gui.getNovoValorPNL().getDataRegistroTF().getText();
    }
    
    public void setDataRegistro( String dataReg ) {
        gui.getNovoValorPNL().getDataRegistroTF().setText( dataReg ); 
    }
    
}
