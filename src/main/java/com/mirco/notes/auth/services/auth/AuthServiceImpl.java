package com.mirco.notes.auth.services.auth;

import com.mirco.notes.auth.model.entities.SystemUser;
import com.mirco.notes.auth.model.repository.ISystemUserRepository;
import com.mirco.notes.auth.model.request.LoginRequest;
import com.mirco.notes.auth.model.request.RegisterRequest;
import com.mirco.notes.auth.model.response.TokenResponse;
import com.mirco.notes.auth.services.jwt.IJwtService;
import com.mirco.notes.shared.model.exceptions.UserNotRegisteredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    private final ISystemUserRepository iSystemUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService iJwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(ISystemUserRepository iSystemUserRepository,
                           PasswordEncoder passwordEncoder,
                           IJwtService iJwtService,
                           AuthenticationManager authenticationManager) {
        this.iSystemUserRepository = iSystemUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.iJwtService = iJwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenResponse register(RegisterRequest registerRequest) {
        SystemUser system = SystemUser.builder()
                .email(registerRequest.email())
                .fullName(registerRequest.fullName())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        SystemUser savedUser = iSystemUserRepository.save(system);

        return new TokenResponse(iJwtService.generateToken(savedUser));
    }

    @Override
    public TokenResponse auth(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SystemUser systemUser = getUserByEmail(loginRequest.email());

        return new TokenResponse(iJwtService.generateToken(systemUser));
    }

    private SystemUser getUserByEmail(String email) {
        Optional<SystemUser> userFromDB = iSystemUserRepository.findSystemUserByEmail(email);
        if (userFromDB.isEmpty()) {
            // todo que se muestre el error
            throw new UserNotRegisteredException("User not found");
        }

        return userFromDB.get();
    }

}
