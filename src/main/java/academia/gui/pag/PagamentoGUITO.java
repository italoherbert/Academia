package academia.gui.pag;

import academia.Consts;
import libs.gui.tabela.TabelaMD;

public class PagamentoGUITO {
    
    private PagamentoGUI gui;
    private String titulo = "Parcelas do aluno: ";
        
    public PagamentoGUITO(PagamentoGUI gui) {
        this.gui = gui;
    }
        
    public void setVisivel( boolean v ) {
        gui.setVisible( v ); 
    }
    
    public void setNomeTitulo( String nomeAluno ) {
        gui.setTitle( titulo + nomeAluno + "." ); 
    }
    
    public void limpar() {
        this.setProxDeb( Consts.TEXTO_VASIO );
        this.setDescontoProxDeb( Consts.TEXTO_VASIO );
        this.setTotalProxDeb( Consts.TEXTO_VASIO ); 
        this.setQuantParcelasDebs( Consts.TEXTO_VASIO );
        this.setDescontoDebs( Consts.TEXTO_VASIO ); 
        this.setTotalDebs( Consts.TEXTO_VASIO );                
        
        this.setEsconderPPGsSelecionado( false ); 
        this.getPGsTBLMD().removeTodasAsLinhas();
    }
        
    public TabelaMD getPGsTBLMD() {
        return gui.getPGsTBL().getTMD();
    }

    public boolean isEsconderPPGsSelecionado() {
        return gui.getEsconderParcelasPagasCB().isSelected();
    }
    
    public void setEsconderPPGsSelecionado( boolean b ) {
        gui.getEsconderParcelasPagasCB().setSelected( b );
    }
    
    public String getProxDeb() {
        return gui.getQuitarDebPNL().getProxDebTF().getText();
    }
    
    public String getDescontoProxDeb() {
        return gui.getQuitarDebPNL().getDescontoProxDebTF().getText();
    }
    
    public String getTotalProxDeb() {
        return gui.getQuitarDebPNL().getTotalProxDebTF().getText();
    }

    public String getQuantParcelasDebs() {
        return gui.getQuitarDebPNL().getQuantParcelasDebsTF().getText();
    }

    public String getDescontoDebs() {
        return gui.getQuitarDebPNL().getDescontoDebsTF().getText();
    }
    
    public String getTotalDebs() {
        return gui.getQuitarDebPNL().getTotalDebsLV().getText();
    }
    
    public String getPagoAte() {
        return gui.getPagoAteLV().getText();
    }
    
    public String getDiaPag() {
        return gui.getDiaPagLV().getText();
    }

    public void setProxDeb( String debito ) {
        gui.getQuitarDebPNL().getProxDebTF().setText( debito );
    }
    
    public void setDescontoProxDeb( String desconto ) {
        gui.getQuitarDebPNL().getDescontoProxDebTF().setText( desconto ); 
    }
    
    public void setTotalProxDeb( String total ) {
        gui.getQuitarDebPNL().getTotalProxDebTF().setText( total ); 
    }

    public void setQuantParcelasDebs( String quant ) {
        gui.getQuitarDebPNL().getQuantParcelasDebsTF().setText( quant ); 
    }

    public void setDescontoDebs( String desconto ) {
        gui.getQuitarDebPNL().getDescontoDebsTF().setText( desconto ); 
    }
    
    public void setTotalDebs( String total ) {
        gui.getQuitarDebPNL().getTotalDebsLV().setText( total ); 
    }   
    
    public void setPagoAte( String pagoAte ) {
        gui.getPagoAteLV().setText( pagoAte ); 
    }
    
    public void setDiaPag( String diaPag ) {
        gui.getDiaPagLV().setText( diaPag );
    }
    
}
