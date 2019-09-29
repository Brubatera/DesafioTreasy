package com.desafio.treasy.challenge.controllers;

import com.desafio.treasy.challenge.dtos.ChildrenDTO;
import com.desafio.treasy.challenge.dtos.GetNodeDTO;
import com.desafio.treasy.challenge.dtos.NodeDTO;
import com.desafio.treasy.challenge.dtos.NodeIdDTO;
import com.desafio.treasy.challenge.exceptions.FatherCantBecomeSonOfHimself;
import com.desafio.treasy.challenge.exceptions.IdNotFoundException;
import com.desafio.treasy.challenge.exceptions.ParentIdNotFoundException;
import com.desafio.treasy.challenge.services.NodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/node")
@AllArgsConstructor
public class NodeController {

    private NodeService nodeService;

    @GetMapping
    public List<GetNodeDTO> findAllAndParentIdIsNull() {
        return nodeService.findAllAndParentIdIsNull();
    }

    @GetMapping("/{parentId}")
    public List<ChildrenDTO> findAllByParentId(@PathVariable Long parentId) throws ParentIdNotFoundException {
        return nodeService.findAllByParentId(parentId);
    }

    @PostMapping
    public NodeIdDTO save(@RequestParam String code,
                          @RequestParam String description,
                          @RequestParam(required = false) Optional<Long> parentId,
                          @RequestParam String detail) throws ParentIdNotFoundException {
        return nodeService.save(code, description, parentId, detail);
    }

    @PutMapping("/{id}")
    public NodeIdDTO updateNode(@PathVariable(value = "id") Long id, @RequestBody NodeDTO nodeDTO) throws FatherCantBecomeSonOfHimself, IdNotFoundException {
        return nodeService.update(id, nodeDTO);
    }

    @DeleteMapping("/{id}")
    public NodeIdDTO deleteNode(@PathVariable(value = "id") Long id) throws IdNotFoundException {
        return nodeService.delete(id);
    }
}