package com.jb.caas.advice;

/*
 * copyrights @ fadi
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrDetails {

    private final String key = "Caas";
    private String value;
}

