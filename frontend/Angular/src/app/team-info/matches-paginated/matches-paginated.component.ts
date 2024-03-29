import { Component, OnInit } from '@angular/core';
import {Match} from "../../model/match";
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {MatchesPaginatedService} from "./matches-paginated.service";

@Component({
  selector: 'app-matches-paginated',
  templateUrl: './matches-paginated.component.html'
})
export class MatchesPaginatedComponent implements OnInit {

  public _matchesListTable: Match[] ;
  team_id : string;
  page:number=0;
  public _matchesListTableAux : Match[];
  public pagingConfirmation:boolean;

  constructor(private route:ActivatedRoute, private matchesPaginatedService : MatchesPaginatedService) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.team_id = params.team_id;
      this.page=0;
      this._matchesListTableAux= new Array<Match>();
      this._matchesListTable= new Array<Match>();
      this.pagingConfirmation=true;
      this.matchesPaginatedService.getFirstPage(this.team_id).subscribe(
        data=>{
          this._matchesListTable = data;
        }
      );
      this.page++;
    })
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
          this.pagingConfirmation = false;
        }else {
          this._matchesListTable.push(this._matchesListTableAux[0]);
          if(typeof this._matchesListTableAux[1]==='undefined'){
            this.pagingConfirmation = false;
          }else{
            this._matchesListTable.push(this._matchesListTableAux[1]);
          }
        }
      },
      error => this.handleError(error)
    );
    this._matchesListTableAux = new Array<Match>();
    this.page++;
  }


  public handleError(error: any) {
    console.error(error);
  }
}

