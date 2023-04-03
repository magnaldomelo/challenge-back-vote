package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.external.UsersApiResponse;
import com.southsystem.challengebackvote.domain.model.external.UsersResultEnum;
import com.southsystem.challengebackvote.domain.service.UsersService;
import com.southsystem.challengebackvote.infrastructure.util.ValidDocumentsUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsersServiceImpl implements UsersService {

    //TODO Api disponibilizada pela South está offline
    /*@Autowired
    private UsersAPI usersAPI;*/

    @Autowired
    private ValidDocumentsUtil validDocumentsUtil;

    @Override
    public UsersApiResponse validCpf(String cpf) {
        if (validDocumentsUtil.isCpfValid(cpf)){
            return UsersApiResponse.builder().value(UsersResultEnum.ABLE_TO_VOTE.toString()).build();
        }

        return UsersApiResponse.builder().value(UsersResultEnum.UNABLE_TO_VOTE.toString()).build();
    }
}
