import {HttpEvent, HttpHandlerFn, HttpInterceptorFn, HttpRequest} from '@angular/common/http';
import {inject} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../auth/services/auth.service";
import {Observable} from "rxjs";

export const tokenInterceptor: HttpInterceptorFn = (req: HttpRequest<unknown>, next:
    HttpHandlerFn) => {
  debugger;
  console.log("Hi from interceptor")
  const router = inject(Router);
  const authService = inject(AuthService);
  try {
    debugger;
    if (localStorage.getItem("token")){
      if (authService.isTokenExpired()) {
        authService.logout(); // You might want to implement a logout method in AuthService
        router.navigate(['/login']);
        return new Observable<HttpEvent<any>>();
      }
    }
  }catch (e){
    console.log(e)
  }

  return next(req);
};
