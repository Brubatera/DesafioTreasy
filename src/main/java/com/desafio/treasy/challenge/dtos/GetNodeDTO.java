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
    private List<GetNodeDTO> babies;

    public GetNodeDTO(Long id, Long parentId, String code, String description, String detail, List<Node> babies) {
        this.id = id;
        this.parentId = parentId;
        this.code = code;
        this.description = description;
        this.detail = detail;
        this.babies = convertBabiesToDTO(babies);
    }

    private List<GetNodeDTO> convertBabiesToDTO(List<Node> babies) {
        return babies.stream().map(Node::convertNodeToDTO).collect(Collectors.toList());
    }
}
