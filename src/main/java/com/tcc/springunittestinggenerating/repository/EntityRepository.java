package com.tcc.springunittestinggenerating.repository;

import com.tcc.springunittestinggenerating.model.EntityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<EntityModel, Integer> {
}
