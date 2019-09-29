package com.desafio.treasy.challenge.dtos;

import com.desafio.treasy.challenge.entities.Node;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class GetNodeDTO {

    private Long id;
    private String code;
    private String description;
    private Long parentId;
    private String detail;
    private List<GetNodeDTO> children;

    public GetNodeDTO(Long id, Long parentId, String code, String description, String detail, List<Node> children) {
        this.id = id;
        this.parentId = parentId;
        this.code = code;
        this.description = description;
        this.detail = detail;
        this.children = convertChildrenToDto(children);
    }

    private List<GetNodeDTO> convertChildrenToDto(List<Node> children) {
        return children.stream().map(Node::convertNodeToDTO).collect(Collectors.toList());
    }
}
