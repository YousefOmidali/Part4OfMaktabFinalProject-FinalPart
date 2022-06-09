package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseEntity;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.exceptions.WrongPriceEntered;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Suggestion extends BaseEntity<Long> {
    private Long suggestedPrice;
    private String workTime;    // it can be one hour, one day or one month ...
    private String workStartDate;
    @ManyToOne
    private Users users;
    @ManyToOne
    private Order order;

    
    public Suggestion(Users users, Order order, Long suggestedPrice, String workTime, String workStartDate) {
        this.users = users;
        this.order = order;
        if (suggestedPrice >= order.getSubService().getBasePrice())
            this.suggestedPrice = suggestedPrice;
        else
            throw new WrongPriceEntered("your suggested price cant be bigger than order's base price! ");
        this.workTime = workTime;
        this.workStartDate = workStartDate;
    }
}
