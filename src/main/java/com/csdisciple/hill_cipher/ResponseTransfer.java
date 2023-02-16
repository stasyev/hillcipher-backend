package com.csdisciple.hill_cipher;

public class ResponseTransfer {
    private String response;
    public ResponseTransfer(String response){
        setResponse(response);
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
