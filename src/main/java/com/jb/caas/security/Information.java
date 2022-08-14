package com.jb.caas.security;

/*
 * copyrights @ fadi
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Information {

    private ClientType type;
    private int id;
    private LocalDateTime time;
}
