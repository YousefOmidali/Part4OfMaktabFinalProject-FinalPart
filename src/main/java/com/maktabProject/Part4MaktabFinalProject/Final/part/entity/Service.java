package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Service extends BaseEntity<Long> {
    private String description;

}
