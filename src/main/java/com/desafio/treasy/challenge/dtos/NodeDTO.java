package com.desafio.treasy.challenge.dtos;

import com.desafio.treasy.challenge.entities.Node;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NodeDTO {

    private Long parentId;
    private String code;
    private String description;
    private String detail;

    public Node convertDTOtoNode() {
        return new Node(parentId, code, description, detail);
    }
}
