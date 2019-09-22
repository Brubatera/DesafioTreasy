package com.desafio.treasy.challenge.controllers;

import com.desafio.treasy.challenge.dtos.EditableDTO;
import com.desafio.treasy.challenge.dtos.NodeDTO;
import com.desafio.treasy.challenge.entities.Node;
import com.desafio.treasy.challenge.services.NodeService;
import javassist.bytecode.DuplicateMemberException;
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
    public List<Node> findAll() {
        return nodeService.findAll();
    }

    @GetMapping("/{parentId}")
    public List<Node> findAllByParentId(@PathVariable Long parentId) {
        return nodeService.findAllByParentId(parentId);
    }

    @PostMapping
    public Long save(@RequestBody NodeDTO nodeDTO) throws ChangeSetPersister.NotFoundException {
        return nodeService.save(nodeDTO.convertNodeDtoToNode());
    }

    @PutMapping
    public Long updateNode(@RequestBody EditableDTO nodeDTO) throws Exception {
        return nodeService.update(nodeDTO);
    }
}