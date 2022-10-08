package libs.bd;

public class GBDArquivoConfig {
    
    private String arquivo;
    
    private String propriedadeDriver;
    private String propriedadeURL;
    private String propriedadeUsuario;
    private String propriedadeSenha;

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public String getPropriedadeDriver() {
        return propriedadeDriver;
    }

    public void setPropriedadeDriver(String propriedadeDriver) {
        this.propriedadeDriver = propriedadeDriver;
    }

    public String getPropriedadeURL() {
        return propriedadeURL;
    }

    public void setPropriedadeURL(String propriedadeURL) {
        this.propriedadeURL = propriedadeURL;
    }

    public String getPropriedadeUsuario() {
        return propriedadeUsuario;
    }

    public void setPropriedadeUsuario(String propriedadeUsuario) {
        this.propriedadeUsuario = propriedadeUsuario;
    }

    public String getPropriedadeSenha() {
        return propriedadeSenha;
    }

    public void setPropriedadeSenha(String propriedadeSenha) {
        this.propriedadeSenha = propriedadeSenha;
    }
    
}
