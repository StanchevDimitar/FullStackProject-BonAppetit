import {Routes} from '@angular/router';
import {RecipeListComponent} from './recipe-list/recipe-list.component';
import {CreateRecipeComponent} from './create-recipe/create-recipe.component';
import {UpdateRecipeComponent} from './update-recipe/update-recipe.component';
import {RegisterComponent} from "./auth/components/register/register.component";
import {LoginComponent} from "./auth/components/login/login.component";
import {RecipeFavoriteListComponent} from "./recipe-favorite-list/recipe-favorite-list.component";
import {MyRecipesComponent} from "./my-recipes/my-recipes.component";


export const routes: Routes = [
    {path: "recipes", component: RecipeListComponent},
    {path: "fav-recipes", component: RecipeFavoriteListComponent},
    {path: "my-recipes", component: MyRecipesComponent},
    {path: "create-recipe", component: CreateRecipeComponent},
    {path: "update-recipe/:id", component: UpdateRecipeComponent},
    {path: "", redirectTo: "recipes", pathMatch: "full"},
    {path: "register", component: RegisterComponent},
    {path: "login", component: LoginComponent}
];
