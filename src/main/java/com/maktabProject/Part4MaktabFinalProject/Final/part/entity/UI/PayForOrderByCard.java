package com.maktabProject.Part4MaktabFinalProject.Final.part.entity.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayForOrderByCard {
    private Long card;
    private Long cvv2;
    private Long orderId;
    private Long amount;
    private Long expertId;
}
