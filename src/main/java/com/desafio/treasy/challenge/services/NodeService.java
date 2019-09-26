package com.desafio.treasy.challenge.services;

import com.desafio.treasy.challenge.dtos.NodeDTO;
import com.desafio.treasy.challenge.dtos.NodeIdDTO;
import com.desafio.treasy.challenge.entities.Node;
import com.desafio.treasy.challenge.repositories.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NodeService {

    private NodeRepository nodeRepository;

    public List<Node> findAllAndParentIdIsNull() {
        return nodeRepository.findAllByParentIdIsNull();
    }

    public List<Node> findAllByParentId(Long parentId) {
        return nodeRepository.findAllByParentId(parentId);
    }

    public NodeIdDTO save(Node nodeToSave, NodeDTO nodeDTO) {
        Node node = new Node();

        node.setDetail(nodeToSave.getDetail());
        node.setDescription(nodeToSave.getDescription());
        node.setCode(nodeToSave.getCode());

        if (nodeDTO.getParentId() != null) {
            Node parent = nodeRepository.findById(nodeDTO.getParentId()).orElse(null);
            Long responseId;

            if (parent != null) {
                parent.getBabies().add(node);
                parent.setHasBabies(hasBaby(parent));
                node.setParentId(nodeToSave.getParentId());
                node.setHasBabies(hasBaby(node));
                responseId = nodeRepository.save(node).getId();
                nodeRepository.save(parent);

                return new NodeIdDTO(responseId);
            }
        }
        node.setHasBabies(false);
        return new NodeIdDTO(nodeRepository.save(node).getId());
    }

    public NodeIdDTO update(Long id, NodeDTO nodeDTO) {
        Node nodeToUpdate = nodeRepository.findById(id).orElse(null);
        Long responseId;

        if (nodeToUpdate != null && nodeDTO.getParentId() != null) {

            removeBabyFromExParent(nodeToUpdate);

            nodeToUpdate.setCode(nodeDTO.getCode());
            nodeToUpdate.setDescription(nodeDTO.getDescription());
            nodeToUpdate.setDetail(nodeDTO.getDetail());
            responseId = nodeRepository.save(nodeToUpdate).getId();

            updateNewParent(nodeDTO, nodeToUpdate);
            return new NodeIdDTO(responseId);
        }
        return null;
    }

    private void updateNewParent(NodeDTO nodeDTO, Node nodeToUpdate) {
        if (!isSameParent(nodeToUpdate))
            nodeRepository.findById(nodeDTO.getParentId()).ifPresent(parent -> {
                parent.getBabies().add(nodeToUpdate);
                parent.setHasBabies(hasBaby(parent));
                nodeToUpdate.setParentId(nodeDTO.getParentId());
                nodeRepository.save(nodeToUpdate);
                nodeRepository.save(parent);
            });
    }

    private void removeBabyFromExParent(Node nodeToUpdate) {
        if (!isSameParent(nodeToUpdate))
            nodeRepository.findById(nodeToUpdate.getParentId()).ifPresent(oldparent -> {
                oldparent.getBabies().remove(nodeToUpdate);
                nodeRepository.save(oldparent);
            });
    }

    private boolean isSameParent(Node nodeToUpdate) {
        return nodeToUpdate.getBabies().stream().anyMatch(node -> node.getParentId().equals(nodeToUpdate.getId()));
    }

    private boolean hasBaby(Node parent) {
        return parent.getBabies() != null;
    }
}
