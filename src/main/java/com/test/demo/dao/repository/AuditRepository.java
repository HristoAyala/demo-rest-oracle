package com.test.demo.dao.repository;

import com.test.demo.dao.entity.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditEntity, Integer> {
}
