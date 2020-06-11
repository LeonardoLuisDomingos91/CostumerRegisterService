package com.luis.leonardo.costumerregisterservice.dto;

public class TokenDto {

    private String token;
    private String httpAuthenticationType;


    public TokenDto(String token, String httpAuthenticationType) {
        this.token = token;
        this.httpAuthenticationType = httpAuthenticationType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHttpAuthenticationType() {
        return httpAuthenticationType;
    }

    public void setHttpAuthenticationType(String httpAuthenticationType) {
        this.httpAuthenticationType = httpAuthenticationType;
    }
}
