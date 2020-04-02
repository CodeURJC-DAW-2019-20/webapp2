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

  public getTeamImage(teamName:string){
    let url : string = this.urlFather + 'teams/'+teamName+'/image/0';
    return this.http.get<File>(url).pipe(
      map(response=>response),
      catchError(error => Observable.throw("Server error (" + error.status + "): " + error.text()))
    )
  }
}
