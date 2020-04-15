import {HttpEvent,HttpHandler,HttpRequest,HttpErrorResponse,HttpInterceptor} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { Injectable } from '@angular/core';
import { ErrorService } from './errors.service';
import { Router } from '@angular/router';
import {SpinnerService} from "../shared-services/spinner.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor{

	constructor (private errorService: ErrorService, private router: Router, private spinner: SpinnerService) {}

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
				if (this.router.url.indexOf('SignIn') > -1)
					this.router.navigate(['TenniShip/SignIn']);
				else if (this.router.url.includes('RegisterMatch')) {
				  let tournament = this.router.url.split("/")[4];
				  console.log(tournament);
				  alert("All matches played. Redirecting to tournament page");
				  this.router.navigate(['TenniShip', 'Tournament', tournament]);
				  this.spinner.changeLoading(false);
        }
				else {
					this.errorService.setapiError();
					this.router.navigate(['TenniShip/Error']);
				}
			return throwError(errorMessage);
			})
		)
	}
}
