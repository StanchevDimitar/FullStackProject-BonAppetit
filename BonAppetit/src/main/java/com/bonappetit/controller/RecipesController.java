package com.bonappetit.controller;

import com.bonappetit.model.DTO.RecipeDto;
import com.bonappetit.model.DTO.UserRegisterDto;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.service.CategoryService;
import com.bonappetit.service.RecipesService;
import com.bonappetit.service.UserService;
import com.bonappetit.util.CurrentUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/recipes")
public class RecipesController {


    private final RecipesService recipesService;
    private final CurrentUser currentUser;
    private final UserService userService;

    @ModelAttribute("recipe")
    public RecipeDto recipeDto() {
        return new RecipeDto();
    }


    private final CategoryService categoryService;

    public RecipesController(CategoryService categoryService, RecipesService recipesService, CurrentUser currentUser, UserService userService) {
        this.categoryService = categoryService;
        this.recipesService = recipesService;
        this.currentUser = currentUser;
        this.userService = userService;
    }

    @GetMapping("/add-recipes")
    public String getRecipesAdd(Model model){
        if (!model.containsAttribute("categoryList")){
            List<String> allCategoriesName = categoryService.getAllCategoriesName();
            model.addAttribute("categoryList",allCategoriesName);
        }
        return "recipe-add";
    }

    @PostMapping("/add-recipes")
    public String RecipesAdd(@Valid RecipeDto recipeDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("recipe",recipeDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.recipe",
                    bindingResult);
            return "recipe-add";
        }

        recipesService.saveRecipe(recipeDto);
        return "redirect:/";
    }

    @GetMapping("/favourites")
    public String getFavourite(Model model){
        Set<Recipe> favouriteRecipes = recipesService.getFavouriteRecipes();
        model.addAttribute("favouriteRecipes",favouriteRecipes);
        model.addAttribute("currentUser",currentUser);
        return "favourites";
    }

    @GetMapping("/addToFav/{name}")
    public String addToFavorites(@PathVariable String name, Model model) {

        userService.addToFavourite(name);

        return "redirect:/"; // or any other redirect or view
    }
    @GetMapping("/removeFromFav/{name}")
    public String removeFromFav(@PathVariable String name, Model model) {

        userService.removeFromFav(name);

        return "redirect:/recipes/favourites"; // or any other redirect or view
    }

}
