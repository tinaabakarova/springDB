package ru.otus.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "LOGIN", nullable = false, unique = true)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE", nullable = false)
    private String role;

    @Column(name = "IS_ACCOUNT_NON_EXPIRED", nullable = false)
    private Boolean isAccountNonExpired;

    @Column(name = "IS_ACCOUNT_NON_LOCKED", nullable = false)
    private Boolean isAccountNonLocked;

    @Column(name = "IS_CREDENTIALS_NON_EXPIRED", nullable = false)
    private Boolean isCredentialsNonExpired;

    @Column(name = "IS_ENABLED", nullable = false)
    private Boolean isEnabled;
}
