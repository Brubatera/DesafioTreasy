package com.desafio.treasy.challenge.services;

import com.desafio.treasy.challenge.entities.Node;
import com.desafio.treasy.challenge.repositories.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NodeService {

    private NodeRepository nodeRepository;

    public Node save(Node node) {
        return nodeRepository.save(node);
    }

    public List<Node> findAll() {
        return nodeRepository.findAll();
    }
}
