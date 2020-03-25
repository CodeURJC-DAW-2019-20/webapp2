import { Component, OnInit } from '@angular/core';
import {Tournament} from "../model/tournament";

@Component({
  selector: 'app-select-tournament',
  templateUrl: './select-tournament.component.html',
})
export class SelectTournamentComponent implements OnInit {

  private _tournamentList: Tournament[] = new Array<Tournament>();
  public finished: Boolean;

  constructor() {

  }

  ngOnInit(): void {
    this._tournamentList = [{name : "Champions League"}, {name : "EuroCup"}, {name : "Davis Cup"}];
    this.finished = Object.keys(this._tournamentList).length === 0;
  }

  getTournamentList(): Tournament[] {
    return this._tournamentList;
  }


}
