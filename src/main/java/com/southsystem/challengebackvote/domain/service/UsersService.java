package com.southsystem.challengebackvote.domain.service;

import com.southsystem.challengebackvote.domain.model.external.UsersApiResponse;

public interface UsersService {
    UsersApiResponse validCpf(String cpf);
}
