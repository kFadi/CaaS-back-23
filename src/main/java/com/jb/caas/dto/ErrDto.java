package com.jb.caas.dto;

/*
 * copyrights @ fadi
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrDto {

    private final String key = "Caas";
    private String value;
}

