package com.algaworks.algafood.domain.enums;


import lombok.Getter;

@Getter
public enum DocumentType {

    PDF("pdf"),
    XLS("xls");

    private String type;

    DocumentType(String type) {
        this.type = type;
    }


}
