package com.security_asymmetric_encryption.user.Impl;

import com.security_asymmetric_encryption.enums.ErrorCode;
import com.security_asymmetric_encryption.exception.BusinessException;
import com.security_asymmetric_encryption.user.User;
import com.security_asymmetric_encryption.user.UserMapper;
import com.security_asymmetric_encryption.user.UserRepository;
import com.security_asymmetric_encryption.user.UserService;
import com.security_asymmetric_encryption.user.request.ChangePasswordRequest;
import com.security_asymmetric_encryption.user.request.ProfileUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(final String userEmail) throws UsernameNotFoundException{
        return this.userRepository.findByEmailIgnoreCase(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("username not found with this userEmail: " + userEmail));
    }

    @Override
    public void updateProfileInfo(final ProfileUpdateRequest request, final String userId){
            final User user = this.userRepository.findById(userId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND, userId));
            this.userMapper.mergerUserInfo(user,request);
            this.userRepository.save(user);
    }

    @Override
    public void changePassword(final ChangePasswordRequest request, final String userId){
        if(!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BusinessException(ErrorCode.CHANGE_PASSWORD_MISMATCH);
        }
            final User savedUser = this.userRepository.findById(userId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND,userId));

            if(!this.passwordEncoder.matches(request.getCurrentPassword(),savedUser.getPassword())){
                throw new BusinessException(ErrorCode.INVALID_CURRENT_PASSWORD);
            }
            final String encode = this.passwordEncoder.encode(request.getNewPassword());

            savedUser.setPassword(encode);
            this.userRepository.save(savedUser);

    }

    @Override
    public void deactivateAccount(final String userId){
        final User user = this.userRepository.findById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND,userId));
        if(!user.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_DEACTIVATE);
        }
        user.setEnabled(false);
        this.userRepository.save(user);

    }

    @Override
    public void reactivateAccount(final String userId){
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND,userId));
        if(user.isEnabled()){
            throw new BusinessException(ErrorCode.ACCOUNT_ALREADY_REACTIVATE);
        }
        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    public void deleteAccount(final String userId){
        // this method need the rest of the entity

        // the logic is just to schedule a profile for deletion

        // and the schedule job will pick the profile and delete everything
    }



}
