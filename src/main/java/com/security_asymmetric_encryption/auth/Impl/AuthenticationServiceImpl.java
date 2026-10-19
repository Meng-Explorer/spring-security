package com.security_asymmetric_encryption.auth.Impl;

import com.security_asymmetric_encryption.auth.AuthenticationService;
import com.security_asymmetric_encryption.auth.request.AuthenticationRequest;
import com.security_asymmetric_encryption.auth.request.RefreshRequest;
import com.security_asymmetric_encryption.auth.request.RegistrationRequest;
import com.security_asymmetric_encryption.auth.response.AuthenticationResponse;
import com.security_asymmetric_encryption.enums.ErrorCode;
import com.security_asymmetric_encryption.exception.BusinessException;
import com.security_asymmetric_encryption.user.UserMapper;
import com.security_asymmetric_encryption.user.UserRepository;
import com.security_asymmetric_encryption.role.Role;
import com.security_asymmetric_encryption.role.RoleRepository;
import com.security_asymmetric_encryption.security.JwtService;
import com.security_asymmetric_encryption.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager  authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(final AuthenticationRequest request) {
        final Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        final User user = (User) authentication.getPrincipal();
        final String token = this.jwtService.generateAccessToken(user.getEmail());
        final String refreshToken = this.jwtService.generateRefreshToken(user.getEmail());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .access_token(token)
                .refresh_token(refreshToken)
                .token_type(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPassword(request.getPassword(),request.getConfirmPassword());

        final Role roleUser = this.roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user dose not exist"));
        final List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        final User user = this.userMapper.toUser(request);
        user.setRoles(roles);
        log.debug("save user {}", user.getEmail());
        this.userRepository.save(user);
        final List<User> users = new ArrayList<>();
        users.add(user);
        roleUser.setUsers(users);
        this.roleRepository.save(roleUser);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        final String newAccessToken = this.jwtService.refreshAccessToken(request.getRefreshToken());
        final String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .access_token(newAccessToken)
                .refresh_token(request.getRefreshToken())
                .token_type(tokenType)
                .build();
    }

    private void checkUserEmail(String email) {
        final boolean emailExists = this.userRepository.existsByEmailIgnoreCase(email);
        if(emailExists){
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkUserPhoneNumber(String phoneNumber) {
        final boolean phoneNumberExists = this.userRepository.existsByPhoneNumber(phoneNumber);
        if(phoneNumberExists){
            throw new BusinessException(ErrorCode.PHONE_NUMBER_ALREADY_EXISTS);
        }
    }

    private void checkPassword(String password, String confirmPassword) {
        if(password == null || !password.equals(confirmPassword)){
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
    }
}
