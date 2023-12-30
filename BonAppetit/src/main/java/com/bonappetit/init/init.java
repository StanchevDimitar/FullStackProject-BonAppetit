package com.bonappetit.init;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.enums.CategoryEnum;
import com.bonappetit.repo.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class init implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    public init(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (categoryRepository.findAll().isEmpty()) {
            CategoryEnum[] values = CategoryEnum.values();

            for (CategoryEnum value : values) {
                Category category = new Category();
                category.setName(value);
                if (value.equals(CategoryEnum.MAIN_DISH)) {
                    category.setDescription("Heart of the meal, substantial and satisfying; main dish delights taste buds.");
                }else if (value.equals(CategoryEnum.COCKTAIL)){
                    category.setDescription("Sip of sophistication, cocktails blend flavors, creating a spirited symphony in every glass.");
                }else if (value.equals(CategoryEnum.DESSERT)){
                    category.setDescription("Sweet finale, indulgent and delightful; dessert crowns the dining experience with joy.");
                }
                categoryRepository.save(category);
            }


        }
    }
}
