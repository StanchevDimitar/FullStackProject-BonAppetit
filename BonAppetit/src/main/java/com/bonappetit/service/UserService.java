package com.bonappetit.service;

import com.bonappetit.config.JWT.UserAuthProvider;
import com.bonappetit.model.DTO.CredentialsDto;
import com.bonappetit.model.DTO.UserJWTDto;
import com.bonappetit.model.DTO.UserLoginDto;
import com.bonappetit.model.DTO.UserRegisterDto;
import com.bonappetit.model.DTO.ViewDto.RecipeViewDto;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.repo.RecipesRepository;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.util.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.*;

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

    public String getCurrentlyLoggedUserUsername() {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof UserJWTDto) {
                // If the principal is an instance of UserJWTDto, extract the username
                UserJWTDto userDTO = (UserJWTDto) principal;
                return userDTO.getUsername();
            } else {
                // Handle the case when the principal is not an instance of UserJWTDto
                return null; // or throw an exception, or return a default value
            }
        } else {
            // Handle the case when there is no authenticated user
            return null; // or throw an exception, or return a default value
        }
    }

    public List<String> checkCredentials(UserRegisterDto user) {
        List<String> errors = new ArrayList<>();
        Optional<User> byUsername = userRepository.findByUsername(user.getUsername());
        if (byUsername.isPresent()){
            errors.add("Username is taken");
        }
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()){
            errors.add("Email is taken");
        }
        return errors;
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
    public void addToFavourite(Long id, String username) {
        Recipe recipeByName = recipesRepository.findById(id).get();
        User user = userRepository.findByUsername(username).get();
        user.setFavouriteRecipes(recipeByName);
        userRepository.save(user);
    }

    public void removeFromFav(String name) {
        Recipe recipeByName = recipesRepository.findRecipeByName(name);
        User currentUser1 = getCurrentUser();
        Set<Recipe> favouriteRecipes = currentUser1.getFavouriteRecipes();
        favouriteRecipes.removeIf(r -> r.getName().equals(recipeByName.getName()));
        userRepository.save(currentUser1);
    }

    public boolean checkCredentials(String username) {
        return userRepository.findByUsername(username).isPresent();
    }


    @Transactional
    public void removeFromFavourite(Long id, String username) {
        Recipe recipe = recipesRepository.findById(id).get();
        User user = userRepository.findByUsername(username).get();
        Set<Recipe> favouriteRecipes = user.getFavouriteRecipes();
        favouriteRecipes.remove(recipe);
        userRepository.save(user);
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
