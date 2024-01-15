import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    EventEmitter,
    Input,
    OnInit,
    Output
} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Recipe} from "../../../recipe";
import {AuthService} from "../../../auth/services/auth.service";
import {RecipeService} from "../../../recipe.service";
import {Router} from "@angular/router";
import {lastValueFrom} from "rxjs";

@Component({
    selector: 'single-list-component',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './single-list.component.html',
    styleUrl: './single-list-component.css',

})
export class SingleListComponent implements OnInit {

    @Input() category: string = '';
    @Input() favorites: any[] = [];  // Input for favorite recipes
    @Output() addToFavorites = new EventEmitter<number>();
    @Output() removeFromFavorites = new EventEmitter<number>();
    @Output() isFavorite = new EventEmitter<number>();
    recipes: Recipe[];
    ownsRecipes: number[];
    isLogged: boolean;

    constructor(protected authService: AuthService, private recipeService: RecipeService, private router: Router, private cdr: ChangeDetectorRef) {
        this.recipes = new Array;
        this.ownsRecipes = new Array;
        this.isLogged = this.authService.isAuthenticated()

    }

    onIsFavorite(id: number): boolean {

        return this.favorites.includes(id);
    }

    onAddToFavorites(recipeId: number): void {
        this.cdr.detectChanges();
        this.addToFavorites.emit(recipeId);
        this.cdr.detectChanges();
    }

    // Example method to remove a recipe from favorites
    onRemoveFromFavorites(recipeId: number): void {
        this.cdr.detectChanges();
        this.removeFromFavorites.emit(recipeId);
        this.cdr.detectChanges();

    }

    ownRecipe(recipeId: number): boolean {
        return this.ownsRecipes.includes(recipeId)
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
        switch (this.category) {
            case "Main Dishes":
                this.recipeService.getMainDish().subscribe(data => {
                    this.recipes = data;
                });
            break;
            case "All":
                this.recipeService.getAllRecipes().subscribe(data => {
                    this.recipes = data;
                });
            break;
            case "Desserts":
                this.recipeService.getDesserts().subscribe(data => {
                    this.recipes = data;
                });
            break;
            case "Cocktails":
                this.recipeService.getCocktails().subscribe(data => {
                    this.recipes = data;
                });
            break;
        }

        if (this.isLogged) {
            this.recipeService.getOwnRecipesId().subscribe(data => {
                this.ownsRecipes = data;
            })
            this.recipeService.getAllFavoritesIds().subscribe(data => {
                this.favorites = data;
            })
        }
    }
}
