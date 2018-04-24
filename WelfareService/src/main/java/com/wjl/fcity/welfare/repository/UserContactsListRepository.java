package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.UserContactsList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface UserContactsListRepository extends CrudRepository<UserContactsList, Long> {

}
