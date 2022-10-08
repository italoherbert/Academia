package academia.gui.modalidade.form;

import academia.Consts;

public class ModalidadeFormGUITO {
    
    private ModalidadeFormGUI gui;
    
    public ModalidadeFormGUITO( ModalidadeFormGUI gui ) {
        this.gui = gui;
    }

    public void setAbilitarPorDataFim( boolean b ) {
        gui.getFormPNL().getDataRegistroTF().setEditable( b );
        gui.getFormPNL().getDescTF().setEditable( b );
        
        gui.getFormPNL().getRemoverBT().setEnabled( b );
        gui.getFormPNL().getSalvarBT().setEnabled( b );
        gui.getFormPNL().getLimparBT().setEnabled( b ); 
    }
    
    public void setDataFimEditavel( boolean b ) {
        gui.getFormPNL().getDataFimTF().setEditable( b ); 
    }
    
    public void setDataCriacaoEditavel( boolean b ) {
        gui.getFormPNL().getDataRegistroTF().setEditable( b ); 
    }        
    
    public void setValorInicialEditavel( boolean b ) {
        gui.getFormPNL().getValorInicialTF().setEditable( b ); 
    }
    
    public void setRemoverBTAbilitado( boolean b ) {
        gui.getFormPNL().getRemoverBT().setEnabled( b ); 
    }
    
    public void setVisivel( boolean b ) {
        gui.setVisible( b ); 
    }
            
    public void limpar( ModFormLimparDriver drv ) {
        this.setDataRegistro( drv.getDataCriacao() ); 
        this.setValorInicial( drv.getValorInicial() );
        

        this.setDescricao( Consts.TEXTO_VASIO );
        this.setDataFim( Consts.TEXTO_VASIO ); 
    }
    
    public String getID() {
        return gui.getFormPNL().getIDTF().getText();
    }
        
    public void setID( String id ) {
        gui.getFormPNL().getIDTF().setText( id ); 
    }
    
    public String getDescricao() {
        return gui.getFormPNL().getDescTF().getText();
    }
                
    public void setDescricao( String desc ) {
        gui.getFormPNL().getDescTF().setText( desc ); 
    }    
    
    public String getDataRegistro() {
        return gui.getFormPNL().getDataRegistroTF().getText();
    }
    
    public void setDataRegistro( String data ) {
        gui.getFormPNL().getDataRegistroTF().setText( data ); 
    }        
    
    public String getDataFim() {
        return gui.getFormPNL().getDataFimTF().getText();
    }
    
    public void setDataFim( String data ) {
        gui.getFormPNL().getDataFimTF().setText( data ); 
    }
    
    public String getValorInicial() {
        return gui.getFormPNL().getValorInicialTF().getText();
    }
    
    public void setValorInicial( String valor ) {
        gui.getFormPNL().getValorInicialTF().setText( valor ); 
    }
    
    public String getValorDiaria() {
        return gui.getFormPNL().getValorDiariaTF().getText();
    }
    
    public void setValorDiaria( String valor ) {
        gui.getFormPNL().getValorDiariaTF().setText( valor ); 
    }
    
}
