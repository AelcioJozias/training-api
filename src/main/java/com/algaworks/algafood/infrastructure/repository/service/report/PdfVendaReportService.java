package com.algaworks.algafood.infrastructure.repository.service.report;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.api.dto.input.VendaDiaria;
import com.algaworks.algafood.domain.enums.DocumentType;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class PdfVendaReportService implements VendaReportService {

    @Autowired
    VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet, DocumentType type) {
        try {
            // criar um hashMap para setar os parâmetros do relatório, aqui pode ir também algo como data início/fim
            HashMap<String, Object> parametros = new HashMap<>();

            // setando o locale para o jasper aplicar formatação de valor correta (currency)
            parametros.put("REPORT_LOCALE", new Locale("pt" , "BR"));

            // pesquisa normal dos dados
            List<VendaDiaria> vendaDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);

            // transformando os dados para o tipo que o jasper interpreta para usar no relatório
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendaDiarias);

            if(type.equals(DocumentType.XLS)){
                // get the jasper file
                InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jrxml");

                //create the template compiled template with the jrxml
                JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

                // faz a união de todos os parametros para criar o jasperPrint(arquivo ainda sem um formato definido)
                //  O JasperPrint é a representação do relatório preenchido com dados
                JasperPrint jasperPrintXLS = JasperFillManager.fillReport(jasperReport, parametros, dataSource);

                // Instancia exporter do tipo xls
                JRXlsxExporter exporter = new JRXlsxExporter();

                // instancia do outputStream
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                // SET do tipo de arquivo que vai ser
                exporter.setExporterInput(new SimpleExporterInput(jasperPrintXLS));

                /* Este método configura a saída para o exportador. O método setExporterOutput recebe um objeto do tipo
                 * ExporterOutput, que neste caso é um SimpleOutputStreamExporterOutput construído com o objeto outputStream
                 * criado anteriormente. Isso significa que o resultado da exportação
                 * será escrito no ByteArrayOutputStream em memória.
                 */
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));

                /* Finalmente, este método é chamado para executar a exportação. O relatório representado pelo
                 * JasperPrint fornecido é exportado para o formato XLSX, e o resultado é escrito no ByteArrayOutputStream
                */
                exporter.exportReport();
                return outputStream.toByteArray();
            }
            InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            // esse cara foi feito aqui novamente porque ele não pega o jrxml, mas sim o arquivo .jasper. Diferente do que está dentro do bloco if acima
            JasperPrint jasperPrintPDF = JasperFillManager.fillReport(inputStream, parametros, dataSource);
            return JasperExportManager.exportReportToPdf(jasperPrintPDF);
        } catch (Exception e) {
            throw new ReportException("Não foi possível emitir o relatório", e);
        }
    }
}
