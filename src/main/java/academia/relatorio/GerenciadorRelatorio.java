package academia.relatorio;

import academia.relatorio.alunoemdia.RelatorioAlunoEmDiaGerador;
import academia.relatorio.dodia.RelatorioDiaGerador;
import academia.relatorio.compmed.RelatorioCompMedidasGerador;
import java.io.IOException;

public class GerenciadorRelatorio {
            
    private RelatorioDiaGerador relatorioDia;
    private RelatorioAlunoEmDiaGerador relatorioAlunoEmDia;
    private RelatorioCompMedidasGerador relatorioCompMedidas;
            
    private RelatorioTO rTO;
    
    public GerenciadorRelatorio( RelatorioTO rTO ) {
        this.rTO = rTO;
        this.relatorioDia = new RelatorioDiaGerador( rTO );
        this.relatorioAlunoEmDia = new RelatorioAlunoEmDiaGerador( rTO );
        this.relatorioCompMedidas = new RelatorioCompMedidasGerador( rTO );
    }
                  
    public void visualizarPDF( String arq ) throws VisualizarPDFException {        
        try {
            String visualizadorPDF = rTO.getConfig().getVisualizadorPDF();
            Runtime.getRuntime().exec( visualizadorPDF+" "+arq );
        } catch ( IOException e ) {
            throw new VisualizarPDFException( e );
        }        
    }

    public RelatorioDiaGerador getRelatorioDiaGerador() {
        return relatorioDia;
    }
    
    public RelatorioAlunoEmDiaGerador getRelatorioAlunoEmDiaGerador() {
        return relatorioAlunoEmDia;
    }

    public RelatorioCompMedidasGerador getRelatorioCompMedidasGerador() {
        return relatorioCompMedidas;
    }
    
    
}
