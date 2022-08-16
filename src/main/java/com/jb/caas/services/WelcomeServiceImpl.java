package com.jb.caas.services;

/*
 * copyrights @ fadi
 */

import com.jb.caas.beans.Company;
import com.jb.caas.beans.Customer;
import com.jb.caas.dto.LoginResDto;
import com.jb.caas.exceptions.CouponSecurityException;
import com.jb.caas.exceptions.SecMsg;
import com.jb.caas.repos.CompanyRepository;
import com.jb.caas.repos.CustomerRepository;
import com.jb.caas.security.ClientType;
import com.jb.caas.security.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WelcomeServiceImpl implements WelcomeService {

    private final AdminService adminService;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final TokenManager tokenManager;


    @Override
    public LoginResDto login(ClientType clientType, String email, String password) throws CouponSecurityException {

        int id = 0;
        String name = "";

        switch (clientType) {
            case ADMIN:
                if (!((AdminServiceImpl) adminService).login(email, password)) {
                    throw new CouponSecurityException(SecMsg.INVALID_CREDENTIALS);
                }
                break;

            case COMPANY:
                if (!((CompanyServiceImpl) companyService).login(email, password)) {
                    throw new CouponSecurityException(SecMsg.INVALID_CREDENTIALS);
                }
                Company cmp = companyRepository.findByEmail(email);
                id = cmp.getId();
                name = cmp.getName();
                break;

            case CUSTOMER:
                if (!((CustomerServiceImpl) customerService).login(email, password)) {
                    throw new CouponSecurityException(SecMsg.INVALID_CREDENTIALS);
                }
                Customer cst = customerRepository.findByEmail(email);
                id = cst.getId();
                name = cst.getFirstName() + " " + cst.getLastName();
                break;

            default:
                throw new CouponSecurityException(SecMsg.INVALID_CREDENTIALS);
        }

        UUID token = tokenManager.add(clientType, id);

        return LoginResDto.builder()
                .token(token)
                .type(clientType)
                .email(email)
                .id(id)
                .name(name)
                .build();

    }
}