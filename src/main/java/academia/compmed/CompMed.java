package academia.compmed;

public abstract class CompMed {
    
    public final static int QUANT_MEDIDAS = 14;
    
    private final static int DATA_REG = 0;
    private final static int PESO = 1;
    private final static int ALTURA = 2;
    private final static int BRACO_ESQ = 3;
    private final static int BRACO_DIR = 4;
    private final static int ANTEBRACO_ESQ = 5;
    private final static int ANTEBRACO_DIR = 6;
    private final static int TORAX = 7;
    private final static int CINTURA = 8;
    private final static int BUMBUM = 9;
    private final static int COXA_ESQ = 10;
    private final static int COXA_DIR = 11;
    private final static int PANTURRILHA_ESQ = 12;
    private final static int PANTURRILHA_DIR = 13;    

    private final static int[] LINHAS_INDICES = {
        DATA_REG, 
        PESO, 
        ALTURA, 
        BRACO_ESQ, 
        BRACO_DIR, 
        ANTEBRACO_ESQ, 
        ANTEBRACO_DIR,
        TORAX, 
        CINTURA, 
        BUMBUM, 
        COXA_ESQ, 
        COXA_DIR, 
        PANTURRILHA_ESQ, 
        PANTURRILHA_DIR
    };
    
    private final static String[] TIPOS_MEDIDAS = {
        "Data de registro",
        "Peso (Kg)",
        "Altura (Metros)",
        "Braço esquerdo (cm)",
        "Braço direito (cm)",
        "Antebraço esquerdo (cm)",
        "Antebraço direito (cm)",
        "Torax (cm)",
        "Cintura (cm)",
        "Bumbum (cm)",
        "Coxa esquerda (cm)",
        "Coxa direita (cm)",
        "Panturrilha esquerda (cm)",
        "Panturrilha direita (cm)"
    };
    
    public final static String[] UNIDADE_MED = {
        "",
        "Kg",
        "metros",
        "cm",
        "cm",
        "cm",
        "cm",
        "cm",
        "cm",
        "cm",
        "cm",
        "cm",
        "cm",
        "cm",
    };
    
    public abstract String[] getLinha( int indice );
    
    public abstract void setLinha( int indice, String... linha );        
    
    public int getQuantMedidas() {
        return QUANT_MEDIDAS;
    }
    
    public String getTipoMedida( int indice ) {
        return TIPOS_MEDIDAS[ indice ];
    }
    
    public String getUnidadeMedida( int indice ) {
        return UNIDADE_MED[ indice ];
    }
    
    public int[] getLinhasIndices() {
        return LINHAS_INDICES;
    }
    
    public void setLnDataReg( String... dados ) {
        this.setLinha( DATA_REG, dados ); 
    }    
    
    public void setLnPeso( String... dados ) {
        this.setLinha( PESO, dados ); 
    }
    
    public void setLnAltura( String... dados ) {
        this.setLinha( ALTURA, dados ); 
    }
    
    public void setLnBracoEsquerdo( String... dados ) {
        this.setLinha( BRACO_ESQ, dados ); 
    }
    
    public void setLnBracoDireito( String... dados ) {
        this.setLinha( BRACO_DIR, dados ); 
    }
    
    public void setLnAntebracoEsquerdo( String... dados ) {
        this.setLinha( ANTEBRACO_ESQ, dados ); 
    }
    
    public void setLnAntebracoDireito( String... dados ) {
        this.setLinha( ANTEBRACO_DIR, dados ); 
    }
    
    public void setLnTorax( String... dados ) {
        this.setLinha( TORAX, dados ); 
    }
    
    public void setLnCintura( String... dados ) {
        this.setLinha( CINTURA, dados ); 
    }
    
    public void setLnBumbum( String... dados ) {
        this.setLinha( BUMBUM, dados ); 
    }
    
    public void setLnCoxaEsquerda( String... dados ) {
        this.setLinha( COXA_ESQ, dados ); 
    }
    
    public void setLnCoxaDireita( String... dados ) {
        this.setLinha( COXA_DIR, dados ); 
    }
    
    public void setLnPanturrilhaEsquerda( String... dados ) {
        this.setLinha( PANTURRILHA_ESQ, dados ); 
    }
    
    public void setLnPanturrilhaDireita( String... dados ) {
        this.setLinha( PANTURRILHA_DIR, dados ); 
    }
       
}
