import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Match} from "../../model/match";
import {catchError, map} from "rxjs/operators";
import {throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MatchesPaginatedService {

  teamUrl:string;
  // https://localhost:8443/api/tenniship/teams/{team}/matches?page=pageRequested&?size=sizeListMatches

  constructor(private http:HttpClient) {
    this.teamUrl="/api/tenniship/teams/"
  }

  public getFirstPage(team_id:string){
    let url = this.teamUrl + team_id+'/matches?page=0&size=2';
    return this.http.get<Match[]>(url).pipe(
      map(response =>response),
      catchError(this.handleError)
    )
  }

  public getMoreMatches(team_id:string,page:number){
    let url = this.teamUrl + team_id + '/matches?page=' + page + '&size=2';
    return this.http.get<Match[]>(url).pipe(
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
