package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.dto.LoginResDto;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.security.ClientType;

public interface WelcomeService {

    LoginResDto login(ClientType clientType, String email, String password) throws CouponSecurityException;

}
