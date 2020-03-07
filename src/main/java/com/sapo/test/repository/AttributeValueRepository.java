package com.sapo.test.repository;

import com.sapo.test.entity.AttributeValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
}
