import {CanActivateFn, Router} from "@angular/router";
import {inject} from "@angular/core";
import {AuthService} from "../auth/services/auth.service";
import {HttpClient} from "@angular/common/http";


export const authGuard: CanActivateFn=(route, state) =>  {
    debugger;
    console.log("I am in the guard!")
    const http = inject(HttpClient)
    const router = inject(Router);
    const authService = inject(AuthService);
    if (!authService.isTokenExpired()) {
        return true; // Token is still valid, allow access
    } else {
        // Token is either expired or not present
        authService.logout(); // You might want to implement a logout method in AuthService
        router.navigate(['/login']);
        return false;
    }

}