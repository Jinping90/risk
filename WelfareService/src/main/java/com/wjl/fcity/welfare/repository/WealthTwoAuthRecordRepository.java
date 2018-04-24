package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.WealthTwoAuthRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface WealthTwoAuthRecordRepository extends CrudRepository<WealthTwoAuthRecord, Long> {

}
