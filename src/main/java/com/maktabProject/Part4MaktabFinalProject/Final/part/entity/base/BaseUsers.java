package com.maktabProject.Part4MaktabFinalProject.Final.part.entity.base;

import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.authentication.CheckPassword;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.RolesInEnum;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.enums.UserStatus;
import com.maktabProject.Part4MaktabFinalProject.Final.part.entity.exceptions.InvalidPassword;
import com.maktabProject.Part4MaktabFinalProject.Final.part.services.RoleService;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@MappedSuperclass
public abstract class BaseUsers implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String username;
    @Column(nullable = false, updatable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    private String signUpTime;
    @Enumerated(EnumType.STRING)
    private RolesInEnum rolesInEnum;


    public BaseUsers(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public BaseUsers(String firstname, String lastname, String email, String username, String password) {
        CheckPassword checkPassword = new CheckPassword();
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        if (checkPassword.checkPasswordCondition(password))
            this.password = password;
        else throw new InvalidPassword("Password must be 8 or more and have UpperCase, number, @%#$ ... ");
    }


    public BaseUsers(String firstname, String lastname, String email, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
    }

    public BaseUsers(String firstname, String lastname, String email, String username, String password, UserStatus userStatus, String signUpTime) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.userStatus = userStatus;
        this.signUpTime = signUpTime;
    }
}
