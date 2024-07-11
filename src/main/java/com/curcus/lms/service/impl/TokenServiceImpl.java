package com.curcus.lms.service.impl;

import com.curcus.lms.model.entity.Token;
import com.curcus.lms.repository.TokenRepository;
import com.curcus.lms.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public List<Token> findAllValidTokenByUser(Long id) {
        return List.of();
    }

    @Override
    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
