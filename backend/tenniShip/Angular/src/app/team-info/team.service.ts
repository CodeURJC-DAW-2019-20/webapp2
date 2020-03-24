import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import { map } from 'rxjs/operators';
import {TeamFileData} from "../model/team-file-data";


@Injectable({providedIn: 'root'})
export class TeamService {

  teamsUrl : string;
  private _teamFileAux : TeamFileData;

  constructor(private http : HttpClient) {
    this.teamsUrl =  "/api/tenniship/teams/"
  }

  public getTeamFileData(teamName: string){
    return this.http.get<TeamFileData>(this.teamsUrl+teamName).pipe(
      map(response=>response),
      catchError(this.handleError));
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


  setTeamFileAux(value: TeamFileData) {
    this._teamFileAux = value;
  }
}

