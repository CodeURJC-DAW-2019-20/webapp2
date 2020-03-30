import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";
import { map } from 'rxjs/operators';
import {TeamFileData} from "../model/team-file-data";


@Injectable({providedIn: 'root'})
export class TeamService {

  teamsUrl : string;
  private winRate: number;

  constructor(private http : HttpClient) {
    this.teamsUrl =  "/api/tenniship/teams/"
  }

  public getTeamFileData(teamName: string){
    return this.http.get<TeamFileData>(this.teamsUrl+teamName).pipe(
      map(response=>response),
      catchError(error => this.handleError(error)));
  }

  getWinPercentage(): number {
    return this.winRate;
  }

  setWinPercentage(value: number) {
    this.winRate = value;
  }

  public handleError(error: any) {
		console.error(error);
		return Observable.throw("Server error (" + error.status + "): " + error.text())
	}

}

