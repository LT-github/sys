package com.lt.sys.vo;

import lombok.Data;

@Data
public class UserLoginResp {

    private Long id;

    private String username;

    private String token;

    public UserLoginResp(Long id, String username, String token) {
        this.id = id;
        this.username = username;
        this.token = token;
    }
}
