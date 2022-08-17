package com.jb.caas.controllers;

/*
 * copyrights @ fadi
 */

import com.jb.caas.dto.LoginReqDto;
import com.jb.caas.dto.LoginResDto;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.security.ClientType;
import com.jb.caas.services.WelcomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/welcome")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WelcomeController {

    private final WelcomeService welcomeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDto login(@Valid @RequestBody LoginReqDto loginReqDto) throws CouponSecurityException {

        ClientType type = loginReqDto.getType();
        String email = loginReqDto.getEmail();
        String password = loginReqDto.getPassword();

        return welcomeService.login(type, email, password);
    }

}