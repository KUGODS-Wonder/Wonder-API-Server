package kugods.wonder.app.auth.service;

import kugods.wonder.app.auth.dto.*;

public interface AuthService {
    SigninResponse signin(SigninRequest request);

    SignupResponse signup(SignupRequest request);

    SigninResponse googleLogin(OauthLoginReqeust request, String googleToken);
}
