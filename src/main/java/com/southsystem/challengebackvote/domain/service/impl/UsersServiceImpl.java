package com.southsystem.challengebackvote.domain.service.impl;

import com.southsystem.challengebackvote.domain.model.external.UsersApiResponse;
import com.southsystem.challengebackvote.domain.model.external.UsersResultEnum;
import com.southsystem.challengebackvote.domain.service.UsersService;
import com.southsystem.challengebackvote.infrastructure.exception.BusinessException;
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

    private final static String UNABLE_TO_VOTE = "UNABLE_TO_VOTE";

    @Autowired
    private ValidDocumentsUtil validDocumentsUtil;

    @Override
    public void validCpf(String cpf) {
        try{
            if(!this.validDocumentsUtil.isCpfValid(cpf)){
                throw new BusinessException("CPF '" + cpf + "' is unable to vote.");
            }
        }catch (Exception e){
            throw new BusinessException("CPF '" + cpf + "' is unable to vote.");
        }
    }
}
