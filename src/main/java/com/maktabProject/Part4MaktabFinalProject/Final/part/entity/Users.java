package com.maktabProject.Part4MaktabFinalProject.Final.part.entity;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base.BaseUsers;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.RolesInEnum;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.UserStatus;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.exceptions.FileIsTooBig;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.sql.Blob;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Users extends BaseUsers {
    //Only Customer
    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Comment> comments;
    //Both customer and expert
    @OneToOne
    private Wallet wallet;
    private String verificationCode;
    private Boolean verified;
    //Only experts
    private Long likes;     //This is expert's rating system
    @Lob
    @Column(name = "IMAGE")
    private Blob image;
    private String imageLink;
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<SubService> subService = new HashSet<>();
    //For spring security
    private Boolean expired;
    private Boolean locked;
    private Boolean credentialsExpired;
    private Boolean enabled;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @SneakyThrows
    public Users(Long id, String firstname, String lastname, String email, String username, String password, UserStatus userStatus, String signUpTime, RolesInEnum rolesInEnum, List<Comment> comments, Wallet wallet, Long likes, Blob image, String imageLink, Set<SubService> subService, Boolean expired, Boolean locked, Boolean credentialsExpired, Boolean enabled, Set<Role> roles) {
        super(id, firstname, lastname, email, username, password, userStatus, signUpTime, rolesInEnum);
        this.comments = comments;
        this.wallet = wallet;
        this.likes = likes;
        if ((image.length() / 1024) <= 300)
            this.image = image;
        else
            throw new FileIsTooBig("file is too big! (upto 300kb & jpg)");
        this.imageLink = imageLink;
        this.subService = subService;
        this.expired = expired;
        this.locked = locked;
        this.credentialsExpired = credentialsExpired;
        this.enabled = enabled;
        this.roles = roles;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + getId() +
                ",firstname=" + getFirstname() +
                ",lastname=" + getLastname() +
                ",email=" + getEmail() +
                ",username=" + getUsername() +
                ",password=" + getPassword() +
                ",userStatus=" + getUserStatus() +
                ",signUpTime=" + getSignUpTime() +
                ",comments=" + comments +
                ", wallet=" + wallet +
                ", likes=" + likes +
                ", verified=" + verified +
                ", role=" + getRolesInEnum() +
                '}';
    }
}
