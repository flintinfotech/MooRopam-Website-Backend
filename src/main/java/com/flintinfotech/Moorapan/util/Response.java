package com.flintinfotech.Moorapan.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class Response {
    private Object data;
    private String code;
    private String message;

    public Response() {} // no-args constructor

    public Response(Object data, String status, String message) {
        this.data = data;
        this.code = status;
        this.message = message;
    }

}