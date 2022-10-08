package academia.gui.painels.medidas;

import academia.Consts;

public class MedidasFormPNLTO {    
    
    private MedidasFormPNLTODriver drv;

    public MedidasFormPNLTO( MedidasFormPNLTODriver drv ) {
        this.drv = drv;
    }
     
    public void setDataRegVisivel( boolean visivel ) {
        drv.getFormPNL().getDataRegPNL().setVisible( visivel ); 
    }
    
    public void limpar( LimparMedidasPNLCallBack cb ) {
        this.setDataReg( cb.getMedidasDataReg() ); 
        this.setPeso( Consts.TEXTO_VASIO ); 
        this.setAltura( Consts.TEXTO_VASIO );
        this.setBracoEsquerdo( Consts.TEXTO_VASIO );
        this.setAntebracoEsquerdo( Consts.TEXTO_VASIO ); 
        this.setBracoDireito( Consts.TEXTO_VASIO );
        this.setAntebracoDireito( Consts.TEXTO_VASIO );
        this.setTorax( Consts.TEXTO_VASIO );
        this.setCintura( Consts.TEXTO_VASIO );
        this.setBumbum( Consts.TEXTO_VASIO );
        this.setCoxaEsquerda( Consts.TEXTO_VASIO );
        this.setPanturrilhaEsquerda( Consts.TEXTO_VASIO );         
        this.setCoxaDireita( Consts.TEXTO_VASIO );
        this.setPanturrilhaDireita( Consts.TEXTO_VASIO ); 
    }
        
    public String getID() {
        return drv.getFormPNL().getIDTF().getText();
    }
    
    public String getDataReg() {
        return drv.getFormPNL().getDataRegTF().getText();
    }
        
    public String getPeso() {
        return drv.getFormPNL().getPesoTF().getText();
    }
    
    public String getAltura() {
        return drv.getFormPNL().getAlturaTF().getText();
    }
    
    public String getBracoEsquerdo() {
        return drv.getFormPNL().getBracoEsquerdoTF().getText();
    }
    
    public String getAntebracoEsquerdo() {
        return drv.getFormPNL().getAntebracoEsquerdoTF().getText();
    }
    
    public String getBracoDireito() {
        return drv.getFormPNL().getBracoDireitoTF().getText();
    }
    
    public String getAntebracoDireito() {
        return drv.getFormPNL().getAntebracoDireitoTF().getText();
    }
    
    public String getTorax() {
        return drv.getFormPNL().getToraxTF().getText();
    }
    
    public String getCintura() {
        return drv.getFormPNL().getCinturaTF().getText();
    }
    
    public String getBumbum() {
        return drv.getFormPNL().getBumbumTF().getText();
    }
    
    public String getCoxaEsquerda() {
        return drv.getFormPNL().getCoxaEsquerdaTF().getText();
    }
    
    public String getPanturrilhaEsquerda() {
        return drv.getFormPNL().getPanturrilhaEsquerdaTF().getText();
    } 
    
    public String getCoxaDireita() {
        return drv.getFormPNL().getCoxaDireitaTF().getText();
    }
    
    public String getPanturrilhaDireita() {
        return drv.getFormPNL().getPanturrilhaDireitaTF().getText();
    } 
       
    public void setID( String id ) {
        drv.getFormPNL().getIDTF().setText( id ); 
    }
    
    public void setDataReg( String daraReg ) {
        drv.getFormPNL().getDataRegTF().setText( daraReg ); 
    }
    
    public void setPeso( String peso ) {
        drv.getFormPNL().getPesoTF().setText( peso );
    }
    
    public void setAltura( String altura ) {
        drv.getFormPNL().getAlturaTF().setText( altura );
    }
    
    public void setBracoEsquerdo( String braco ) {
        drv.getFormPNL().getBracoEsquerdoTF().setText( braco );
    }
    
    public void setAntebracoEsquerdo( String antebraco ) {
        drv.getFormPNL().getAntebracoEsquerdoTF().setText( antebraco ); 
    }
    
    public void setBracoDireito( String braco ) {
        drv.getFormPNL().getBracoDireitoTF().setText( braco );
    }
    
    public void setAntebracoDireito( String antebraco ) {
        drv.getFormPNL().getAntebracoDireitoTF().setText( antebraco ); 
    }
    
    public void setTorax( String torax ) {
        drv.getFormPNL().getToraxTF().setText( torax ); 
    }
    
    public void setCintura( String cintura ) {
        drv.getFormPNL().getCinturaTF().setText( cintura );
    }
    
    public void setBumbum( String gluteos ) {
        drv.getFormPNL().getBumbumTF().setText( gluteos );
    }
    
    public void setCoxaEsquerda( String coxa ) {
        drv.getFormPNL().getCoxaEsquerdaTF().setText( coxa );
    }
    
    public void setPanturrilhaEsquerda( String panturrilha ) {
        drv.getFormPNL().getPanturrilhaEsquerdaTF().setText( panturrilha );
    }
    
    public void setCoxaDireita( String coxa ) {
        drv.getFormPNL().getCoxaDireitaTF().setText( coxa );
    }
    
    public void setPanturrilhaDireita( String panturrilha ) {
        drv.getFormPNL().getPanturrilhaDireitaTF().setText( panturrilha );
    }
        
}
