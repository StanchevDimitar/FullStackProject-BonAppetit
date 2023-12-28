package com.bonappetit.repo;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.enums.CategoryEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("SELECT c.name FROM Category c")
    List<String> findAllName();
    @Query("SELECT c FROM Category c where c.name = :categoryName")
    Category findByName(@Param("categoryName") String categoryName);

    Category findCategoriesByName(CategoryEnum name);
}
