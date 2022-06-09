package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Role extends BaseEntity<Long> {
    String name;

    @ManyToMany
    @JoinTable(
            name = "usersRoles",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "users_id")})
    private Set<Users> users = new HashSet<>();
}
