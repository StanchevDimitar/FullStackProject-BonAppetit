import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Recipe } from '../recipe';
import { RecipeService } from '../recipe.service';
import { routes } from '../app.routes';
import { Router } from '@angular/router';
import {AuthService} from "../auth/services/auth.service";
import {lastValueFrom} from "rxjs";




@Component({
  selector: 'app-recipe-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './recipe-list.component.html',
  styleUrl: './recipe-list.component.css'
})
export class RecipeListComponent implements OnInit {

  allRecipes: Recipe[];
  desserts: Recipe[];
  favorites: number[]
  constructor(private recipeService: RecipeService, private router: Router,protected authService: AuthService) {
    this.allRecipes = new Array;
    this.desserts = new Array;
    this.favorites = new Array;
  }

  async addToFavourites(id: number) {
    try {
      debugger;
      if (!this.isFavorite(id)) {
        await lastValueFrom(this.recipeService.addFavourite(id));
        this.favorites.push(id);
      }
    } catch (error) {

      console.error('Error adding to favorites:', error);
    }
  }
  isFavorite(id: number): boolean {
    return this.favorites.includes(id);
  }
  async removeFromFavourites(id: number) {
    try {
      debugger;
      if (this.isFavorite(id)) {
        await lastValueFrom(this.recipeService.removeFavourite(id));
        const index = this.favorites.indexOf(id);
        this.favorites.splice(index, 1);
      }
    } catch (error) {

      console.error('Error removing to favorites:', error);
    }
  }


  deleteRecipe(id: number) {
    debugger;
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

  editRecipe(id: number){
    this.router.navigate(["update-recipe",id]);
  }
  

  ngOnInit(): void {
    this.recipeService.getAllRecipes().subscribe(data => {
      debugger;
      this.allRecipes = data;
    });

    this.recipeService.getDesserts().subscribe(data => {
      this.desserts = data;
    });
    this.recipeService.getAllFavorites().subscribe(data => {
      this.favorites = data;
    })

  }


}
