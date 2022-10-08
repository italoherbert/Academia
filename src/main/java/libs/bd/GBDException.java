package libs.bd;


public class GBDException extends Exception {

    public GBDException() {}

    public GBDException(String string) {
        super(string);
    }

    public GBDException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public GBDException(Throwable thrwbl) {
        super(thrwbl);
    }
    
}
