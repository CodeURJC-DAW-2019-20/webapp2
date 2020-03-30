import {HttpEvent,HttpHandler,HttpRequest,HttpErrorResponse,HttpInterceptor} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { ErrorService } from './errors.service';
import { Router } from '@angular/router';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor{

	constructor (private errorService: ErrorService, private router: Router) {}

	intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
		return next.handle(request).pipe(
			retry(1),
			catchError((error: HttpErrorResponse) => {
			let errorMessage = '';
			if (error.error instanceof ErrorEvent) {
				// client-side error
				this.errorService.errorNum = error.status;
				errorMessage = `Error: ${error.error.message}`;
			} else {
				// server-side error
				this.errorService.errorNum = error.status;
				errorMessage = `Error Code: ${error.status}\nhtxMessage: ${error.message}`;
			}
			console.log(this.errorService.errorNum);
			console.log(error.status);
			console.log(errorMessage);
			this.router.navigate(['TenniShip/Error']);
			return throwError(errorMessage);
			})
		)
	}
}