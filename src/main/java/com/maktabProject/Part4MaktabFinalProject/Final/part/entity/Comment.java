package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Comment extends BaseEntity<Long> {
    private String description;
    @ManyToOne
    private Users users;
    @OneToOne
    private Order order;

}
