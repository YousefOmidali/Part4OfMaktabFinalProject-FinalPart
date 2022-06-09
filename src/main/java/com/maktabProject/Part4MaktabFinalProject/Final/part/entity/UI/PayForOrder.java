package com.maktabProject.Part4MaktabFinalProject.Final.part.entity.UI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayForOrder {
    private Long customerId;
    private Long orderId;
    private Long amount;
    private Long expertId;
}
