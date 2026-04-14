package com.expansion.server.domain.commission.repository;

import com.expansion.server.domain.commission.entity.CommissionFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommissionFileRepository extends JpaRepository<CommissionFile, Long> {

    List<CommissionFile> findByCommission_CommissionId(Long commissionId);
}
