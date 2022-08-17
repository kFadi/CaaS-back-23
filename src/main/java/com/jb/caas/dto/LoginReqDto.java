package com.jb.caas.dto;

/*
 * copyrights @ fadi
 */

import com.jb.caas.security.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginReqDto {

    @NotNull(message = "Type cannot be null")
    private ClientType type;

    @NotNull(message = "Email cannot be null")
    @Size(max = 45, message = "Email cannot exceed 45 char length")
    @Email(message = "Email should be in a valid pattern")
    private String email;

    @NotBlank(message = "Password cannot be null or whitespace")
    @Size(min = 4, max = 8, message = "Password should be 4-8 char length")
    private String password;
}
