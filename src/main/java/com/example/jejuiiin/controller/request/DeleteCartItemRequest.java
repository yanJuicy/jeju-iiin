package com.example.jejuiiin.controller.request;


import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
public class DeleteCartItemRequest {
    private List<Long> cartItemIdList;
}
