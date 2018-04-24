package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.WealthInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface WealthInfoRepository extends CrudRepository<WealthInfo, Long> {

}
