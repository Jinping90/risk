package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.MessageStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface MessageStatusRepository extends CrudRepository<MessageStatus, Long> {

}
