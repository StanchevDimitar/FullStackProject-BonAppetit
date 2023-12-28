import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { RecipeListComponent } from './recipe-list/recipe-list.component';
import { RecipeService } from './recipe.service';
import { HttpClientModule } from '@angular/common/http';
import {  ReactiveFormsModule } from '@angular/forms';
import { HeaderComponent } from './core/header/header.component';
import { AppModule } from './app.module';
import {AuthService} from "./auth/services/auth.service";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RecipeListComponent, HttpClientModule, RouterModule, ReactiveFormsModule,HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  providers: [RecipeService,AuthService],
})
export class AppComponent {
  title = 'angular-frontend';
}
