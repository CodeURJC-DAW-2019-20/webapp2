import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {catchError, map} from "rxjs/operators";
import {Observable} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class ImageService {

  urlFather : string = '/api/tenniship/';

  constructor(private  http : HttpClient) { }

  public getTeamImage(teamName:string,i : number):Observable<Blob>{
    let url : string = this.urlFather + 'teams/'+teamName+'/image/'+i;
    return this.http.get(url,{responseType:'blob'});
  }

  public getTournamentImage(tournamentName:string):Observable<Blob>{
    let url : string = this.urlFather + 'tournaments/'+tournamentName+'/image';
    return this.http.get(url,{responseType:'blob'});
  }

}
