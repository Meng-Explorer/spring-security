package com.security_asymmetric_encryption.auth;

import com.security_asymmetric_encryption.auth.request.AuthenticationRequest;
import com.security_asymmetric_encryption.auth.request.RefreshRequest;
import com.security_asymmetric_encryption.auth.request.RegistrationRequest;
import com.security_asymmetric_encryption.auth.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);
    void register(RegistrationRequest request);
    AuthenticationResponse refreshToken(RefreshRequest request);
}
