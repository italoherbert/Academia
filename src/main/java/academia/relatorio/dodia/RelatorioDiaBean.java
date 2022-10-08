package academia.relatorio.dodia;

import java.sql.Timestamp;
import java.util.List;

public class RelatorioDiaBean {
 
    Timestamp dataDia;
    List<RelatorioDiaTO> pagamentos;

    public Timestamp getDataDia() {
        return dataDia;
    }

    public void setDataDia(Timestamp dataDia) {
        this.dataDia = dataDia;
    }

    public List<RelatorioDiaTO> getPagamentos() {
        return pagamentos;
    }

    public void setPagamentos(List<RelatorioDiaTO> pagamentos) {
        this.pagamentos = pagamentos;
    }
    
}
