import { Component, OnInit } from '@angular/core';
import {Match} from "../../model/match";
import {TeamService} from "../team.service";
import {ActivatedRoute} from "@angular/router";
import {Observable} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-matches-paginated',
  templateUrl: './matches-paginated.component.html'
})
export class MatchesPaginatedComponent implements OnInit {

  public _matchesListTable: Match[] = new Array<Match>();
  size = this._matchesListTable.length;
  team_id : string;
  url: string = '/api/TenniShip/Team/'

  constructor(private route:ActivatedRoute, private teamService : TeamService, private http : HttpClient) {
    this.team_id = route.snapshot.params.team_id;
  }

  ngOnInit(): void {
  }

  getMatchesListTable(): Match[] {
    return this._matchesListTable;
  }

  public getMoreMatches(page:number):Observable<any>{
    let theUrl = this.url + this.team_id +'/matches?page='+ page + '&size=2';

    return this.http.get<Match[]>(theUrl).pipe(
      map(response=>response),
      catchError(err => Observable.throw('Server error'))
    )
  }

  public addMatches(page:number){
    this.getMoreMatches(page).subscribe(
      data=>{
        this._matchesListTable.concat(data);
        console.log(this._matchesListTable);
      },
      error => this.handleError(error)
    )
  }

  public handleError(error: any) {
    console.error(error);
  }  
}
