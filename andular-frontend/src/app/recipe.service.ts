
import { DebugNode, Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { Recipe } from './recipe';
import {HttpClient, HttpHeaders} from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class RecipeService {


  private Url = "http://localhost:8090/api/recipes";

  constructor(private http: HttpClient) { }

  private getHeaders(): HttpHeaders {
    // Retrieve the token from localStorage
    const token = localStorage.getItem('token') || null;

      return new HttpHeaders().set('Authorization', `Bearer ${token}`);

    // Set the Authorization header with the token

  }
  getAllRecipes(): Observable<Recipe[]>{
    const headers = this.getHeaders();
    debugger;
    return this.http.get<Recipe[]>(`${this.Url}/allRecipes`,{headers});
  }
  getDesserts(): Observable<Recipe[]>{
    return this.http.get<Recipe[]>(`${this.Url}/dessert`);
  }

  deleteById(id: number): Observable<void> {
    return this.http.delete<void>(`${this.Url}/delete/${id}`);
  }
  getRecipeById(id: number): Observable<Recipe> {
    return this.http.get<Recipe>(`${this.Url}/getRecipe/${id}`);
  }
  createRecipe(recipe: Recipe): Observable<Recipe> {
    return this.http.post<Recipe>(`${this.Url}/addRecipe`, recipe)
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
  updateRecipeById(id: Number,recipe: Recipe): Observable<Object>{
    debugger;
    return this.http.put<Object>(`${this.Url}/updateRecipe/${id}`,recipe);
  }

}
