import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { Observable } from "rxjs";
import { catchError } from "rxjs/operators";
import { map } from 'rxjs/operators';
import { TournamentSheetData } from "../model/tournament-sheet-data";


@Injectable({ providedIn: 'root' })
export class TournamentSheetService {

  tournamentsUrl: string;
  public _tournamentSheetAux: TournamentSheetData;

  constructor(private http: HttpClient) {
    this.tournamentsUrl = "/api/tenniship/"
  }

  public getTournamentSheetData(tournamentName: string) {
    return this.http.get<TournamentSheetData>(this.tournamentsUrl + 'tournaments/' + tournamentName).pipe(
      map(response => response),
      catchError(error => this.handleError(error)));
  }

  public deleteTournamentSheet(tournamentName: string) {
    return this.http.delete(this.tournamentsUrl + 'admin/tournaments/' + tournamentName).pipe(
      map(response => response),
      catchError(error => this.handleError(error)));
  }

  private handleError(error: any) {
    console.error(error);
    return Observable.throw("Server error (" + error.status + "): " + error.text())
  }

  setTournamentSheetData(value: TournamentSheetData) {
    this._tournamentSheetAux = value;
  }
}

