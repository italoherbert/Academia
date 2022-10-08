package academia.bd.to;

public class UsuarioTO {
    
    private int id;
    private int tipoID;
    private String nome;
    private String nomeUsuario;
    private String senha;
    private boolean ativo;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getTipoID() {
        return tipoID;
    }

    public void setTipoID(int tipoID) {
        this.tipoID = tipoID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String usuario) {
        this.nomeUsuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }        

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
}
