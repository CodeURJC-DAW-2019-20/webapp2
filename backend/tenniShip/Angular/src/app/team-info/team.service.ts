import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Team} from "../model/team";
import {Tournament} from "../model/tournament";
import {Match} from "../model/match";
import {GraphicTeam} from "../model/graphic-team";
import {pipe, throwError} from "rxjs";
import {catchError} from "rxjs/operators";
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';


@Injectable({providedIn: 'root'})
export class TeamService {

  teamsUrl : string;

  constructor(private http : HttpClient) {
    this.teamsUrl =  "/api/tenniship/teams/"
  }

  public getTeam(teamName: string){
    return this.http.get<Team>(this.teamsUrl+teamName).pipe(catchError(this.handleError));
  }

  public getTournamentList(teamName:string){
    return this.http.get<Tournament[]>(this.teamsUrl+teamName).pipe(catchError(this.handleError));
  }

  public getMatchesList(teamName:string){
    return this.http.get<Match[]>(this.teamsUrl+teamName).pipe(catchError(this.handleError));
  }

  public getGraphicData(teamName:string){
    return this.http.get<GraphicTeam>(this.teamsUrl+teamName).pipe(catchError(this.handleError));
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

  public getTeamFileData(teamName:string):Observable<any>{
    let url = this.teamsUrl+teamName;

    return this.http.get(url).pipe(
      map(response=>{
        this.extractMatchesList(response as any);
        this.extractTeamName(response as any);
        this.extractTournaments(response as any);
        this.extractTeamPlayers(response as any);
        this.extractPercentageWonMatches(response as any);
        this.extractPercentageLostMatches(response as any);
    })//, catchError(err => Observable.throw('Server error'))
    )
  }

  public getMoreMatches(teamName: string,page:number):Observable<any>{
    let url = this.teamsUrl + teamName +'/matches?page='+ page + '&size=2';

    return this.http.get(url).pipe(
      map(response=>this.extractMoreMatches(response as any)),
      catchError(err => Observable.throw('Server error'))
    )
  }

  public extractTeamName (response){
    return response.items.map(val => val.teamName)
  }

  public extractTeamPlayers(response){
    return response.items.map(val=>val.playerList)
  }

  public extractPercentageLostMatches(response){
    return response.items.map(val=>val.percentageLostMatches)
  }

  public extractPercentageWonMatches(response){
    return response.items.map(val=>val.percentageWonMatches)
  }

  public extractTournaments(response){
    return response.items.map(val=>val.TournamentList)
  }

  public extractMatchesList(response){
    return response.items.map(val=>val.matchesList)
  }

  public extractMoreMatches(response){
    return response.items.map(val=> val.matchesList)
  }

}

