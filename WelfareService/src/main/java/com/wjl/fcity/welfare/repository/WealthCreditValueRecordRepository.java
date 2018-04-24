package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.WealthCreditValueRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface WealthCreditValueRecordRepository extends CrudRepository<WealthCreditValueRecord, Long> {

}
