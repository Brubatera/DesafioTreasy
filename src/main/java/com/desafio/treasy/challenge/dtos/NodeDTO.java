package com.desafio.treasy.challenge.dtos;

import com.desafio.treasy.challenge.entities.Node;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NodeDTO {

    private Long parentId;
    private String code;
    private String description;
    private String detail;

    public Node convertDTOtoNode() {
        return new Node(parentId, code, description, detail);
    }
}
