import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import {HTTP_INTERCEPTORS, provideHttpClient, withInterceptors} from "@angular/common/http";
import {httpTokenInterceptor} from "./Guards/http-token-interceptor.service";


export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes),
  provideHttpClient(withInterceptors([])),
    { provide: HTTP_INTERCEPTORS, useClass: httpTokenInterceptor, multi: true }
  ]
};
