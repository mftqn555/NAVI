package com.myweb.navi.dto;

import lombok.Getter;

@Getter
public class UniqueResponse {

    private boolean unique;

    public UniqueResponse() {
    }

    public UniqueResponse(boolean unique) {
        this.unique = unique;
    }
   
}