import {Injectable} from '@angular/core';
import {Observable, catchError, throwError, tap} from 'rxjs';

import {HttpClient} from "@angular/common/http";
import {User} from "../components/model/user";


class AuthToken {
    token!: string;
}

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private http: HttpClient) {
    }

    private apiUrl = 'http://localhost:8090/api/user';

    register(user: User): Observable<any> {

        debugger;
        return this.http.post<any>(`${this.apiUrl}/register`, user);
    }

    login(credentials: User) {
        debugger;
        return this.http.post<AuthToken>(`${this.apiUrl}/login`, credentials).pipe(
            tap((authToken: AuthToken) => {
                debugger;
                localStorage.setItem('token', authToken.token);
            })
        );

    }

    logout(): void {
        localStorage.removeItem('token');
        window.location.reload();
    }

    isAuthenticated(): boolean {
        // Check if localStorage is available before accessing it
        if (typeof localStorage !== 'undefined') {
            const token = localStorage.getItem('token');
            // Check other conditions or return true/false based on your logic
            return token !== null && token !== undefined;
        } else {
            return false;
        }
    }

    getUsernameFromToken(): string | null {
        const token = localStorage.getItem('token');

        if (token) {
            const tokenPayload = this.parseJwt(token);
            return tokenPayload.username || null;
        }

        return null;
    }

    getUserIdFromToken(): string | null {
        const token = localStorage.getItem('token');

        if (token) {
            const tokenPayload = this.parseJwt(token);
            return tokenPayload?.id || null;
        }

        return null;
    }

    private parseJwt(token: string): any {
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        return JSON.parse(atob(base64));
    }

    isTokenExpired(): boolean {
        const token = localStorage.getItem('token');

        if (token) {
            const tokenPayload = this.parseJwt(token);
            // Convert to milliseconds
            const expirationDate = new Date(tokenPayload.exp * 1000);

            return expirationDate < new Date();
        }

        return true;
    }


}


