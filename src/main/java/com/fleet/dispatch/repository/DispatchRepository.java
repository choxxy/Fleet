package com.fleet.dispatch.repository;

import com.fleet.dispatch.model.Dispatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DispatchRepository extends JpaRepository<Dispatch, Integer>, JpaSpecificationExecutor<Dispatch> {
}