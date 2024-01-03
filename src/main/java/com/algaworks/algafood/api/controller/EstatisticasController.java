package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.dto.input.VendaDiaria;
import com.algaworks.algafood.domain.enums.DocumentType;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping("/vendas-diarias")
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, @RequestParam(required = false, defaultValue = "+00:00") String timeOffSet) {
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffSet);
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro, @RequestParam(required = false,
            defaultValue = "+00:00") String timeOffSet, @RequestParam(required = false, defaultValue = "PDF") DocumentType documentType) {

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffSet, documentType);

        HttpHeaders headers = new HttpHeaders();

        setContentType(documentType, headers);

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytesPdf);
    }

    /**
     * set the file return type and  especify the filename
     * @param documentType the document type for return
     * @param headers headers object to set the parameters
     */
    private static void setContentType(DocumentType documentType, HttpHeaders headers) {
        if(documentType.equals(DocumentType.PDF)){
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment, filename=vendas-diarias.pdf");
        }

        if(documentType.equals(DocumentType.XLS)){
            headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment, filename=vendas-diarias.xls");
        }


    }
}
