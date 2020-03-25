import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TournamentSheetService} from "./tournament-sheet.service";
import {TournamentSheetData} from "../model/tournament-sheet-data";

@Component({
  selector: 'app-tournament-sheet',
  templateUrl: './tournament-sheet.component.html',
  styleUrls: []
})
export class TournamentSheetComponent implements OnInit {

  public isMenuCollapsed = true;
  public active = "groups";
  public _tournamentSheetData: TournamentSheetData;
  public tournament_id: string;

  constructor(private route : ActivatedRoute, private tournamentSheetService: TournamentSheetService){
    this.tournament_id = route.snapshot.params.tournament_id;
  }

  ngOnInit():void {
    this.tournamentSheetService.getTournamentSheetData(this.tournament_id).subscribe(
      data => {
        this._tournamentSheetData = data;
        this.tournamentSheetService.setTournamentSheetData(data);
        console.log(data);
        console.log(this.getTournamentSheetData());
      }
    );
  }

  getTournamentName(): string {
    return this.tournament_id;
  }

  getTournamentSheetData(): TournamentSheetData {
    return this._tournamentSheetData;
  }

}
