package com.vasisth.astrovasisth_core_svc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalResponse<T>{
    private int statusCode;
    private String message;
    private T data;
}
