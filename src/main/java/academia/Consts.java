package academia;

import java.awt.Color;
import java.awt.Font;

public class Consts {

    public final static String ARQ_CONFIG = "config.cfg";
        
    public final static String BD_PROP_DRIVER = "bd.driver";
    public final static String BD_PROP_URL = "bd.url";
    public final static String BD_PROP_USUARIO = "bd.usuario";
    public final static String BD_PROP_SENHA = "bd.senha";

    public final static String PROP_USUARIO_ADMIN_TIPO_ID = "usuario.tipo.admin";
    public final static String PROP_USUARIO_FUNC_TIPO_ID = "usuario.tipo.func";
    
    public final static String PROP_DIARIA_ALUNO_ID = "aluno.id.diaria";
    
    public final static String PROP_VISUALIZADOR_REL = "relatorio.visualizador";    
    public final static String PROP_CARREGAR_FUNC_MEDS = "func.medidas.carregar";
    
    public final static String SIM = "S";
    
    public final static String REL_DIA_ARQUIVO = "relatorio/rel-dia.pdf";
    public final static String REL_ALUNOS_EM_DIA_ARQUIVO = "relatorio/rel-alunos-em-dia.pdf";
    public final static String REL_COMP_MEDIDAS_ARQUIVO = "relatorio/rel-comp-medidas.pdf";
    
    public final static float REL_ALUNOS_EM_DIA_LARGURA = 400f;
    public final static float REL_ALUNOS_EM_DIA_ALTURA = 300f;    
               
    public final static String DATA_MASCARA = "##/##/####";
    public final static String DATA_CARACS_VALIDOS = "0123456789";    
    
    public final static String DATA_MASCARA_SDF = "dd/MM/yyyy";
    public final static String DATA_HORA_MASCARA_SDF = "dd/MM/yyyy, hh:mm:ss";
    public final static String HORA_MASCARA_SDF = "HH:mm:ss";
    public final static String DATA_MES_SDF = "MM/yyyy";
    public final static String DIA_SDF = "dd";
    
    public final static String NUMERO_REAL_MASCARA = "R$ 0.00";
    public final static String NUMERO_FLUTUANTE_MASCARA = "0.00";
    
    public final static String NUMERO_FATOR_MASCARA = "0.000000";
    public final static String NUMERO_PORCENTAGEM = "0.00%";
    
    public final static String TEXTO_VASIO = "";
    public final static String PENDENTE_TEXTO = "pendente";
    public final static String NAO_FINALIZADA_TEXTO = "Não finalizada!";
    public final static String NAO_ENCERRADA_TEXTO = "Não encerrada!";
            
    public final static String ATIVIDADE_ATIVO = "ativo";
    public final static String ATIVIDADE_INATIVO = "inativo";
    
    public final static String ESTADO_ADIMPLENTE = "adimplente";
    public final static String ESTADO_INADIMPLENTE = "inadimplente";
    public final static String ESTADO_EM_TOLERANCIA = "em tolerância";
    
    public final static String NAO_INFORMADO = "Não informado(a)";    
                           
    public final static String SIM_TEXTO = "Sim";
    public final static String NAO_TEXTO = "Não";
    
    public final static String VALOR_DIARIA_NULO_TXT = "Não registrado";
    
    public final static int QDIAS_EM_MES = 30;
    public final static int QMS_EM_DIA = 86400000;
    public final static String CARACTER_BUSCA_TODOS = "*";        
    
    public final static Font TEXTO_FONTE = new Font( Font.SANS_SERIF, Font.PLAIN, 16 );    
    public final static Color TEXTO_COR = Color.BLUE;
    
}
