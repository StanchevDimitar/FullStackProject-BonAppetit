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
  constructor(private http: HttpClient) {}
  private apiUrl = 'http://localhost:8090/api/user';
  register(user: User): Observable<any> {

  debugger;
    return this.http.post<User>(`${this.apiUrl}/register`, user);
  }
testMethod(authToken: AuthToken){
    debugger;
  localStorage.setItem('token', authToken.token);
}
  login(credentials: User) {
    debugger;
      return this.http.post<AuthToken>(`${this.apiUrl}/login`, credentials).pipe(
        tap((authToken: AuthToken) => {
          debugger;
          this.testMethod(authToken)
        })
      );

  }

  isAuthenticated(): boolean {
    // Check if localStorage is available before accessing it
    if (typeof localStorage !== 'undefined') {
      const token = localStorage.getItem('token');
      // Check other conditions or return true/false based on your logic
      return token !== null && token !== undefined;
    } else {
      // localStorage is not available (e.g., during server-side rendering)
      // Handle the case accordingly, you might want to return false or throw an error
      return false;
    }
  }
  logout(): void {
    localStorage.removeItem('token');
    window.location.reload();
  }
}


