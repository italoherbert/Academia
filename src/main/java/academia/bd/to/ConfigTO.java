package academia.bd.to;

public class ConfigTO extends BDConfigTO {
    
    private int pgTolerancia;
    private boolean autoCarregarUsuarios;
    private boolean autoCarregarModalidades;
    
    public int getPGTolerancia() {
        return pgTolerancia;
    }
    
    public void setPGTolerancia( int t ) {
        this.pgTolerancia = t;
    }

    public boolean isAutoCarregarUsuarios() {
        return autoCarregarUsuarios;
    }

    public void setAutoCarregarUsuarios(boolean autoCarregarUsuarios) {
        this.autoCarregarUsuarios = autoCarregarUsuarios;
    }

    public boolean isAutoCarregarModalidades() {
        return autoCarregarModalidades;
    }

    public void setAutoCarregarModalidades(boolean autoCarregarModalidades) {
        this.autoCarregarModalidades = autoCarregarModalidades;
    }
    
}
