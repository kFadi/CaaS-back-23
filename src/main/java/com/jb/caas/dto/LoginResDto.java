package com.jb.caas.dto;

/*
 * copyrights @ fadi
 */

import com.jb.caas.security.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResDto {

    private UUID token;
    private ClientType type;
    private String email;
    private int id;
    private String name;
}
