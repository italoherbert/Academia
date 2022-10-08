package academia;

public interface MSGConsts {
    
    public final static String SISTEMA_TITULO = "Sistema";
    public final static String LOGIN_TITULO = "Login";
    public final static String USUARIO_TITULO = "Usuário";
    public final static String ALUNO_TITULO = "Alunos";
    public final static String MOD_TITULO = "Modalidades";
    public final static String PAG_TITULO = "Pagamentos";
    public final static String MENS_TITULO = "Mensalidades";
    public final static String RELATORIO_TITULO = "Relatórios";
    public final static String CONFIG_TITULO = "Configurações";
    
    public final static String MARCAR_COMO_INATIVA = " - Inativa";
        
    public final static String CONEXAO_BD_ERRO = 
        "Não foi possível conectar ao Banco de Dados.\nDetalhes do erro: \n\t%1";
    
    public final static String ARQ_CONF_NAO_CARREGADO =
        "Arquivo de configurações não carregado. \nDetalhes do erro: \n\t%1";
    
    public final static String PROP_NAO_ENCONTRADA =
        "Propriedade \"%1\" não encontrada no arquivo de configuração";
    
    public final static String ADMIN_TIPO_ID_NAO_NUMERICO = 
        "Identificador de tipo administrador não numérico.";
    
    public final static String FUNC_TIPO_ID_NAO_NUMERICO = 
        "Identificador de tipo funcionário não numérico.";
    
    public final static String MAT_ID_DIARIA_NAO_NUMERICO =
        "Identificador da matrícula de diária não numérico.";             
    
    
    public final static String SEM_PERMISSAO =
        "O usuário %1 não tem permissões para executar essa operação.";    
    
    public final static String LOGIN_INVALIDO =
        "Não foi possível entrar com usuario: %1.\n"
      + "Verifique se digitou o nome de usuário e senha corretamente!";
        
    public final static String NENHUM_USUARIO_ENCONTRADO = 
        "Nenhum usuário encontrado pelos critérios de busca informados.";
            
    public final static String USUARIO_INATIVO =
        "O usuário %1 está inativo.";
    
    public final static String USUARIO_JA_EXISTE =
        "Já existe um usuário com nome de usuario %1 cadastrado no sistema.";
    
    public final static String NENHUM_USUARIO_SELECIONADO = 
        "Nenhum usuário selecionado.";
    
    public final static String USUARIO_REMOVIDO = 
        "Usuário removido com êxito.";
    
    public final static String PERGUNTA_REMOVER_USUARIO =
        "Tem certeza que deseja remover o usuário %1?";
    
    public final static String USUARIO_NAO_REMOVIDO =
        "Não foi possível remover o usuario %1 \n"
      + "porque existem pagamentos registrados por ele.";
            
    public final static String NOME_USUARIO_OBRIGATORIO = 
        "O campo nome de usuário é de preenchimento obrigatório.";
    
    public final static String SENHA_OBRIGATORIA = 
        "O campo senha é obrigatório.";
    
    public final static String SENHA_REPETIDA_OBRIGATORIA = 
        "O campo senha repetida é obrigatório.";
    
    public final static String SENHAS_DESIGUAIS =
        "A senha informada e a senha repetida não são iguais.\n"
      + "Verifique os campos de senha.";    
    
    public final static String SENHA_ALTERADA =
        "Senha alterada com sucesso.";    
    
    
    
    public final static String INCONSISTENCIA_MAT_ALUNO = 
        "Inconsistência na matrícula do aluno.";
    
    public final static String INCONSISTENCIA_CAD_ALUNO = 
        "Inconsistência na validação do cadastro de aluno.";
    
    public final static String INCONSISTENCIA_EDIT_MEDIDAS = 
        "Inconsistência na edição de medidas do aluno.";
    
    public final static String INCONSISTENCIA_NOVO_VALOR_MENS =
        "Inconsistência na validação do formulario de novo valor da mensalidade.";
    
    public final static String INCONSISTENCIA_CAD_MOD = 
        "Inconsistência no cadastro de modalidade.";
    
    public final static String INCONSISTENCIA_CAD_EDIT_MEDIDAS =
        "Inconsistência no cadastro ou edição de medidas do aluno.";
    
    public final static String ALUNO_SEM_MAT_ATIVA = 
        "Aluno editado sem matrícula ativa.";
    
    
    public final static String DADOS_SALVOS =
        "Dados salvos com êxito.";
    
    public final static String REMOCAO_CANCELADA =
        "Remoção cancelada.";
    
    public final static String REMOCAO_CONCLUIDA =
        "Remoção concluída.";
    
    public final static String ID_NAO_CARREGADO = 
        "Identificador não carregado.";
    
    
    public final static String NENHUM_ALUNO_ENCONTRADO = 
        "Nenhum aluno encontrado pelos critérios de busca informados.";
    
    public final static String ALUNO_JA_EXISTE = 
        "Já existe um aluno cadastrado com nome: \n%1.";
    
    public final static String NOME_OBRIGATORIO =
        "O campo nome é de preenchimento obrigatório.";
    
    public final static String DATA_MAT_INVALIDA =
        "Data de matrícula inválida.";
    
    public final static String DATA_MENOR_QUE_ATUAL =
        "Informe uma data maior ou igual a: %1.";
    
    public final static String DATA_NASC_INVALIDA =
        "Data de nascimento inválida.";
    
    public final static String DESCONTO_INVALIDO = 
        "Desconto inválido.";
        
    public final static String ALTURA_INVALIDA =
        "A altura %1 é inválida.";
    
    public final static String PESO_INVALIDO =
        "O peso %1 é inválido.";
    
    public final static String BRACO_ESQUERDO_INVALIDO =
        "A medida do braço esquerdo informada é inválida. Informado: %1";
    
    public final static String ANTEBRACO_ESQUERDO_INVALIDO =
        "A medida do antebraço esquerdo informada é inválida. Informado: %1";
    
    public final static String BRACO_DIREITO_INVALIDO =
        "A medida do braço direito informada é inválida. Informado: %1";
    
    public final static String ANTEBRACO_DIREITO_INVALIDO =
        "A medida do antebraço direito informada é inválida. Informado: %1";
    
    
    public final static String TORAX_INVALIDO =
        "A medida do torax informada é inválida. Informado: %1";
    
    public final static String CINTURA_INVALIDO =
        "A medida da cintura informada é inválida. Informado: %1";
    
    public final static String BUMBUM_INVALIDO =
        "A medida do bumbum informada é inválida. Informado: %1";
    
    public final static String COXA_ESQUERDA_INVALIDO =
        "A medida da coxa esquerda informada é inválida. Informado: %1";
    
    public final static String PANTURRILHA_ESQUERDA_INVALIDO =
        "A medida da panturrilha esquerda informada é inválida. Informado: %1";
    
    public final static String COXA_DIREITA_INVALIDO =
        "A medida da coxa direita informada é inválida. Informado: %1";
    
    public final static String PANTURRILHA_DIREITA_INVALIDO =
        "A medida da panturrilha direita informada é inválida. Informado: %1";
    
    
    
    public final static String NENHUM_ALUNO_SEL =
        "Nenhum aluno selecionado.";
    
    public final static String PERGUNTA_REMOVER_ALUNO =
        "Tem certeza que deseja remover o aluno \n"
       + "\"%1\"?";
    
    
    
    public final static String PERGUNTA_ENCERRAR_MAT =
        "Tem certeza que deseja encerrar a matrícula do aluno:\n%1?";
    
    public final static String PERGUNTA_RECRIAR_MAT =
        "Já existe uma matricula ativa para o aluno:\n%1\n"
      + "Deseja encerrar a matricula ativa e criar uma nova?";
    
    public final static String PERGUNTA_REMOVER_MAT = 
        "Tem certeza que deseja remover a matrícula selecionada?";
    
    public final static String NENHUMA_MAT_ATIVA =
        "O aluno selecionado não tem nenhuma matrícula ativa.";
            
    public final static String MAT_ENCERRADA = 
        "Matricula encerrada com sucesso.";
    
    public final static String MAT_CRIADA = 
        "Matricula criada com sucesso para o aluno:\n%1.";
            
    
    public final static String NENHUMA_MOD_SELECIONADA =
        "Nenhuma modalidade selecionada.";
    
    public final static String NENHUMA_MOD_ENCONTRADA = 
        "Nenhuma modalidade encontrada pelos critérios de busca informados.";
    
    public final static String MOD_FINALIZADA =
        "Modalidade finalizada com êxito.";
    
    public final static String MOD_JA_FINALIZADA =
        "A modalidade já foi finalizada antes.";
    
    public final static String PERGUNTA_FINALIZAR_MOD =
        "Tem certeza que deseja finalizar a modalidade: \n\"%1\" ?";    
    
    public final static String FINALIZACAO_CANCELADA = 
        "Finalização cancelada.";
    
    public final static String DATA_REGISTRO_MOD_INVALIDA =
        "Data de criação inválida.";
    
    public final static String EXISTE_MOD_ATIVA = 
        "Já existe modalidade ativa com descrição:\n%1.";
    
    public final static String PERGUNTA_REMOVER_MOD =
        "Tem certeza que deseja remover a modalidade \n"
      + "\"%1\"\n e suas mensalidades?";
        
    public final static String EXISTE_MOD_ADD_AO_ALUNO =
        "A modalidade está adicionada a algum aluno. \n"
      + "Por isso, não é possível remover a modalidade sem \n"
      + "causar inconsistências.";
    
    public final static String DESCRICAO_CAMPO_OBRIGATORIO =
        "A descrição é um campo de preenchimento obrigatório.";
    
    public final static String VALOR_INICIAL_MOD_INVALIDO =
        "Valor inicial inválido.";
    
    public final static String NENHUMA_MOD_ADICIONADA =
        "Adicione ao menos uma modalidade.";

  
    
    public final static String NENHUMA_PARCELA_SEL =
        "Nenhuma parcela selecionada.";
    
    public final static String NENHUMA_PARCELA_PENDENTE = 
        "Nenhuma parcela pendente.";
    
    public final static String NENHUMA_PARCELA_PAGA = 
        "Nenhuma parcela paga.";
    
    public final static String QUANT_PARCELAS_INVALIDA =
        "Quantidade de parcelas inválida.";
    
    public final static String MOD_SEM_MENS =
        "Modalidade \"%1\" sem mensalidade associada.";
    
    public final static String TENTATIVA_REM_PARCELA_OUTRO_FUNC =
        "Você não tem permissão de remover pagamento efetuado por: \n%1.";
    
    public final static String VALOR_PROX_DEB_INVALIDO = 
            "Valor do débito inválido!";

    public final static String PAG_REALIZADO =
        "Pagamento realizado com êxito.";
    
    public final static String PAG_REMOVIDO =
        "Pagamento removido com êxito.";
    
    
    public final static String NOVO_VALOR_MENS_INVALIDO = 
        "O valor da mensalidade informado é inválido.";
    
    public final static String VALOR_MENS_ALTERADO =
        "Valor de mensalidade alterado com sucesso.";
    
    
    public final static String PERGUNTA_REMOVER_MEDIDAS =
        "Tem certeza que deseja remover as medidas?";
    
    public final static String MEDIDAS_REMOVIDAS =
        "Medidas removidas com sucesso.";
    
    
    public final static String NENHUMA_MAT_SEL =
        "Nenhuma matricula selecionada.";
    
    public final static String NENHUMA_MEDS_SELECIONADA =
        "Nenhum conjunto de medidas selecionado";
    
    public final static String SEL_DUAS_MEDIDAS =
        "Para comparar grupos de medidas é necessario selecionar dois grupos.";
    
    
    public final static String DATA_REL_DIA_INVALIDA =
        "Data de relatório do dia inválida.";
    
    public final static String RELATORIO_NAO_GERADO = 
        "Ocorreu um erro na geração do relatório. \nDetalhes do erro: \n\t%1";
    
    public final static String RELATORIO_NAO_MOSTRADO = 
        "Ocorreu um erro na visualização do relatório. \nDetalhes do erro: \n\t%1";
    
            
    
    public final static String DATA_FIM_NULA =
        "Em aberto.";
    
    public final static String VAL_TOLER_INVALIDO = 
        "Valor da tolerância informado é inválido.";    
    
    public final static String VAL_TOLER_ALTERADO =
        "Valor de tolerância alterado com sucesso.";
    
}
