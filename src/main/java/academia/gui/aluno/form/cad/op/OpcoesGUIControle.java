package academia.gui.aluno.form.cad.op;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

public class OpcoesGUIControle {
        
    private List<OpcaoGUIControle> opcoes = new ArrayList();
    private int opcaoCorrenteIndice = 0;
    
    private OpcoesGUIControleDriver drv;

    public OpcoesGUIControle( OpcoesGUIControleDriver drv ) {
        this.drv = drv;
    }    
    
    public void addOpcao( String card, JPanel painel ) {
        opcoes.add( new OpcaoGUIControle( card, painel ) );
    }
    
    public void para( String card ) {
        int i = 0;
        for( OpcaoGUIControle op : opcoes ) {
            if ( card.equals( op.getCard() ) ) {
                this.para( i );
                return;
            }
            i++;
        }
    }
    
    public void para( int opcaoIndice ) {
        int tam = opcoes.size();
        if ( opcaoIndice >= 0 && opcaoIndice < tam ) {
            opcaoCorrenteIndice = opcaoIndice;
            
            OpcaoGUIControle opcao = opcoes.get(opcaoCorrenteIndice );
            drv.mostraPNL( opcaoCorrenteIndice, opcao );
        }
    }
    
    public void voltar() {
        if ( opcaoCorrenteIndice > 0 ) {
            opcaoCorrenteIndice--;

            OpcaoGUIControle opcao = opcoes.get( opcaoCorrenteIndice );
            drv.mostraPNL( opcaoCorrenteIndice, opcao );
        }
    }        
    
    public void avancar() {
        int tam = opcoes.size();
        if ( opcaoCorrenteIndice < tam-1 ) {
            opcaoCorrenteIndice++;                
            
            OpcaoGUIControle opcao = opcoes.get( opcaoCorrenteIndice );
            drv.mostraPNL( opcaoCorrenteIndice, opcao );
        }
    }        
        
}
