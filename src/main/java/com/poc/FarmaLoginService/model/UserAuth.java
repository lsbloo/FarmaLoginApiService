package com.poc.FarmaLoginService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor
@Entity
@Getter
@Setter
public class UserAuth implements UserDetails  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String name;

    private String cpf;

    private Integer salt;

    private boolean isActivated;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @ManyToMany
    @JoinTable(
            name="user_auth_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Collection<Role> roles;

    public UserAuth(Long id,  String email, String password, String name, String cpf) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
    }

    public UserAuth(String email, String password, String name, String cpf) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
    }


    public static UserAuth build(UserAuth userAuth) {
        return new UserAuth(
                userAuth.getEmail(),
                userAuth.getPassword(),
                userAuth.getName(),
                userAuth.getCpf());
    }
}
