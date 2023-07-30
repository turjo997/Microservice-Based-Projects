package com.HabibDev.BookShopApplication.model;


import lombok.*;;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ShopModel {

    private Integer shopId;
    private List<Integer> bookIds;
    private Integer amount;

}
