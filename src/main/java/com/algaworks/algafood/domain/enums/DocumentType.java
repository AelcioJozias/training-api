package com.algaworks.algafood.domain.enums;


import lombok.Getter;
import lombok.Setter;

@Getter
public enum DocumentType {

    PDF("pdf"),
    XLS("xls");

    private String type;

    DocumentType(String type) {
        this.type = type;
    }


}
