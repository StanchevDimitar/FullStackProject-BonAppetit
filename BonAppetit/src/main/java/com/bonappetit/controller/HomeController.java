package com.bonappetit.controller;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipesRepository;
import com.bonappetit.util.CurrentUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Controller
public class HomeController {

    private final CurrentUser currentUser;
    private final RecipesRepository recipesRepository;
    private final CategoryRepository categoryRepository;

    public HomeController(CurrentUser currentUser, RecipesRepository recipesRepository, CategoryRepository categoryRepository) {
        this.currentUser = currentUser;
        this.recipesRepository = recipesRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/")
    public String getIndex(){
        if (currentUser.isLogged()) {

            return "redirect:/home";
        }
        return "index";
    }
    @GetMapping("/home")
    public String getHome(Model model){
        Category dessertCategory = categoryRepository.findCategoriesByName(CategoryEnum.DESSERT);
        List<Recipe> dessertRecipes = recipesRepository.findRecipeByCategory(dessertCategory);

        Category mainDishCategory = categoryRepository.findCategoriesByName(CategoryEnum.MAIN_DISH);
        List<Recipe> mainDishRecipes = recipesRepository.findRecipeByCategory(mainDishCategory);

        Category cocktailCategory = categoryRepository.findCategoriesByName(CategoryEnum.COCKTAIL);
        List<Recipe> cocktailRecipes = recipesRepository.findRecipeByCategory(cocktailCategory);
        model.addAttribute("desserts",dessertRecipes);
        model.addAttribute("cocktails",cocktailRecipes);
        model.addAttribute("mainDishes",mainDishRecipes);
        return "home";
    }

}
