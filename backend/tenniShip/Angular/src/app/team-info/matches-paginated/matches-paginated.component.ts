import { Component, OnInit } from '@angular/core';
import {Match} from "../../model/match";
import {TeamService} from "../team.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-matches-paginated',
  templateUrl: './matches-paginated.component.html',
  styleUrls: ['./matches-paginated.component.css']
})
export class MatchesPaginatedComponent implements OnInit {

  private _matchesListTable: Match[] = new Array<Match>();
  size = this._matchesListTable.length;
  team_id : string;

  constructor(private route:ActivatedRoute, private teamService : TeamService) {
    this.team_id = route.snapshot.params.team_id;
  }

  ngOnInit(): void {
  }

  getMatchesListTableUpdate(page:number) {
    this.addMatchesToTheList(page,this._matchesListTable);
    return this._matchesListTable;
  }

  getMatchesListTable(): Match[] {
    return this._matchesListTable;
  }

  addMatchesToTheList(page:number, matchesList: Match[]){
    this.teamService.getMoreMatches(this.team_id,page).subscribe(data=>matchesList.concat(data));
  }
}
