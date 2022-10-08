package academia.controlador.pag;

public class GeraParcelaException extends Exception {

    public GeraParcelaException() {
    }

    public GeraParcelaException(String string) {
        super(string);
    }

    public GeraParcelaException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public GeraParcelaException(Throwable thrwbl) {
        super(thrwbl);
    }

    public GeraParcelaException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
}
