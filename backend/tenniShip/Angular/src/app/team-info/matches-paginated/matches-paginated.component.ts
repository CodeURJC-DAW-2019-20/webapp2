import { Component, OnInit } from '@angular/core';
import {Match} from "../../model/match";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {MatchesPaginatedService} from "./matches-paginated.service";

@Component({
  selector: 'app-matches-paginated',
  templateUrl: './matches-paginated.component.html',
  styleUrls: []
})
export class MatchesPaginatedComponent implements OnInit {

  public _matchesListTable: Match[] = new Array<Match>();
  size = this._matchesListTable.length;
  team_id : string;
  page:number=0;
  public _matchesListTableAux : Match[] = new Array<Match>();
  public pagingConfirmation:boolean = true;

  constructor(private route:ActivatedRoute, private matchesPaginatedService : MatchesPaginatedService, private http : HttpClient) {
    this.team_id = route.snapshot.params.team_id;
  }

  ngOnInit(): void {
    this.matchesPaginatedService.getFirstPage(this.team_id).subscribe(
      data=>{
        this._matchesListTable=data;
        console.log(data);
      }
    );
    this.page++;
  }

  getMatchesListTable(): Match[] {
    return this._matchesListTable;
  }

  public addMatches(){
    this.matchesPaginatedService.getMoreMatches(this.team_id,this.page).subscribe(
      data=>{
        this._matchesListTableAux=data;
        console.log(this._matchesListTable);
        if(typeof this._matchesListTableAux[0]==='undefined'){
          this.pagingConfirmation=false;
        }else {
          this._matchesListTable.push(this._matchesListTableAux[0]);
          if(typeof this._matchesListTableAux[1]==='undefined'){
            this.pagingConfirmation=false;
          }else{
            this._matchesListTable.push(this._matchesListTableAux[1]);
          }
        }
      }
    );
    this._matchesListTableAux = new Array<Match>();
    this.page++;
  }

}

