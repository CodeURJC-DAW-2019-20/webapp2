import {Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import {map } from 'rxjs/operators';
import {TournamentSheetData} from "../model/tournament-sheet-data";


@Injectable({providedIn: 'root'})
export class TournamentSheetService {

  tournamentsUrl : string;
  public _tournamentSheetAux : TournamentSheetData;

  constructor(private http : HttpClient) {
    this.tournamentsUrl =  "/api/tenniship/tournaments/"
  }

  public getTournamentSheetData(tournamentName: string){
    return this.http.get<TournamentSheetData>(this.tournamentsUrl+tournamentName).pipe(
      map(response=>response),
      catchError(this.handleError));
  }

  public deleteTournamentSheet(tournamentName: string){
      this.http.delete(this.tournamentsUrl+tournamentName).subscribe
      (response => console.log(response),error => console.error(error));
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

  setTournamentSheetData(value: TournamentSheetData) {
    this._tournamentSheetAux = value;
  }
}

