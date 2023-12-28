package com.bonappetit.controller;

import com.bonappetit.model.DTO.ViewDto.RecipeViewDto;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipesRepository;
import com.bonappetit.service.RecipesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RestControllerRecipes {


    private final RecipesService recipesService;

    public RestControllerRecipes(RecipesService recipesService) {
        this.recipesService = recipesService;

    }

    @GetMapping("/dessert")
    public ResponseEntity<List<RecipeViewDto>> getDesserts(){
        return ResponseEntity.ok(recipesService.getAllDesserts());
    }

    @GetMapping("/allRecipes")
    public ResponseEntity<List<RecipeViewDto>> getAllRecipes(){
        return ResponseEntity.ok(recipesService.getAll());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id){
        boolean delete = recipesService.deleteRecipeById(id);

        if (delete){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/updateRecipe/{id}")
    public ResponseEntity<RecipeViewDto> updateRecipe(@PathVariable Long id, @RequestBody RecipeViewDto request){
        //TODO add binging result or sth valid to check the body
        boolean updated = recipesService.updateRecipe(id, request);

        if (updated){
            return ResponseEntity.ok(request);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/addRecipe")
    public ResponseEntity<RecipeViewDto> createRecipe(@RequestBody RecipeViewDto recipe){
        boolean isSaved = recipesService.saveRecipe(recipe);

        if (isSaved){
            return ResponseEntity.ok(recipe);
        }

        return ResponseEntity.badRequest().build();

    }

    @GetMapping("/getRecipe/{id}")
    public ResponseEntity<RecipeViewDto> getById(@PathVariable Long id){
        RecipeViewDto recipeViewDtoById = recipesService.getRecipeViewDtoById(id);
        if (recipeViewDtoById != null){
            return ResponseEntity.ok(recipeViewDtoById);
        }
        return ResponseEntity.badRequest().build();
    }
}
