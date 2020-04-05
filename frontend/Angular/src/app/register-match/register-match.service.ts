import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {RegisterMatchData} from "../model/register-match-data";
import {catchError, map} from "rxjs/operators";
import {Observable, throwError} from "rxjs";
import {Match} from "../model/match";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class RegisterMatchService {

  private baseUrl: string;
  // https://localhost:8443/api/tenniship/tournaments/{tournament}/matches
  private adminUrl: string;
  // https://localhost:8443/api/tenniship/admin/tournaments/{tournament}/matches/{group}
  private registerUrl: string;
  // https://localhost:8443/api/tenniship/tournaments/{tournament}/matches

  constructor(private http: HttpClient, private router: Router) {
    this.baseUrl = "/api/tenniship/tournaments/";
    this.adminUrl = "/api/tenniship/admin/tournaments/";
    this.registerUrl = "/api/tenniship/tournaments/";
  }

  getData(tournament: string) {
    let url = this.baseUrl + tournament + "/matches";
    console.log("getData");
    console.log(url);
    return this.http.get<RegisterMatchData>(url).pipe(
      map(response=>response),
      catchError(this.handleError)
    )
  }

  getAdminData(tournament: string, group: string) {
    let url = this.adminUrl + tournament + "/matches/" + group;
    return this.http.get<RegisterMatchData>(url).pipe(
      map(response=>response),
      catchError(this.handleError)
    )
  }

  registerMatch(tourName: string, match: Match): Observable<Match> {
    let url = this.registerUrl + tourName + "/matches"; // tourName could be obtained from the match object, but just in case
    console.log(url);
    console.log(match);
    return this.http.put<Match>(url,match).pipe(
      map(response => response),
      catchError(error => Observable.throw('Server error'))
    );
  }

  private handleError(error: HttpErrorResponse) {
    if (error.error instanceof ErrorEvent) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error.message);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong,
      console.error(
        `Backend returned code ${error.status}, ` +
        `body was: ${error.error}`);
    }
    return throwError(
      'Something bad happened; please try again later.');
  }
}
