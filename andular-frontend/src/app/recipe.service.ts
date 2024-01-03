import {Injectable} from '@angular/core';
import {catchError, Observable, of, tap} from 'rxjs';
import {Recipe} from './recipe';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthService} from "./auth/services/auth.service";


@Injectable({
    providedIn: 'root'
})
export class RecipeService {


    private Url = "http://localhost:8090/api/recipes";


    constructor(private http: HttpClient, private authService: AuthService) {
    }

    private getHeaders(): HttpHeaders {
        // Retrieve the token from localStorage
        const token = localStorage.getItem('token') || null;

        return new HttpHeaders().set('Authorization', `Bearer ${token}`);

        // Set the Authorization header with the token

    }

    getAllRecipes(): Observable<Recipe[]> {
        debugger
        return this.http.get<Recipe[]>(`${this.Url}/allRecipes`,);
    }

    getDesserts(): Observable<Recipe[]> {

        return this.http.get<Recipe[]>(`${this.Url}/dessert`);
    }

    deleteById(id: number): Observable<void> {
        const headers = this.getHeaders();
        return this.http.delete<void>(`${this.Url}/delete/${id}`, {headers});
    }

    getRecipeById(id: number): Observable<Recipe> {
        const headers = this.getHeaders();
        return this.http.get<Recipe>(`${this.Url}/getRecipe/${id}`, {headers});
    }

    createRecipe(recipe: Recipe): Observable<Recipe> {
        const headers = this.getHeaders();
        recipe.addedBy = this.authService.getUsernameFromToken();
        return this.http.post<Recipe>(`${this.Url}/addRecipe`, recipe, {headers})
            .pipe(
                tap((result) => {
                    console.log('Result:', result);
                }),
                catchError((error) => {
                    console.error('Error:', error);
                    throw error;
                })
            );
    }

    updateRecipeById(id: Number, recipe: Recipe): Observable<Object> {
        const headers = this.getHeaders();
        debugger;
        return this.http.put<Object>(`${this.Url}/updateRecipe/${id}`, recipe, {headers});
    }


    addFavourite(id: number) {
        debugger;
        const headers = this.getHeaders();
        const currentUserUsername = this.authService.getUsernameFromToken();
        return this.http.post(`${this.Url}/addFavourite/${id}`, currentUserUsername, {headers})
    }

    getAllFavoritesIds(): Observable<number[]> {
        const headers = this.getHeaders();
        const userID = this.authService.getUserIdFromToken();
        const url = `${this.Url}/getFavouritesIds/${userID}`;

        return this.http.get<number[]>(url, {headers});
    }
    getAllFavorites(): Observable<Recipe[]> {
        const headers = this.getHeaders();
        const userID = this.authService.getUserIdFromToken();
        const url = `${this.Url}/getFavourites/${userID}`;

        return this.http.get<Recipe[]>(url, {headers});
    }

    removeFavourite(id: number) {
        const headers = this.getHeaders();
        const currentUserUsername = this.authService.getUsernameFromToken();
        return this.http.post(`${this.Url}/removeFavourite/${id}`, currentUserUsername, {headers})
    }

    getOwnRecipes() {
        debugger;
        const headers = this.getHeaders();
        const userID = this.authService.getUserIdFromToken();
        return this.http.get<number[]>(`${this.Url}/getOwnRecipes/${userID}`,{headers})
    }
}
