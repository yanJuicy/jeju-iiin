package com.example.jejuiiin.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
public class HeaderResponse {
    private List<String> category = Arrays.asList("MAGAZINE", "SHOP");

    @JsonProperty(value = "MAGAZINE")
    private List<String> magazine = Arrays.asList("iiin","FINDERS");

    @JsonProperty(value = "SHOP")
    private List<String> shop = Arrays.asList("ART","BOOK","FOOD","GOODS","한림수칙","RESERVATION");

    private boolean login = false;
    private int cartItemCount = 0;

    public HeaderResponse(boolean login, int cartItemCount) {
        this.login = login;
        this.cartItemCount = cartItemCount;
    }
}
