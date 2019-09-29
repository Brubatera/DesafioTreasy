package com.desafio.treasy.challenge.entities;

import com.desafio.treasy.challenge.dtos.GetNodeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long parentId;

    @OneToMany
    private List<Node> children;

    @Size(max = 50)
    private String code;

    @Size(max = 100)
    private String description;

    @Size(max = 255)
    private String detail;

    private Boolean hasChildren;

    public Node(Long parentId, String code, String description, String detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
        this.parentId = parentId;
    }

    public GetNodeDTO convertNodeToDTO() {
        return new GetNodeDTO(id, parentId, code, description, detail, children);
    }
}
