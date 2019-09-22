package com.desafio.treasy.challenge.repositories;

import com.desafio.treasy.challenge.entities.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}
