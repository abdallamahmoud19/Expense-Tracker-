package com.edafa.ExpenseTracker.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@Setter
@Getter
@AllArgsConstructor
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column( nullable = false)

    private String firstName;
    @Column(nullable = false)

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;
    @Column( nullable = false)

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password ;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles" ,
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
            @OrderColumn(name = "id")

    @Column( nullable = false)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    // User ↔ Transactions
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<BaseTransaction> transactions;

    // User ↔ Categories
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    // User ↔ Budgets
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonIgnore
    private List<Budget> budgets = new ArrayList<>();


    @Builder.Default
    private boolean isEnabled = true ;
    @Builder.Default
    private boolean isCredentialsNonExpired = true ;
    @Builder.Default
    private boolean isAccountNonLocked = true;

    @Builder.Default
    private boolean isAccountNonExpired = true ;

    public User(Long id,String firstName,  String lastName, String email, String password, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.roles = roles;

        this.isEnabled = true;
        this.isCredentialsNonExpired = true;
        this.isAccountNonLocked = true;
        this.isAccountNonExpired = true;
    }
}