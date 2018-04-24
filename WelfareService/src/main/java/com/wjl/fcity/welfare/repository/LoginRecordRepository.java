package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.LoginRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface LoginRecordRepository extends JpaRepository<LoginRecord, Long> {
}
