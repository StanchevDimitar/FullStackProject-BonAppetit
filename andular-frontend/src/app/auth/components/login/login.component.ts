import {Component, WritableSignal, signal} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {passwordMatchValidator} from "../../../validators/password.validator";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import {User} from "../model/user";
import {NgIf} from "@angular/common";

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [
        ReactiveFormsModule,
        NgIf

    ],
    providers: [],
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
})
export class LoginComponent {

    constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    }

    loginForm: FormGroup = this.fb.group({
        username: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
        password: ["", [Validators.required, Validators.minLength(3), Validators.maxLength(20)]],
    })

    user: User = new User();
    isValid: boolean = true;

    onSubmit() {
        this.user.username = this.loginForm.value.username;
        this.user.password = this.loginForm.value.password;
        this.loginUser();
    }

    redirect(route: string) {
        return this.router.navigate([route]);
    }

    private loginUser() {
        if (this.loginForm.valid) {
            const credentials = this.loginForm.value;
            this.authService.login(credentials).subscribe({
                next: (authToken) => {
                    this.isValid = true;
                    this.router.navigate(["/"]);

                },
                error: (error) => {
                    // Handle login error here
                    this.isValid = false;
                }
            });


        }
    }
}
