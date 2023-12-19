package com.algaworks.algafood.core.data;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;



public class PageableTranslator {

    /*
    * esse método apenas faz o filter e um novo map para setar o que foi definido no hashmap para fins de ordenação   
    */
    public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
        var orders = pageable.getSort().stream()
                .filter(order -> fieldsMapping.containsKey(order.getProperty()))
                .map(order -> new Sort.Order(order.getDirection(),
                        fieldsMapping.get(order.getProperty())))
                .collect(Collectors.toList());

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(orders));
    }

}
