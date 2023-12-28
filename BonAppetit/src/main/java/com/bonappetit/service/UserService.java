package com.bonappetit.service;

import com.bonappetit.config.JWT.UserAuthProvider;
import com.bonappetit.model.DTO.CredentialsDto;
import com.bonappetit.model.DTO.UserJWTDto;
import com.bonappetit.model.DTO.UserLoginDto;
import com.bonappetit.model.DTO.UserRegisterDto;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.repo.RecipesRepository;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.util.CurrentUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CurrentUser currentUser;
    private final RecipesRepository recipesRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAuthProvider userAuthProvider;

    public UserService(UserRepository userRepository, CurrentUser currentUser, RecipesRepository recipesRepository, PasswordEncoder passwordEncoder, UserAuthProvider userAuthProvider) {
        this.userRepository = userRepository;
        this.currentUser = currentUser;
        this.recipesRepository = recipesRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAuthProvider = userAuthProvider;
    }

    public boolean checkUsername(String username) {
        Optional<User> byUsername = userRepository.findByUsername(username);

        return byUsername.isPresent();
    }

    public UserJWTDto registerUser(UserRegisterDto userRegisterDto) {



        User userToRegister = new User();
        userToRegister.setUsername(userRegisterDto.getUsername());
        userToRegister.setEmail(userRegisterDto.getEmail());
        userToRegister.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        userRepository.save(userToRegister);

        UserJWTDto jwtDto = new UserJWTDto();
        jwtDto.setUsername(userRegisterDto.getUsername());
        jwtDto.setEmail(userRegisterDto.getEmail());
        Long id = userRepository.findByUsername(userRegisterDto.getUsername()).get().getId();
        jwtDto.setId(id);
        jwtDto.setToken(userAuthProvider.createToken(jwtDto));
        return jwtDto;
    }

    public boolean login(UserLoginDto userLoginDto) {

        Optional<User> byUsername = userRepository.findByUsername(userLoginDto.getUsername());

        if (byUsername.isEmpty()){
            return false;
        }
        User newUserInfo = byUsername.get();
        if (!newUserInfo.getPassword().equals(userLoginDto.getPassword())){
            return false;
        }

        currentUser.setUsername(newUserInfo.getUsername());
        currentUser.setLogged(true);

        return true;
    }

    public UserJWTDto login(CredentialsDto credentialsDto) {

        Optional<User> byUsername = userRepository.findByUsername(credentialsDto.username());

        if (byUsername.isEmpty()){
            throw new InvalidParameterException();
        }
        User newUserInfo = byUsername.get();
        if (!passwordEncoder.matches(credentialsDto.password(), newUserInfo.getPassword())){
            throw new InvalidParameterException();
        }

        UserJWTDto JWTUser = new UserJWTDto();
        JWTUser.setId(newUserInfo.getId());
        JWTUser.setUsername(newUserInfo.getUsername());
        JWTUser.setEmail(newUserInfo.getEmail());

        return JWTUser;
    }

    public void logout() {
        currentUser.setUsername("");
        currentUser.setLogged(false);
    }

    public User getCurrentUser(){
        return userRepository.findByUsername(currentUser.getUsername()).get();
    }

    public void addToFavourite(String name) {
        Recipe recipeByName = recipesRepository.findRecipeByName(name);
        User currentUser1 = getCurrentUser();
        currentUser1.setFavouriteRecipes(recipeByName);
        userRepository.save(currentUser1);
    }

    public void removeFromFav(String name) {
        Recipe recipeByName = recipesRepository.findRecipeByName(name);
        User currentUser1 = getCurrentUser();
        Set<Recipe> favouriteRecipes = currentUser1.getFavouriteRecipes();
        favouriteRecipes.removeIf(r -> r.getName().equals(recipeByName.getName()));
        userRepository.save(currentUser1);
    }

//    public User getCurrentlyLoggedInUser(){
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username;
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails)principal).getUsername();
//        } else {
//            username = principal.toString();
//        }
//
//        return userRepository.findByUsername(username).orElse(null);
//
//
//    }
}
