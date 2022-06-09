package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseEntity;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long> {
    @ManyToOne
    private Users users;
    @ManyToOne
    private SubService subService;
    private String madeTime;
    private Long suggestedPrice;
    private String workDescription;
    private String workDate;
    private String address;
    private Boolean accepted;
    private Boolean payed;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}
