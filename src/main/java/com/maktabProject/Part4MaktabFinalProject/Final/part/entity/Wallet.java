package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Wallet extends BaseEntity<Long> {
    private Long amount;


    public Wallet(Long id, Long amount) {
        super(id);
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "id=" + getId() +
                ",amount=" + amount +
                '}';
    }
}
