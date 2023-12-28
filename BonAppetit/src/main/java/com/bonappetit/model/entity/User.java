package com.bonappetit.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Table(name = "users")
@Entity
public class User extends BaseEntity{

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;
    @Column()
    @Email
    private String email;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<Recipe> recipes;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Recipe> favouriteRecipes;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public User setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
        return this;
    }

    public Set<Recipe> getFavouriteRecipes() {
        return favouriteRecipes;
    }


    public User setFavouriteRecipes(Recipe favouriteRecipes) {
        this.favouriteRecipes.add(favouriteRecipes);
        return this;
    }
}
