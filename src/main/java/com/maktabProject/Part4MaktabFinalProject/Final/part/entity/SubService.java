package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class SubService extends BaseEntity<Long> {
    @Column(unique = true)
    private String description;
    private Long basePrice;
    @ManyToOne
    private Service service;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "SubServices_Experts",
            joinColumns = {@JoinColumn(name = "subservice_id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id")})
    private Set<Users> users = new HashSet<>();

    @Override
    public String toString() {
        return "SubService{" +
                "id=" + getId() +
                ", description='" + description + '\'' +
                ", basePrice=" + basePrice +
                ", service=" + service +
                '}';
    }
}
