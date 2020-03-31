import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TournamentSheetService} from "./tournament-sheet.service";
import {TournamentSheetData} from "../model/tournament-sheet-data";
import { catchError } from 'rxjs/operators';
import {ErrorService} from "../errors/errors.service"

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

  constructor(private route : ActivatedRoute, private tournamentSheetService: TournamentSheetService, 
    private errorService: ErrorService){
    this.tournament_id = route.snapshot.params.tournament_id;
  }

  ngOnInit():void {
    this.tournamentSheetService.getTournamentSheetData(this.tournament_id).subscribe(
      data => {
        this._tournamentSheetData = data;
        this.tournamentSheetService.setTournamentSheetData(data);
        console.log(data);
        console.log(this.getTournamentSheetData());
      },
      error => this.handleError(error)
    );
  }

  getTournamentName(): string {
    return this.tournament_id;
  }

  getTournamentSheetData(): TournamentSheetData {
    return this._tournamentSheetData;
  }

  public handleError(error: any) {
    this.errorService.setMsg("tournament "+ this.tournament_id);
    console.error(error);
  }

}