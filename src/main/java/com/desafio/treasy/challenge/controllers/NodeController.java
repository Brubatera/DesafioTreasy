package com.desafio.treasy.challenge.controllers;

import com.desafio.treasy.challenge.Exceptions.FatherCantBecomeSonOfHimself;
import com.desafio.treasy.challenge.Exceptions.IdNotFoundException;
import com.desafio.treasy.challenge.Exceptions.ParentIdNotFoundException;
import com.desafio.treasy.challenge.dtos.NodeDTO;
import com.desafio.treasy.challenge.dtos.NodeIdDTO;
import com.desafio.treasy.challenge.entities.Node;
import com.desafio.treasy.challenge.services.NodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/node")
@AllArgsConstructor
public class NodeController {

    private NodeService nodeService;

    @GetMapping
    public List<Node> findAllAndParentIdIsNull() {
        return nodeService.findAllAndParentIdIsNull();
    }

    @GetMapping("/{parentId}")
    public List<Node> findAllByParentId(@PathVariable Long parentId) throws ParentIdNotFoundException {
        return nodeService.findAllByParentId(parentId);
    }

    @PostMapping
    public NodeIdDTO save(@RequestBody NodeDTO nodeDTO) throws ParentIdNotFoundException {
        return nodeService.save(nodeDTO.convertDTOtoNode(), nodeDTO);
    }

    @PutMapping("/{id}")
    public NodeIdDTO updateNode(@PathVariable(value = "id") Long id, @RequestBody NodeDTO nodeDTO) throws FatherCantBecomeSonOfHimself, IdNotFoundException {
        return nodeService.update(id, nodeDTO);
    }
}