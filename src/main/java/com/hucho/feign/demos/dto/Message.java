package com.hucho.feign.demos.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNullFields;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String message;

}
