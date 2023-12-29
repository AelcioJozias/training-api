package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.enums.DocumentType;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface VendaReportService {

    /**
     * return a report em byte data type. This report can be an PDF or XLS
     * @param filtro the object filter, pass by the api user
     * @param timeOffSet the time zone of the user (default = '+00:00')
     * @param type the document type, PDF/XLS
     * @return byte of file (pdf or xls)
     */
    byte [] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet, DocumentType type);
}
