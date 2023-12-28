import { Injectable } from '@angular/core';
// import {
//   User,
//   getAuth,
//   onAuthStateChanged,
//   updateProfile,
// } from 'firebase/auth';
// import { AuthService } from './auth.service';
import { from } from 'rxjs';

export interface IUserProfile {
  displayName?: string;
  photoUrl?: string;
}

@Injectable({
  providedIn: 'root',
})
export class UserService {
  // private _loggedUser: User | null = null;

  // public get loggedUser(): User | null {
  //   return this._loggedUser;
  // }
  // public set loggedUser(value: User | null) {
  //   this._loggedUser = value;
  // }

  // constructor(private authService: AuthService) {
  //   onAuthStateChanged(authService.auth, (user) => {
  //     this.loggedUser = user;
  //   });
  // }

  // updateUser(fieldsToUpdate: IUserProfile) {
  //   if (this.loggedUser) {
  //     return from(updateProfile(this.loggedUser, fieldsToUpdate));
  //   }

  //   return null;
  // }
}
