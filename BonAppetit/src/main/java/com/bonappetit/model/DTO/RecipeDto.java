package com.bonappetit.model.DTO;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.enums.CategoryEnum;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RecipeDto {
    @Size(min = 2, max = 40, message ="Name must be between 2 and 40 symbols")
    @NotNull
    private String name;
    @Size(min = 2, max = 150, message ="Ingredients must be between 2 and 150 symbols")
    private String ingredients;
    @NotNull(message = "You must select a category")
    private CategoryEnum category;

    public RecipeDto() {
    }

    public String getName() {
        return name;
    }

    public RecipeDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public RecipeDto setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public RecipeDto setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }
}
