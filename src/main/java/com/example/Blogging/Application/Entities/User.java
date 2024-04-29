package com.example.Blogging.Application.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "user_name", nullable = false, length = 100)
    private String name;

    private String email;

    private String password;

    private String about;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)  // one user can upload multiple posts
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();


//    One user have many roles
    @ManyToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER)

    // In joinColumn name=user is the two joined new column which column name is user  and referencedColumnName refers as
    // which primary key of this(user) table consider as foreign key in Role table which is id(userId)
    @JoinTable(name = "user_role",                     // joinTable create new table based on relation which is user_role
            joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id")
    )          // role is column name and role id (primary key) which is works as a foreign key in this(user role) table
    private Set<Role> roles = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<SimpleGrantedAuthority> authorities = this.roles.stream().map((role) -> new SimpleGrantedAuthority(role.getRoleName())).toList();
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;       // email is consider as username
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
}
