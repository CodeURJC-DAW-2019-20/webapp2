import { Injectable } from '@angular/core';
import{HttpClient,HttpHeaders} from "@angular/common/http";
import {Team} from "../model/team";
import {Observable, throwError} from "rxjs";
import {catchError, map} from "rxjs/operators";


@Injectable({
  providedIn: 'root'
})
export class TeamService {

  private teamsUrl : string;

  constructor(private http : HttpClient) {
    this.teamsUrl =  "https://localhost:8443/api/tenniship/teams/"
  }

  public getTeam(teamName: string){
    return this.http.get<Team>(this.teamsUrl+teamName).pipe(
      map(response=>response),
      catchError(err=> throwError(err))
    );
  }

}

