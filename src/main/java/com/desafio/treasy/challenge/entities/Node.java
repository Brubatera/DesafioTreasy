package com.desafio.treasy.challenge.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    @OneToMany
    private List<Node> babies;

    @Size(max = 50)
    private String code;

    @Size(max = 100)
    private String description;

    @Size(max = 255)
    private String detail;

    private Boolean hasBabies;
}
