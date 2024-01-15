import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {Recipe} from '../recipe';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {RecipeService} from '../recipe.service';
import {Router} from '@angular/router';
import {error} from 'console';


@Component({
    selector: 'app-create-recipe',
    standalone: true,
    imports: [CommonModule, ReactiveFormsModule],
    templateUrl: './create-recipe.component.html',
    styleUrl: './create-recipe.component.css'
})


export class CreateRecipeComponent implements OnInit {
    errors: string[] = [];

    form: FormGroup = this.fb.group({
        name: ['', [Validators.required]],
        ingredients: ['', [Validators.required]],
        category: ['', [Validators.required]],
    })

    recipe: Recipe = new Recipe();


    constructor(private fb: FormBuilder, private recipeService: RecipeService, private router: Router) {
    }

    ngOnInit(): void {
    }

    handleUpdateResponse(response: Recipe): void {
        this.redirectMethod();
        console.log('Update successful:', response);
    }

    handleError(error: any): void {
        if (error.error instanceof Array) {
            // Assuming the backend sends an array of error messages
            this.errors = error.error;
        } else {
            // Clear previous errors if needed
            this.errors = ['An unexpected error occurred. Please try again.'];
        }
    }


    saveRecipe() {
        this.recipeService.createRecipe(this.recipe).subscribe(
            {
                next: this.handleUpdateResponse.bind(this),

                error: this.handleError.bind(this)
            })

    }

    redirectMethod() {
        this.router.navigate(['/']);
    }

    onSubmit() {
        this.recipe.name = this.form.value.name;
        this.recipe.ingredients = this.form.value.ingredients;
        this.recipe.category = this.form.value.category;
        this.saveRecipe();

    }

}
