import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Recipe} from '../recipe';
import {RecipeService} from '../recipe.service';
import {Router} from '@angular/router';
import {AuthService} from "../auth/services/auth.service";
import {lastValueFrom} from "rxjs";
import {SingleListComponent} from "./recipe-components-by-type/maindish-component/single-list.component";


@Component({
    selector: 'app-recipe-list',
    standalone: true,
    imports: [CommonModule, SingleListComponent],
    templateUrl: './recipe-list.component.html',
    styleUrl: './recipe-list.component.css'
})
export class RecipeListComponent implements OnInit {

    favorites: number[]
    isLogged: boolean;


    constructor(private recipeService: RecipeService, protected authService: AuthService) {
        this.favorites = new Array;
        this.isLogged = this.authService.isAuthenticated()
    }


    async addToFavourites(id: number) {
        try {
            if (!this.isFavorite(id)) {
                await lastValueFrom(this.recipeService.addFavourite(id));
                this.favorites.push(id);
            }
        } catch (error) {

            console.error('Error adding to favorites:', error);
        }
    }

    isFavorite(id: number): boolean {
        debugger;
        return this.favorites.includes(id);
    }

    async removeFromFavourites(id: number) {
        try {
            if (this.isFavorite(id)) {
                await lastValueFrom(this.recipeService.removeFavourite(id));
                const index = this.favorites.indexOf(id);
                this.favorites.splice(index, 1);
            }
        } catch (error) {

            console.error('Error removing to favorites:', error);
        }
    }

    ngOnInit(): void {
        if (this.isLogged) {
            this.recipeService.getAllFavoritesIds().subscribe(data => {
                this.favorites = data;
            })
        }
    }
}
