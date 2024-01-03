package com.bonappetit.controller;

import com.bonappetit.model.DTO.ViewDto.RecipeViewDto;
import com.bonappetit.service.RecipesService;
import com.bonappetit.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RestControllerRecipes {


    private final RecipesService recipesService;
    private final UserService userService;

    public RestControllerRecipes(RecipesService recipesService, UserService userService) {
        this.recipesService = recipesService;
        this.userService = userService;
    }

    @GetMapping("/dessert")
    public ResponseEntity<List<RecipeViewDto>> getDesserts() {
        return ResponseEntity.ok(recipesService.getAllDesserts());
    }

    @GetMapping("/allRecipes")
    public ResponseEntity<List<RecipeViewDto>> getAllRecipes() {
        return ResponseEntity.ok(recipesService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        boolean delete = recipesService.deleteRecipeById(id);

        if (delete) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/addFavourite/{id}")
    public ResponseEntity<?> addToFavourite(@PathVariable Long id, @RequestBody String username) {
        userService.addToFavourite(id, username);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeFavourite/{id}")
    public ResponseEntity<?> removeFromFavourite(@PathVariable Long id, @RequestBody String username) {
        userService.removeFromFavourite(id, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getFavouritesIds/{id}")
    public ResponseEntity<?> getFavouritesIds(@PathVariable Long id) {
        List<Long> favourites = recipesService.getFavouritesIds(id);

        return ResponseEntity.ok(favourites);
    }
    @GetMapping("/getOwnRecipes/{id}")
    public ResponseEntity<List<Long>> getOwnRecipes(@PathVariable Long id){
        List<Long> recipesIdsByOwner = recipesService.getRecipesIdsByOwner(id);

        return ResponseEntity.ok(recipesIdsByOwner);
    }

    @GetMapping("/getFavourites/{id}")
    public ResponseEntity<?> getFavourites(@PathVariable Long id) {
        List<RecipeViewDto> favourites = recipesService.getFavourites(id);

        return ResponseEntity.ok(favourites);
    }

    @PutMapping("/updateRecipe/{id}")
    public ResponseEntity<RecipeViewDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeViewDto request) {
        //TODO add binging result or sth valid to check the body
        boolean updated = recipesService.updateRecipe(id, request);

        if (updated) {
            return ResponseEntity.ok(request);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/addRecipe")
    public ResponseEntity<RecipeViewDto> createRecipe(@RequestBody RecipeViewDto recipe) {
        boolean isSaved = recipesService.saveRecipe(recipe);

        if (isSaved) {
            return ResponseEntity.ok(recipe);
        }

        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/getRecipe/{id}")
    public ResponseEntity<RecipeViewDto> getById(@PathVariable Long id) {
        RecipeViewDto recipeViewDtoById = recipesService.getRecipeViewDtoById(id);
        if (recipeViewDtoById != null) {
            return ResponseEntity.ok(recipeViewDtoById);
        }
        return ResponseEntity.badRequest().build();
    }
}
