import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {Tournament} from "../model/tournament";

@Injectable({
  providedIn: 'root'
})
export class SelectTournamentService {

  private baseUrl: string;
  // https://localhost:8443/api/tenniship/tournaments/teams/matches?page=pageNumber&size=pageSize

  constructor(private http:HttpClient) {
    this.baseUrl = "/api/tenniship/tournaments/teams/matches/";
  }

  public getPage(pageNumber:number, pageSize:number) {
    let url = this.baseUrl + "?page=" + pageNumber + "&size=" + pageSize;
    return this.http.get<Tournament[]>(url).pipe(
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
