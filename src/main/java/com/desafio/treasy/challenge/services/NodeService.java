package com.desafio.treasy.challenge.services;

import com.desafio.treasy.challenge.dtos.EditableDTO;
import com.desafio.treasy.challenge.entities.Node;
import com.desafio.treasy.challenge.repositories.NodeRepository;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NodeService {

    private NodeRepository nodeRepository;

    public Long save(Node node) throws ChangeSetPersister.NotFoundException {
        if (node.getParentId() != null) {
            return setBabyIntoParent(node);
        }
        return nodeRepository.save(node).getId();
    }

    public List<Node> findAll() {
        return nodeRepository.findAll();
    }

    public List<Node> findAllByParentId(Long parentId) {
        return nodeRepository.findAllByParentId(parentId);
    }

    public Long update(EditableDTO editableDTO) throws Exception {
        Node node = nodeRepository.findById(editableDTO.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        if (!node.getId().equals(editableDTO.getParentId())) {
            node.setParentId(editableDTO.getParentId());
            setBabyIntoParent(editableDTO, node);
        }
        node.setCode(editableDTO.getCode());
        node.setDescription(editableDTO.getDescription());
        node.setDetail(editableDTO.getDetail());

        return nodeRepository.save(node).getId();
    }

    private void setBabyIntoParent(EditableDTO editableDTO, Node node) {
        Node parent;
        List<Node> babies = new ArrayList<>();

        if (editableDTO.getParentId() != null) {
            parent = nodeRepository.findById(editableDTO.getParentId()).orElse(null);
            babies.add(node);
            parent.setBabies(babies);
        }
    }

    private Long setBabyIntoParent(Node node) throws ChangeSetPersister.NotFoundException {
        List<Node> babies = new ArrayList<>();
        Node parent = nodeRepository.findById(node.getParentId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        babies.add(node);
        parent.setBabies((babies));
        return nodeRepository.save(node).getId();
    }
}
