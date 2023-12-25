package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import org.springframework.http.ResponseEntity;

public interface VendaReportService {
    byte [] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffSet);
}
