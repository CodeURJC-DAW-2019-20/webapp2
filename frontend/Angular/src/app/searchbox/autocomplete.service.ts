import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Tournament} from "../model/tournament";
import {catchError, map} from "rxjs/operators";
import {throwError} from "rxjs";
import {Team} from "../model/team";

@Injectable({
  providedIn: 'root'
})
export class AutocompleteService {

  private tourUrl: string;
  private teamUrl: string;

  constructor(private http: HttpClient) {
    this.tourUrl = "/api/tenniship/tournaments";
    this.teamUrl = "/api/tenniship/teams";
  }

  public getAllTournaments() {
    return this.http.get<Tournament[]>(this.tourUrl).pipe(
      map(response=>response),
      catchError(this.handleError)
    )
  }

  public getAllTeams() {
    return this.http.get<Team[]>(this.teamUrl).pipe(
      map(response=>response),
      catchError(this.handleError)
    )
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
