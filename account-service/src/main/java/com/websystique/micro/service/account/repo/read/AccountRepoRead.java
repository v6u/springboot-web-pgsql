package com.websystique.micro.service.account.repo.read;

import com.websystique.micro.service.account.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;


@Repository
public interface AccountRepoRead extends CrudRepository<Account, Long> {
}

