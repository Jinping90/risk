package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.MessageContent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface MessageContentRepository extends CrudRepository<MessageContent, Long> {

        }
