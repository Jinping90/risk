package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.UserIdCard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface UserIdCardRepository extends CrudRepository<UserIdCard, Long> {

}
