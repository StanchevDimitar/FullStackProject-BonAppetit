import {HttpHeaders, HttpInterceptorFn} from '@angular/common/http';

export const headerInterceptor: HttpInterceptorFn = (req, next) => {


debugger
  const token = localStorage.getItem('token') || null; const modifiedReq = req.clone({
    headers: req.headers.set('Authorization', `Bearer ${token}`),
  });
debugger
  return next(modifiedReq);
};
