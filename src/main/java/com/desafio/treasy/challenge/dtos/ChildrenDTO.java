package com.desafio.treasy.challenge.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChildrenDTO {

    private Long id;
    private String code;
    private String description;
    private Long parentId;
    private String detail;
    private Boolean hasChildren;

    public ChildrenDTO(Long id, String code, String description, Long parentId, String detail, Boolean hasChildren) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.parentId = parentId;
        this.detail = detail;
        this.hasChildren = hasChildren;
    }
}
