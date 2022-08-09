package com.jb.caas.security;

/*
 * copyrights @ fadi
 */

import com.jb.caas.exceptions.CouponSystemException;
import com.jb.caas.exceptions.ErrMsg;
import com.jb.caas.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
@Lazy
@RequiredArgsConstructor
public class LoginManager {

    private final AdminService adminService;
    private final ApplicationContext ctx;

    public ClientService login(String email, String password, ClientType clientType) throws CouponSystemException {

        switch (clientType) {
            case ADMIN:
                if (!((AdminServiceImpl) adminService).login(email, password)) {
                    throw new CouponSystemException(ErrMsg.LOGIN_FAILED);
                }
                return (ClientService) adminService;

            case COMPANY:
                CompanyService companyService = ctx.getBean(CompanyService.class);
                if (!((CompanyServiceImpl) companyService).login(email, password)) {
                    throw new CouponSystemException(ErrMsg.LOGIN_FAILED);
                }
                return (ClientService) companyService;

            case CUSTOMER:
                CustomerService customerService = ctx.getBean(CustomerService.class);
                if (!((CustomerServiceImpl) customerService).login(email, password)) {
                    throw new CouponSystemException(ErrMsg.LOGIN_FAILED);
                }
                return (ClientService) customerService;

            default:
                return null;
        }
    }


}
