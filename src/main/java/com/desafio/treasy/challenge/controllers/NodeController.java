package com.desafio.treasy.challenge.controllers;

import com.desafio.treasy.challenge.dtos.NodeDTO;
import com.desafio.treasy.challenge.dtos.NodeIdDTO;
import com.desafio.treasy.challenge.entities.Node;
import com.desafio.treasy.challenge.services.NodeService;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    public List<Node> findAllByParentId(@PathVariable Long parentId) {
        return nodeService.findAllByParentId(parentId);
    }

    @PostMapping
    public NodeIdDTO save(@RequestBody NodeDTO nodeDTO) throws ChangeSetPersister.NotFoundException {
        return nodeService.save(nodeDTO.convertDTOtoNode(), nodeDTO);
    }

    @PutMapping("/{id}")
    public NodeIdDTO updateNode(@PathVariable(value = "id") Long id, @RequestBody NodeDTO nodeDTO) throws Exception {
        return nodeService.update(id, nodeDTO);
    }
}