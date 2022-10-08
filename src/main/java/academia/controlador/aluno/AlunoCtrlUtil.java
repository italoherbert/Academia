package academia.controlador.aluno;

import academia.Consts;
import academia.GUIConsts;
import academia.MSGConsts;
import academia.bd.BD;
import academia.bd.BDConsts;
import academia.bd.bean.ModalidadeBean;
import academia.bd.to.AlunoTO;
import academia.bd.dao.DAOException;
import academia.bd.to.BDConfigTO;
import academia.bd.to.ConfigTO;
import academia.bd.to.MedidasTO;
import academia.bd.to.ModalidadeTO;
import academia.controlador.ControladorTO;
import academia.gui.GUI;
import academia.gui.aluno.filtro.AlunoFiltroGUITO;
import academia.gui.aluno.form.edit.EditAlunoFormGUITO;
import academia.gui.aluno.pnl.AlunoFormGUITO;
import academia.gui.aluno.form.edit.medidas.EditMedidasGUITO;
import academia.gui.painels.selecionarmods.SelecionarModsPNLTO;
import academia.gui.painels.medidas.MedidasFormPNLTO;
import academia.util.DataUtil;
import academia.util.ModalidadeFormatador;
import academia.util.NumeroFormatador;
import java.awt.Color;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import libs.gui.tabela.TabelaMD;

public class AlunoCtrlUtil {
    
    private ControladorTO cTO;
    
    private boolean filtrarApenasAniversariantes = false;
    
    public AlunoCtrlUtil( ControladorTO cTO ) {
        this.cTO = cTO;
    }

    public int filtrar( AlunoFiltroGUITO to ) throws DAOException {
        BD bd = cTO.getBD();        
        BDConfigTO bdConfig = cTO.getBDConfig();
        
        List<AlunoTO> alunos = new ArrayList( 0 );        
        
        if ( filtrarApenasAniversariantes ) {
            alunos = bd.getAlunoDAO().buscaAlunosCompletandoAno();
        } else {        
            String nome = to.getNome();        
            boolean mi = to.isMostrarAlunosInativos();                

            if ( !nome.isEmpty() ) {            
                int diariaAlunoID = bdConfig.getDiariaAlunoID();
                if ( nome.trim().equals( Consts.CARACTER_BUSCA_TODOS ) ) { 
                    alunos = bd.getAlunoDAO().buscaTodos( mi, diariaAlunoID );
                } else {
                    alunos = bd.getAlunoDAO().filtra( nome, mi, diariaAlunoID );
                }
            }
        }
              
        return this.filtrar( to, alunos );
    }
    
    public void atualizaQuantAnivers() throws DAOException {
        GUI gui = cTO.getGUI();
        BD bd = cTO.getBD();
        
        int quant = bd.getAlunoDAO().quantAlunosCompletandoAno();
        gui.getJP().getAlunoGUI().getFiltroGUI().getTO().setAniverBTQuant( quant );          
    }
        
    public int filtrar( AlunoFiltroGUITO to, List<AlunoTO> alunos ) throws DAOException {        
        BD bd = cTO.getBD();
        DataUtil df = cTO.getDataUtil();
        
        TabelaMD alunoTBLMD = to.getAlunoTBLMD();                        
        alunoTBLMD.removeTodasAsLinhas();
        
        int i = 0;
        Map<Integer, Color> cores = new HashMap();
        for( AlunoTO a : alunos ) {                
            String idadeStr = Consts.NAO_INFORMADO;
            if ( a.getDataNasc() != null ) {
                int idade = df.calculaIdade( a.getDataNasc() );
                idadeStr = String.valueOf( idade );                
            }
                        
            boolean ativo = ( a.getMatriculaCorrente() != BDConsts.ID_NULO );                 
            
            String estadoStr = Consts.ATIVIDADE_INATIVO;
            if ( ativo ) {        
                Date pagoAte = bd.getPagamentoBO().pagoAte( a.getMatriculaCorrente() );
                pagoAte = df.apenasData( pagoAte );
                
                Date dataAtual = df.apenasData( new Date() );                
                
                if ( pagoAte.compareTo( dataAtual ) >= 0 ) {
                    estadoStr = Consts.ESTADO_ADIMPLENTE;
                    cores.put( i, GUIConsts.COR_ALUNO_ADIMPLENTE );
                } else {
                    ConfigTO configBDTO = bd.getConfigDAO().busca();
                    Calendar pagoAteC = Calendar.getInstance();
                    pagoAteC.setTime( pagoAte );
                    pagoAteC.add( Calendar.DAY_OF_MONTH, configBDTO.getPGTolerancia() );                               

                    pagoAte = df.apenasData( pagoAteC.getTime() );                                          
                    if ( pagoAte.compareTo( dataAtual ) >= 0 ) {
                        estadoStr = Consts.ESTADO_EM_TOLERANCIA;
                        cores.put( i, GUIConsts.COR_PARCELA_TOLERANCIA );
                    } else {                         
                        estadoStr = Consts.ESTADO_INADIMPLENTE;           
                        cores.put( i, GUIConsts.COR_PARCELA_ATRAZADA );
                    }
                }
            } else {
                cores.put(i, GUIConsts.COR_INATIVO );
            }

            to.getAlunoTBLMD().addLinha( new String[]{
                String.valueOf( a.getID() ),
                a.getNome(),
                idadeStr,
                estadoStr,
                ( ativo ? Consts.ATIVIDADE_ATIVO : Consts.ATIVIDADE_INATIVO ),
                a.getObs()
            });
            
            i++;
        }
        
        alunoTBLMD.pintaTabela( cores );
        
        return alunos.size();
    }           

    
    
    public List<String> modalidadesNaoFinalizadas( Timestamp dataMat ) throws DAOException {
        BD bd = cTO.getBD();
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();        
                
        List<ModalidadeTO> modalidades = bd.getModalidadeDAO().buscaTodas();
        List<String> mods = new ArrayList();
        for( ModalidadeTO m : modalidades ) 
            if ( m.getDataFim() == null && dataMat.compareTo( m.getDataInicio() ) >= 0 )
                mods.add( mf.formataModalidade( m.getID(), m.getDescricao() ) );
            
        return mods;            
    }
    
    public void carregarTabelaMedidas( EditMedidasGUITO eMedsTO, int matID ) throws DAOException {
        BD bd = cTO.getBD();
        
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        List<MedidasTO> listaMedidas = bd.getMatriculaDAO().buscaMedidas( matID );
        
        TabelaMD medidasTBLMD = eMedsTO.getMedidasTBLMD();        
        medidasTBLMD.removeTodasAsLinhas();
        
        for( MedidasTO meds : listaMedidas ) {
            medidasTBLMD.addLinha( new String[] {
                String.valueOf( meds.getID() ),
                String.valueOf( meds.getPeso() ),
                nf.formatoFlutuante( meds.getAltura() ) 
            } );
        }
    }
    
    public void carregarListasModalidades( SelecionarModsPNLTO emTO, int matID ) throws DAOException {
        BD bd = cTO.getBD();
                                                        
        List<ModalidadeBean> alMods = bd.getMatriculaDAO().buscaModalidades( matID );
        List<ModalidadeTO> tdsMods = bd.getModalidadeDAO().buscaTodas();        
                
        List<String> alunoModsVet = this.calculaAlunoMods( alMods, emTO );        
        List<String> outrasModsVet = this.calculaOutrasMods( alMods, tdsMods );
                
        emTO.setAlunoMods( alunoModsVet ); 
        emTO.setOutrasMods( outrasModsVet );                 
    }
    
    private List<String> calculaAlunoMods( List<ModalidadeBean> alMods, SelecionarModsPNLTO emTO ) {
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
              
        Map<Integer, Color> coresMap = new HashMap();
        int i = 0;
        
        List<String> modalidades = new ArrayList();
        for( ModalidadeBean m : alMods ) {
            if ( m.getDataEncerramento() == null ) {
                String descF = mf.formataModalidade( m.getMatModID(), m.getDescricao() );
                modalidades.add( descF );                                    
                
                if ( m.getDataFim() != null )
                    coresMap.put( i, GUIConsts.COR_INATIVO );
            }
            
            i++;
        }       
        
        emTO.getListaAddMD().pintaLista( coresMap ); 
        
        return modalidades;
    }
    
    private List<String> calculaOutrasMods( List<ModalidadeBean> alMods, 
                                            List<ModalidadeTO> tdsMods ) {
        ModalidadeFormatador mf = cTO.getModalidadeFormatador();
        
        List<String> outrasMods = new ArrayList();
        for( ModalidadeTO m : tdsMods ) {            
            if ( m.getDataFim() == null ) {
                int tam = alMods.size();
                boolean achou = false;
                for( int i = 0; !achou && i < tam; i++ ) {
                    ModalidadeBean alM = alMods.get( i );
                    if ( alM.getID() == m.getID() )
                        if ( alM.getDataEncerramento() == null )
                            achou = true;                
                }            
                if ( !achou ) {                                        
                    String descF = mf.formataModalidade( m.getID(), m.getDescricao() );
                    outrasMods.add( descF );
                }
            }
        }        
        return outrasMods;
    }
    
    public List<String> validaCadMedidasForm( MedidasFormPNLTO medidas ) {                
        NumeroFormatador nf = cTO.getNumeroFormatador();
                
        List<String> erros = new ArrayList();
                        
        try {
            Integer.parseInt( medidas.getPeso() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.PESO_INVALIDO.replace("%1", medidas.getPeso() );
            erros.add( msg );
        }
        
        try {
            nf.valorFlutuante( medidas.getAltura() );
        } catch ( ParseException e ) {
            String msg = MSGConsts.ALTURA_INVALIDA.replace("%1", medidas.getAltura() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getBracoEsquerdo() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.BRACO_ESQUERDO_INVALIDO.replace("%1", medidas.getBracoEsquerdo() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getAntebracoEsquerdo() ); 
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.ANTEBRACO_ESQUERDO_INVALIDO.replace("%1", medidas.getAntebracoEsquerdo() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getBracoDireito() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.BRACO_DIREITO_INVALIDO.replace("%1", medidas.getBracoDireito() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getAntebracoDireito() ); 
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.ANTEBRACO_DIREITO_INVALIDO.replace("%1", medidas.getAntebracoDireito() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getTorax() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.TORAX_INVALIDO.replace("%1", medidas.getTorax() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getCintura() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.CINTURA_INVALIDO.replace("%1", medidas.getCintura() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getBumbum());
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.BUMBUM_INVALIDO.replace("%1", medidas.getBumbum() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getCoxaEsquerda() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.COXA_ESQUERDA_INVALIDO.replace("%1", medidas.getCoxaEsquerda() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getPanturrilhaEsquerda() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.PANTURRILHA_ESQUERDA_INVALIDO.replace("%1", medidas.getPanturrilhaEsquerda() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getCoxaDireita() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.COXA_DIREITA_INVALIDO.replace("%1", medidas.getCoxaDireita() );
            erros.add( msg );
        }
        
        try {
            Integer.parseInt( medidas.getPanturrilhaDireita() );
        } catch ( NumberFormatException e ) {
            String msg = MSGConsts.PANTURRILHA_DIREITA_INVALIDO.replace("%1", medidas.getPanturrilhaDireita() );
            erros.add( msg );
        }
        
        return erros;
    }
    
    public List<String> validaCadAlunoForm( AlunoFormGUITO to ) throws DAOException {
        BD bd = cTO.getBD();
        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        List<String> erros = new ArrayList<String>();
        
        if ( to.getNome().isEmpty() ) {
            erros.add( MSGConsts.NOME_OBRIGATORIO );                         
        } else {
            String nome = to.getNome();               
            boolean existe = bd.getAlunoDAO().existePorNome( nome ); 
            if ( existe ) {
                String msg = MSGConsts.ALUNO_JA_EXISTE.replace( "%1", nome );
                erros.add( msg );
            }
        }                
                
        try {
            df.converteData( to.getDataMatricula() );
        } catch ( ParseException e ) {
            erros.add( MSGConsts.DATA_MAT_INVALIDA );                        
        }               
                
        try {
            nf.valorFlutuante( to.getDesconto() );
        } catch ( ParseException e ) {
            erros.add( MSGConsts.DESCONTO_INVALIDO );
        }
       
        return erros;
    }
    
    public List<String> validaEditAlunoForm( EditAlunoFormGUITO to ) throws DAOException {
        BD bd = cTO.getBD();
        DataUtil df = cTO.getDataUtil();
        NumeroFormatador nf = cTO.getNumeroFormatador();
        
        List<String> erros = new ArrayList();
        
        if ( to.getNome().isEmpty() ) {
            erros.add( MSGConsts.NOME_OBRIGATORIO );                         
        } else {
            int alunoID = Integer.parseInt( to.getID() );
            String nome = to.getNome();
            String nomeAlunoAtual = bd.getAlunoDAO().buscaNome( alunoID );                    
                
            if ( !nome.equalsIgnoreCase( nomeAlunoAtual ) ) {                
                boolean existe = bd.getAlunoDAO().existePorNome( nome ); 
                if ( existe ) {                            
                    String msg = MSGConsts.ALUNO_JA_EXISTE.replace( "%1", nome );
                    erros.add( msg );
                }
            }
        }        
      
        int alunoID = Integer.parseInt( to.getID() );
        int currMatID = bd.getAlunoDAO().buscaMatCorr( alunoID );
        if ( currMatID != BDConsts.ID_NULO ) {
            try {
                nf.valorFlutuante( to.getDesconto() );
            } catch ( ParseException e ) {
                erros.add( MSGConsts.DESCONTO_INVALIDO );
            }
        
            try {
                df.converteData( to.getDataMatricula() );
            } catch ( ParseException e ) {
                erros.add( MSGConsts.DATA_MAT_INVALIDA );                        
            }
        }
                
        return erros;
    } 

    public boolean isFiltrarApenasAniversariantes() {
        return filtrarApenasAniversariantes;
    }

    public void setFiltrarApenasAniversariantes(boolean filtrarApenasAniversariantes) {
        this.filtrarApenasAniversariantes = filtrarApenasAniversariantes;
    }
    
}
