package com.algaworks.algafood.infrastructure.repository.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Autowired
    VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet) {
        try {
            // primeiro pega o arquivo jasper. esse é um jeito de pegar o que tá definido no classpath
            InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            // criar um hashMap para setar os parâmetros do relatório, aqui pode ir também algo como data início/fim
            HashMap<String, Object> parametros = new HashMap<>();

            // setando o locale para o jasper aplicar formatação de valor correta (currency)
            parametros.put("REPORT_LOCALE", new Locale("pt" , "BR"));

            // pesquisa normal dos dados
            List<VendaDiaria> vendaDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);

            // transformando os dados para o tipo que o jasper interpreta para usar no relatório
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendaDiarias);

            /*
            * junção de tudo que foi criado até aqui. o stream(arquivo), parametros e os dados
            * que foram estraídos acima
            * */
            JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

            // esse é o ponto onde eu defino o que eu quero fazer, pdf, excel etc...
            return JasperExportManager.exportReportToPdf(jasperPrint);


        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir o relatório", e);
        }
    }
}
