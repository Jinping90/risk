package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.WealthRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface WealthRecordRepository extends CrudRepository<WealthRecord, Long> {

}
