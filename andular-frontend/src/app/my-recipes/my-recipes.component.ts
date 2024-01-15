import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Recipe} from "../recipe";
import {RecipeService} from "../recipe.service";
import {AuthService} from "../auth/services/auth.service";
import {Router} from "@angular/router";

@Component({
    selector: 'app-my-recipes',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './my-recipes.component.html',
    styleUrl: './my-recipes.component.css'
})
export class MyRecipesComponent implements OnInit {

    recipes: Recipe[];

    constructor(private recipeService: RecipeService, protected authService: AuthService, private router: Router) {
        this.recipes = new Array;
    }

    deleteRecipe(id: number) {
        this.recipeService.deleteById(id).subscribe({
            next: () => {
                console.log('Recipe deleted successfully');
                window.location.reload();
            },
            error: (error) => {
                console.error('Error deleting recipe:', error);
                window.location.reload();
            },
        });
    }

    editRecipe(id: number) {
        this.router.navigate(["update-recipe", id]);
    }

    ngOnInit() {
        this.recipeService.getOwnRecipes().subscribe(data => {
            this.recipes = data;
        })
    }

}
