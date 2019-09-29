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
import java.util.Optional;
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

    public NodeIdDTO save(String code, String description, Optional<Long> parentId, String detail) {
        Long responseId;
        Node parent;
        Node node = Node.builder()
                .code(code)
                .description(description)
                .parentId(parentId.orElse(null))
                .detail(detail)
                .build();

        if (node.getParentId() != null) {
            parent = nodeRepository.findById(node.getParentId()).orElseThrow(ParentIdNotFoundException::new);
            parent.getChildren().add(node);
            parent.setHasChildren(hasChildren(parent));
            node.setHasChildren(hasChildren(node));
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
        List<Node> children = nodeRepository.findAllByParentId(nodeToDelete.getId()).orElse(null);

        if (nodeToDelete.getParentId() != null) {
            Node parent = nodeRepository.findById(nodeToDelete.getParentId()).orElse(null);
            if (parent.getChildren().contains(nodeToDelete)) {
                parent.getChildren().remove(nodeToDelete);
                nodeRepository.saveAndFlush(parent);
            }
        }

        if (hasChildren(nodeToDelete)) {
            nodeToDelete.getChildren().removeAll(children);
            nodeRepository.saveAndFlush(nodeToDelete);
        }

        if (children != null)
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
        if (nodeToUpdate.getParentId() != null)
            nodeRepository.findById(nodeToUpdate.getParentId()).map(oldparent ->
                    oldparent.getChildren().remove(nodeToUpdate));
        nodeRepository.save(nodeToUpdate);
    }

    private boolean isSameParent(Node nodeToUpdate) {
        return nodeToUpdate.getChildren().stream().anyMatch(node -> node.getParentId().equals(nodeToUpdate.getId()));
    }

    private boolean hasChildren(Node node) {
        return node.getChildren() != null && !node.getChildren().isEmpty();
    }

}
