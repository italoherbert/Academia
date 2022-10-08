package academia.gui.telainicial;

public class TelaInicialGUITO {

    private TelaInicialGUI gui;
    
    public TelaInicialGUITO( TelaInicialGUI gui ) {
        this.gui = gui;
    }
    
    public void setVisivel( boolean visivel ) {
        gui.setVisible( visivel ); 
    }
    
}
