import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Recipe } from '../recipe';
import { ActivatedRoute } from '@angular/router';
import { RecipeService } from '../recipe.service';
import { Router } from '@angular/router';
import { error } from 'console';

@Component({
  selector: 'app-update-recipe',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './update-recipe.component.html',
  styleUrl: './update-recipe.component.css'
})
export class UpdateRecipeComponent implements OnInit{
  recipe: Recipe = new Recipe();
  id: number;
 
  updateForm: FormGroup = this.fb.group({
    name: ['', [Validators.required]],
    ingredients: ['', [Validators.required]],
    category: ['', [Validators.required]],
  })
  
  constructor(private fb: FormBuilder, private route: ActivatedRoute, private recipeService: RecipeService, private router: Router){
    this.id = 0;
  }

  async ngOnInit() {
    
    this.id = this.route.snapshot.params["id"];
    
     this.recipeService.getRecipeById(this.id).subscribe(data => {
      this.recipe = data;
    })
    debugger;
    console.log(this.recipe);
    
  }
  redirectMethod() {
    this.router.navigate(['/']);
  }
  onSubmit(){
    debugger;
    this.recipe.name = this.updateForm.value.name;
    this.recipe.ingredients = this.updateForm.value.ingredients;
    this.recipe.category = this.updateForm.value.category;
    this.recipeService.updateRecipeById(this.id, this.recipe).subscribe(data => {
      this.redirectMethod();
    }),(error: any) => console.log(error);
    
    
  }
}
