import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Team} from "../model/team";
import {Tournament} from "../model/tournament";
import {Match} from "../model/match";
import {GraphicTeam} from "../model/graphic-team";
import {pipe, throwError} from "rxjs";
import {catchError} from "rxjs/operators";


@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private teamsUrl : string;

  constructor(private http : HttpClient) {
    this.teamsUrl =  "api/tenniship/teams/"
  }

  public getTeam(teamName: string){
    return this.http.get<Team>(this.teamsUrl+teamName).pipe(catchError(this.handleError));
    ;
  }

  public getTournamentList(teamName:string){
    return this.http.get<Tournament[]>(this.teamsUrl+teamName)
  }

  public getMatchesList(teamName:string){
    return this.http.get<Match[]>(this.teamsUrl+teamName)
  }

  public getGraphicData(teamName:string){
    return this.http.get<GraphicTeam>(this.teamsUrl+teamName)
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


  //
  // public extractTeamName (response:Response){
  //   return response.json().items.map(val => val.teamName)
  // }
  //
  // public extractTeamPlayers(response: Response){
  //   return response.json().items.map(val=>val.players)
  // }
  //
  // public extractPercentageLostMatches(response:Response){
  //   return response.json().items.map(val=>val.percentageLostMatches)
  // }
  //
  // public extractPercentageWonMatches(response:Response){
  //   return response.json().items.map(val=>val.percentageWonMatches)
  // }
  //
  // public extractTournaments(response:Response){
  //   return response.json().items.map(val=>val.Tournaments)
  // }
  //
  // public extractMatchesList(response: Response){
  //   return response.json().items.map(val=>val.matchesList)
  // }

}

