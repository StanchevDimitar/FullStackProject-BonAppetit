import {Injectable} from "@angular/core";
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {Router} from "@angular/router";
import {AuthService} from "../auth/services/auth.service";

@Injectable()
export class httpTokenInterceptor implements HttpInterceptor {

    constructor(private router: Router, private authService: AuthService) {
    }

    intercept(
        request: HttpRequest<any>,
        next: HttpHandler
    ): Observable<HttpEvent<any>> {
        try {
            if (typeof localStorage !== 'undefined' && localStorage.getItem('token')) {
                if (this.authService.isTokenExpired()) {
                    localStorage.removeItem('token');
                    this.router.navigate(['/login']);
                    return new Observable<HttpEvent<any>>();
                }
            }
        } catch (e) {
            console.log(e);
        }

        return next.handle(request).pipe(
            catchError((error: HttpErrorResponse) => {
                console.log(error.status)
                if (error.status === 401 || error.status === 403) {
                    // Token is invalid or expired
                    localStorage.removeItem('token');
                    this.router.navigate(['/login']);
                }

                return throwError(error);
            })
        );
    }
}