package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
