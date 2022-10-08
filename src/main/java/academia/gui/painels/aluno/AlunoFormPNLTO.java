package academia.gui.painels.aluno;

import academia.Consts;
import academia.gui.aluno.pnl.LimparAlunoGUICallback;

public class AlunoFormPNLTO {
    
    private AlunoFormPNLTODriver drv;
    
    public AlunoFormPNLTO( AlunoFormPNLTODriver drv ) {
        this.drv = drv;
    }
    
    public void setDescontoPNLVisivel( boolean visivel ) {
        drv.getFormPNL().getDescontoPNL().setVisible( visivel ); 
    }
    
    public void limpar( LimparAlunoGUICallback drv ) {
        this.setDataMatricula( drv.getDataMatricula() ); 
             
        this.setDesconto( "0" ); 
        this.setNome( Consts.TEXTO_VASIO );
        this.setDataNasc( Consts.TEXTO_VASIO ); 
        this.setObs( Consts.TEXTO_VASIO ); 
    }        
            
    public String getID() {
        return drv.getFormPNL().getIDTF().getText();
    }
    
    public String getDataMatricula() {
        return drv.getFormPNL().getDataMatriculaTF().getText();
    }
    
    public String getDataNasc() {
        return drv.getFormPNL().getDataNascTF().getText();
    }
    
    public String getNome() {
        return drv.getFormPNL().getNomeTF().getText();
    }
                
    public String getObs() {
        return drv.getFormPNL().getObsTA().getText();
    }
    
    public String getDesconto() {
        return drv.getFormPNL().getDescontoTF().getText();
    }
        
    public void setID( String id ) {
        drv.getFormPNL().getIDTF().setText( id ); 
    }
    
    public void setNome( String nome ) {
        drv.getFormPNL().getNomeTF().setText( nome );
    }
    
    public void setDataMatricula( String data ) {
        drv.getFormPNL().getDataMatriculaTF().setText( data ); 
    }
    
    public void setDataNasc( String dataNasc ) {
        drv.getFormPNL().getDataNascTF().setText( dataNasc ); 
    }
    
    public void setObs( String obs ) {
        drv.getFormPNL().getObsTA().setText( obs ); 
    }
    
    public void setDesconto( String desconto ) {
        drv.getFormPNL().getDescontoTF().setText( desconto ); 
    }
       
}
