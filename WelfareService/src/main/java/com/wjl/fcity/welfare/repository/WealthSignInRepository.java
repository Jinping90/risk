package com.wjl.fcity.welfare.repository;

import com.wjl.fcity.welfare.model.SignIn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author czl
 */
@Repository
public interface WealthSignInRepository extends CrudRepository<SignIn, Long> {

}
