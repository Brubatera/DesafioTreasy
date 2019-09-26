package com.desafio.treasy.challenge.repositories;

import com.desafio.treasy.challenge.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

    List<Node> findAllByParentId(Long parentId);

    List<Node> findAllByParentIdIsNull();
}
