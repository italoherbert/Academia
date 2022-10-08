package academia.bd;

public class BDException extends Exception {

    public BDException() {
    }

    public BDException(String string) {
        super(string);
    }

    public BDException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public BDException(Throwable thrwbl) {
        super(thrwbl);
    }

    public BDException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }
    
}
