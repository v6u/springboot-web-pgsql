package com.websystique.micro.service.account.repo;

import com.websystique.micro.service.account.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;


@Repository
public interface AccountRepo extends CrudRepository<Account, Long> {
}

