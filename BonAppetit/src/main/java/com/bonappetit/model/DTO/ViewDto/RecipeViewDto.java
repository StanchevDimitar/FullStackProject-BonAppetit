package com.bonappetit.model.DTO.ViewDto;

public class RecipeViewDto {

    private Long id;
    private String name;

    private String ingredients;


    private String category;


    private String addedBy;

    public RecipeViewDto() {
    }

    public Long getId() {
        return id;
    }

    public RecipeViewDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RecipeViewDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public RecipeViewDto setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public RecipeViewDto setCategory(String category) {
        this.category = category;
        return this;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public RecipeViewDto setAddedBy(String addedBy) {
        this.addedBy = addedBy;
        return this;
    }

    @Override
    public String toString() {
        return "RecipeViewDto{" +
                "name='" + name + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", category='" + category + '\'' +
                ", addedBy='" + addedBy + '\'' +
                '}';
    }
}
