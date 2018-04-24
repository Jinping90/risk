package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.LogAppService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface LogAppServiceRepository extends CrudRepository<LogAppService, Long> {

}