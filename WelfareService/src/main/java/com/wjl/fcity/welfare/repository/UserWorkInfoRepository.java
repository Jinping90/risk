package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.UserWorkInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface UserWorkInfoRepository extends CrudRepository<UserWorkInfo, Long> {

}
