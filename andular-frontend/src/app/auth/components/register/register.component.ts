import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {User} from "../model/user";
import {AuthService} from "../../services/auth.service";
import { Router } from '@angular/router';
import {CommonModule} from "@angular/common";
import {passwordMatchValidator} from "../../../validators/password.validator";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule
  ],
  providers: [],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit{
  protected errorMessage: string | null = null;
  constructor(private fb: FormBuilder,private authService: AuthService,private router: Router) {
  }

  errors: string[] =[];

  registerForm: FormGroup = this.fb.group({
    username: ["", [Validators.required,Validators.minLength(3),Validators.maxLength(20)]],
    email: ["", [Validators.required,Validators.email]],
    password: ["",[Validators.required, Validators.minLength(3),Validators.maxLength(20)]],
    confirmPassword: ["",[Validators.required, Validators.minLength(3),Validators.maxLength(20)]],
  },
    {
      validators: passwordMatchValidator,
    })
  user: User = new User();

  onSubmit(){
    this.user.username = this.registerForm.value.username;
    this.user.email = this.registerForm.value.email;
    this.user.password = this.registerForm.value.password;
    this.user.confirmPassword = this.registerForm.value.confirmPassword;
    this.registerUser();
  }


  private handleError(error: any): void {
    if (error.error instanceof Array) {
      // Assuming the backend sends an array of error messages
      this.errors = error.error;
    } else {
      // Clear previous errors if needed
      this.errors = ['An unexpected error occurred. Please try again.'];
    }
    console.log(this.errors)
  }
  registerUser() {
    this.authService.register(this.user).subscribe(
        {
          next: () => {
            this.router.navigate(["/login"]);
          } ,

          error:  (error) => {
            debugger;
            if (error.status === 226) { // HttpStatus.IM_USED
              // Handle username already in use
              this.errorMessage = error.error.text;
            } else {
              console.log(error);
            }
          }
        });
  }
  ngOnInit(): void {
  }
}
