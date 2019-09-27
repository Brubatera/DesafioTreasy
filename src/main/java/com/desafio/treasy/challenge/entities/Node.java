package com.desafio.treasy.challenge.entities;

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
    private List<Node> babies;

    @Size(max = 50)
    private String code;

    @Size(max = 100)
    private String description;

    @Size(max = 255)
    private String detail;

    private Boolean hasBabies;

    public Node(Long parentId, String code, String description, String detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
        this.parentId = parentId;
    }

    public Node(Long parentId, String code, String description, String detail, List<Node> babies) {
        this.code = code;
        this.description = description;
        this.detail = detail;
        this.parentId = parentId;
        this.babies = babies;
    }
}
