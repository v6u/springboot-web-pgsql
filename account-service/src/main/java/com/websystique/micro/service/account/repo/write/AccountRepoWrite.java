package com.websystique.micro.service.account.repo.write;

import com.websystique.micro.service.account.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AccountRepoWrite extends CrudRepository<Account, Long> {
}

