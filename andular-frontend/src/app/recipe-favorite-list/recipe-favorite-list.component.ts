import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AuthService} from "../auth/services/auth.service";
import {Recipe} from "../recipe";
import {RecipeService} from "../recipe.service";
import {lastValueFrom} from "rxjs";

@Component({
    selector: 'app-recipe-favorite-list',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './recipe-favorite-list.component.html',
    styleUrl: './recipe-favorite-list.component.css'
})
export class RecipeFavoriteListComponent implements OnInit {
    favRecipes: Recipe[];
    favorites: number[];

    constructor(protected authService: AuthService, private recipeService: RecipeService) {
        this.favRecipes = [];
        this.favorites = [];
    }

    isFavorite(id: number): boolean {
        return this.favorites.includes(id);
    }

    async removeFromFavourites(id: number) {
        try {
            if (this.isFavorite(id)) {
                await lastValueFrom(this.recipeService.removeFavourite(id));
                const index = this.favorites.indexOf(id);
                this.favorites.splice(index, 1);
                this.favRecipes = this.favRecipes.filter(recipe => recipe.id !== id);
            }
        } catch (error) {

            console.error('Error removing to favorites:', error);
        }
    }

    ngOnInit() {
        this.recipeService.getAllFavorites().subscribe(data => {
            this.favRecipes = data;
        })
        this.recipeService.getAllFavoritesIds().subscribe(data => {
            this.favorites = data;
        })
    }
}
