package academia.gui.aluno.form.cad.op;

import javax.swing.JPanel;

public class OpcaoGUIControle {
    
    private String card;
    private JPanel painel;
    
    public OpcaoGUIControle() {}
    
    public OpcaoGUIControle( String card, JPanel painel ) {
        this.card = card;
        this.painel = painel;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public JPanel getPainel() {
        return painel;
    }

    public void setPainel(JPanel painel) {
        this.painel = painel;
    }
    
}
