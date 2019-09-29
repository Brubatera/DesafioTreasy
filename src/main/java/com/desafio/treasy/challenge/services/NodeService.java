package com.desafio.treasy.challenge.services;

import com.desafio.treasy.challenge.dtos.ChildrenDTO;
import com.desafio.treasy.challenge.dtos.GetNodeDTO;
import com.desafio.treasy.challenge.dtos.NodeDTO;
import com.desafio.treasy.challenge.dtos.NodeIdDTO;
import com.desafio.treasy.challenge.entities.Node;
import com.desafio.treasy.challenge.exceptions.FatherCantBecomeSonOfHimself;
import com.desafio.treasy.challenge.exceptions.IdNotFoundException;
import com.desafio.treasy.challenge.exceptions.ParentIdNotFoundException;
import com.desafio.treasy.challenge.repositories.NodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NodeService {

    private NodeRepository nodeRepository;

    public List<GetNodeDTO> findAllAndParentIdIsNull() {
        List<Node> listNode = nodeRepository.findAllByParentIdIsNull();
        return listNode.stream().map(Node::convertNodeToDTO).collect(Collectors.toList());

    }

    public List<ChildrenDTO> findAllByParentId(Long parentId) throws ParentIdNotFoundException {
        List<Node> listNode = nodeRepository.findAllByParentId(parentId).orElseThrow(ParentIdNotFoundException::new);
        return listNode.stream().map(Node::convertNodeChildToDTO).collect(Collectors.toList());
    }

    public NodeIdDTO save(Node nodeToSave, NodeDTO nodeDTO) {
        Node node = new Node();
        Long responseId;
        Node parent;

        node.setDetail(nodeToSave.getDetail());
        node.setDescription(nodeToSave.getDescription());
        node.setCode(nodeToSave.getCode());
        node.setHasChildren(hasChildren(node));

        if (nodeDTO.getParentId() != null) {
            parent = nodeRepository.findById(nodeDTO.getParentId()).orElseThrow(ParentIdNotFoundException::new);

            parent.getChildren().add(node);
            parent.setHasChildren(hasChildren(parent));
            node.setParentId(nodeToSave.getParentId());
            responseId = nodeRepository.save(node).getId();
            nodeRepository.save(parent);

            return new NodeIdDTO(responseId);
        }
        return new NodeIdDTO(nodeRepository.save(node).getId());
    }

    public NodeIdDTO update(Long id, NodeDTO nodeDTO) throws FatherCantBecomeSonOfHimself {
        Node nodeToUpdate = nodeRepository.findById(id).orElseThrow(IdNotFoundException::new);
        Long responseId;

        if (nodeDTO.getParentId() != null) {

            removeChildrenFromExParent(nodeToUpdate);
            nodeToUpdate.setCode(nodeDTO.getCode());
            nodeToUpdate.setDescription(nodeDTO.getDescription());
            nodeToUpdate.setDetail(nodeDTO.getDetail());
            responseId = nodeRepository.save(nodeToUpdate).getId();
            updateNewParent(nodeDTO, nodeToUpdate);

            return new NodeIdDTO(responseId);
        }

        nodeToUpdate.setCode(nodeDTO.getCode());
        nodeToUpdate.setDescription(nodeDTO.getDescription());
        nodeToUpdate.setDetail(nodeDTO.getDetail());
        responseId = nodeRepository.save(nodeToUpdate).getId();

        return new NodeIdDTO(responseId);
    }

    public NodeIdDTO delete(Long id) throws IdNotFoundException {
        Node nodeToDelete = nodeRepository.findById(id).orElseThrow(IdNotFoundException::new);
        List<Node> children = nodeRepository.findAllByParentId(id).orElseThrow(ParentIdNotFoundException::new);

        if (hasChildren(nodeToDelete)) {
            nodeToDelete.getChildren().removeAll(children);
            nodeRepository.save(nodeToDelete);
        }
        children.forEach(child -> nodeRepository.delete(child));
        nodeRepository.delete(nodeToDelete);

        return new NodeIdDTO(id);
    }

    private void updateNewParent(NodeDTO nodeDTO, Node nodeToUpdate) throws FatherCantBecomeSonOfHimself {
        if (isSameParent(nodeToUpdate)) {
            throw new FatherCantBecomeSonOfHimself("Father can't become son of himself");
        }
        nodeRepository.findById(nodeDTO.getParentId()).ifPresent(parent -> {
            parent.getChildren().add(nodeToUpdate);
            parent.setHasChildren(hasChildren(parent));
            nodeToUpdate.setParentId(nodeDTO.getParentId());
            nodeRepository.save(nodeToUpdate);
            nodeRepository.save(parent);
        });
    }

    private void removeChildrenFromExParent(Node nodeToUpdate) {
        nodeRepository.findById(nodeToUpdate.getParentId()).ifPresent(oldparent -> {
            oldparent.getChildren().remove(nodeToUpdate);
            nodeRepository.save(oldparent);
        });
    }

    private boolean isSameParent(Node nodeToUpdate) {
        return nodeToUpdate.getChildren().stream().anyMatch(node -> node.getParentId().equals(nodeToUpdate.getId()));
    }

    private boolean hasChildren(Node node) {
        return node.getChildren() != null;
    }

}
