package com.desafio.treasy.challenge.controllers;

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

    @PostMapping
    public Node save(@RequestBody Node node) {
        return nodeService.save(node);
    }

    @GetMapping
    public List<Node> findAll() {
        return nodeService.findAll();
    }
}
