package com.desafio.treasy.challenge.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne
//    @Nullable
//    private Node parent;

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
}
