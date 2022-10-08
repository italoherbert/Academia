package academia.bd.to.parcela;

import java.util.ArrayList;
import java.util.List;

public class ParcelasMD {
    
    private ParcelasBean pagSessao;
    
    public ParcelasMD( ParcelasBean pagSessao ) {
        this.pagSessao = pagSessao;
    }
    
    public VisaoParcelaBean buscaParcela( int id ) {
        List<VisaoParcelaBean> parcelas = pagSessao.getVisaoParcelas();
        if ( parcelas != null ) {
            for( VisaoParcelaBean p : parcelas )
                if ( p.getPagsTBLID() == id )
                    return p;
        }
        return null;
    }
    
    public List<VisaoParcelaBean> buscaParcelasPendentes() {
        List<VisaoParcelaBean> lista = new ArrayList();
        List<VisaoParcelaBean> parcelas = pagSessao.getVisaoParcelas();
        if ( parcelas != null ) {
            for( VisaoParcelaBean p : parcelas )
                if ( p.isPendente() )
                    lista.add( p );
        }
        return lista;
    }
    
    
    public VisaoParcelaBean buscaProxParcelaPendente() {
        List<VisaoParcelaBean> parcelas = pagSessao.getVisaoParcelas();
        if ( parcelas != null ) {
            for( VisaoParcelaBean p : parcelas )
                if ( p.isPendente() )
                    return p;
        }
        return null;
    }
    
    public VisaoParcelaBean buscaUltimaParcelaPaga() {
        List<VisaoParcelaBean> parcelas = pagSessao.getVisaoParcelas();
        if ( parcelas != null ) {  
            boolean paga = true;

            int tam = parcelas.size();
            int i = 0;
            while( paga && i < tam ) {
                VisaoParcelaBean p = parcelas.get( i );                       
                if ( p.isPendente() )
                    paga = false; 
                else i++;
            }
            
            if ( paga ) {
                return parcelas.get( tam-1 );
            } else {
                if ( i == 0 ) 
                    return null;
                return parcelas.get( i-1 );
            }                
        }
        return null;
    }
    
}
