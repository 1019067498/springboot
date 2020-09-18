package com.example.demo.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @package: com.example.demo.service
 * @author: QuJiaQi
 * @date: 2020/9/18 11:27
 */
public interface SecurityToken {
    String get_token();

    void set_token(String tokenValue);

    @JsonIgnore
    default Class<? extends SecurityToken> associateEntityClass() {
        return null;
    }
}
