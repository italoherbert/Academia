package academia.config;

import academia.Consts;
import academia.MSGConsts;
import academia.gui.GUIConfig;
import academia.relatorio.RelConfig;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import libs.bd.GBDConfig;

public class Config implements GBDConfig, GUIConfig, RelConfig {
    
    private String visualizadorPDF;
    private boolean carregarFuncMedidas;
    private String bddriver;
    private String bdurl;
    private String bdusuario;
    private String bdsenha;
        
    public void carrega() throws Exception {
        Properties p = new Properties();
        p.load( new FileInputStream( Consts.ARQ_CONFIG ) );                      
        
        List<String> erros = new ArrayList();
        bddriver = p.getProperty( Consts.BD_PROP_DRIVER );
        bdurl = p.getProperty( Consts.BD_PROP_URL );
        bdusuario = p.getProperty( Consts.BD_PROP_USUARIO );
        bdsenha = p.getProperty( Consts.BD_PROP_SENHA );
       
        String carregarFuncMedidasS = p.getProperty( Consts.PROP_CARREGAR_FUNC_MEDS );                
        visualizadorPDF = p.getProperty( Consts.PROP_VISUALIZADOR_REL );        

        if ( bddriver == null )
            erros.add( MSGConsts.PROP_NAO_ENCONTRADA.replace( "%1", Consts.BD_PROP_DRIVER ) );
        if ( bdurl == null )
            erros.add( MSGConsts.PROP_NAO_ENCONTRADA.replace( "%1", Consts.BD_PROP_URL ) );
        if ( bdusuario == null )
            erros.add( MSGConsts.PROP_NAO_ENCONTRADA.replace( "%1", Consts.BD_PROP_USUARIO ) );
        if ( bdsenha == null )
            erros.add( MSGConsts.PROP_NAO_ENCONTRADA.replace( "%1", Consts.BD_PROP_SENHA ) );
         
        if ( carregarFuncMedidasS == null ) {
            erros.add( MSGConsts.PROP_NAO_ENCONTRADA.replace( "%1", Consts.PROP_CARREGAR_FUNC_MEDS ) );
        } else {
            carregarFuncMedidas = carregarFuncMedidasS.equalsIgnoreCase( Consts.SIM );            
        }
        
        if ( visualizadorPDF == null ) {
            erros.add( MSGConsts.PROP_NAO_ENCONTRADA.replace( "%1", Consts.PROP_VISUALIZADOR_REL ) );
        }
        
        if ( !erros.isEmpty() ) {                        
            String ers = "";
            for( String e : erros )
                ers += e + "\n";
            throw new Exception( ers );
        }        
        
    }

    public String getVisualizadorPDF() {
        return visualizadorPDF;
    }

    public boolean isCarregarFuncMedidas() {
        return carregarFuncMedidas;
    }

    public String getDriver() {
        return bddriver;
    }

    public String getURL() {
        return bdurl;
    }

    public String getUsuario() {
        return bdusuario;
    }

    public String getSenha() {
        return bdsenha;
    }
        
}
