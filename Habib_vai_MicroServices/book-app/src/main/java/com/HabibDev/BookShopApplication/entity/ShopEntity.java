package com.HabibDev.BookShopApplication.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class ShopEntity {

    @Id
    @GeneratedValue
    private Integer shopId;
    @ElementCollection
    private List<Integer> bookIds;
    private Integer amount;


}
