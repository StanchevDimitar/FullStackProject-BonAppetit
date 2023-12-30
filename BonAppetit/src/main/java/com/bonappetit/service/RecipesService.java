package com.bonappetit.service;

import com.bonappetit.model.DTO.RecipeDto;
import com.bonappetit.model.DTO.ViewDto.RecipeViewDto;
import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.Recipe;
import com.bonappetit.model.entity.User;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import com.bonappetit.repo.RecipesRepository;
import com.bonappetit.repo.UserRepository;
import com.bonappetit.util.CurrentUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipesService {
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final RecipesRepository recipesRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public RecipesService(CurrentUser currentUser, UserRepository userRepository, CategoryRepository categoryRepository, RecipesRepository recipesRepository, UserService userService, ModelMapper modelMapper) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.recipesRepository = recipesRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public boolean saveRecipe(RecipeDto recipeDto) {
        User user = userService.getCurrentUser();

        Category category = categoryRepository.findCategoriesByName(recipeDto.getCategory());

        Recipe recipe = new Recipe();

        recipe.setName(recipeDto.getName());
        recipe.setAddedBy(user);
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setCategory(category);

        recipesRepository.save(recipe);
        return true;
    }

    public boolean saveRecipe(RecipeViewDto recipeDto) {
        try {
//            User user = userService.getCurrentUser();

            Category category = categoryRepository.findCategoriesByName(CategoryEnum.valueOf(recipeDto.getCategory()));
            User user = userRepository.findByUsername(recipeDto.getAddedBy()).get();
            Recipe recipe = new Recipe();

            recipe.setName(recipeDto.getName());
            recipe.setAddedBy(user);
            recipe.setIngredients(recipeDto.getIngredients());
            recipe.setCategory(category);

            recipesRepository.save(recipe);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public Set<Recipe> getFavouriteRecipes() {

        User currentUser = userService.getCurrentUser();

        if (currentUser.getFavouriteRecipes().isEmpty()) {
            return new HashSet<>();
        }
        return currentUser.getFavouriteRecipes();
    }

    public List<RecipeViewDto> getAll() {
        List<Recipe> all = recipesRepository.findAll();
        return getListOfRecipeViewDtos(all);
    }

    public List<RecipeViewDto> getAllDesserts() {
        Category dessertCategory = categoryRepository.findCategoriesByName(CategoryEnum.DESSERT);
        List<Recipe> dessertRecipes = recipesRepository.findRecipeByCategory(dessertCategory);
        return getListOfRecipeViewDtos(dessertRecipes);
    }

    private List<RecipeViewDto> getListOfRecipeViewDtos(List<Recipe> recipeByCategory) {

        return recipeByCategory.stream()
                .map(this::getRecipeViewDto)
                .collect(Collectors.toList());
    }

    private RecipeViewDto getRecipeViewDto(Recipe recipe) {
        RecipeViewDto recipeViewDto = modelMapper.map(recipe, RecipeViewDto.class);
        recipeViewDto.setAddedBy(recipe.getAddedBy().getUsername());
        recipeViewDto.setCategory(recipe.getCategory().getName().name());
        return recipeViewDto;
    }

    public boolean deleteRecipeById(Long id) {

        if (recipesRepository.findById(id).isPresent()) {
            recipesRepository.deleteById(id);
            return true;
        }
        return false;


    }

    public boolean updateRecipe(Long id, RecipeViewDto request) {
        Optional<Recipe> optionalRecipe = recipesRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            return false;
        }

        try {

            Recipe recipe = optionalRecipe.get();
            String categoryRequest = request.getCategory();
            String categoryEntity = recipe.getCategory().getName().name();
            if (!recipe.getName().equals(request.getName())) {
                recipe.setName(request.getName());
            }
            if (!recipe.getIngredients().equals(request.getIngredients())) {
                recipe.setIngredients(request.getIngredients());
            }

            if (!categoryEntity.equals(categoryRequest)) {
                Category categoriesByName = categoryRepository.findCategoriesByName(CategoryEnum.valueOf(categoryRequest));
                recipe.setCategory(categoriesByName);
            }
            recipesRepository.save(recipe);
            return true;
        } catch (Exception e) {
            return false;
        }


    }

    public RecipeViewDto getRecipeViewDtoById(Long id) {
        Optional<Recipe> byId = recipesRepository.findById(id);
        return byId.map(this::getRecipeViewDto).orElse(null);

    }
}
