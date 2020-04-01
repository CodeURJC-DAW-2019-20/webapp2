import {Component, OnInit, SystemJsNgModuleLoader } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TournamentSheetService} from "./../tournament-sheet.service";
import {TournamentSheetData} from "../../model/tournament-sheet-data";

@Component({
  selector: 'app-tournament-final',
  templateUrl: './tournament-sheet-final.component.html',
  styleUrls: []
})
export class TournamentSheetFinalComponent implements OnInit {

  public _tournamentSheetData: TournamentSheetData;

  constructor(private route : ActivatedRoute, private tournamentSheetService: TournamentSheetService){ 
    this._tournamentSheetData = this.tournamentSheetService._tournamentSheetAux;
  }

  ngOnInit(): void {
  }

  getQuartersTeam(i, homeORaway): string {
    if (Object.keys(this._tournamentSheetData.quarters).length == 0) {
      return "-Keep playing";
    }  
    if (homeORaway == 0) {
      if (this._tournamentSheetData.quarters[i].homePoints > this._tournamentSheetData.quarters[i].homePoints) {
        return this._tournamentSheetData.quarters[i].team1.teamName;
      }
      return this._tournamentSheetData.quarters[i].team2.teamName;
    }
    else if (homeORaway == 1) {
      if (this._tournamentSheetData.quarters[i].homePoints < this._tournamentSheetData.quarters[i].homePoints) {
        return this._tournamentSheetData.quarters[i].team1.teamName;
      }
      return this._tournamentSheetData.quarters[i].team2.teamName;
    }
  }

  getQuartersScore(i, homeORaway): string {
    if (Object.keys(this._tournamentSheetData.quarters).length == 0) {
      return "";
    }
    if (homeORaway == 0) {
      return Math.max(this._tournamentSheetData.quarters[i].homePoints, this._tournamentSheetData.quarters[i].awayPoints).toString();
    }
    return Math.min(this._tournamentSheetData.quarters[i].homePoints, this._tournamentSheetData.quarters[i].awayPoints).toString();
  }

  getSemisTeam(i, homeORaway): string {
    if (Object.keys(this._tournamentSheetData.theSemiFinals).length == 0) {
      return "-Keep playing";
    }  
    if (homeORaway == 0) {
      if (this._tournamentSheetData.theSemiFinals[i].homePoints > this._tournamentSheetData.theSemiFinals[i].homePoints) {
        return this._tournamentSheetData.theSemiFinals[i].team1.teamName;
      }
      return this._tournamentSheetData.theSemiFinals[i].team2.teamName;
    }
    else if (homeORaway == 1) {
      if (this._tournamentSheetData.theSemiFinals[i].homePoints < this._tournamentSheetData.theSemiFinals[i].homePoints) {
        return this._tournamentSheetData.theSemiFinals[i].team1.teamName;
      }
      return this._tournamentSheetData.theSemiFinals[i].team2.teamName;
    }
  }

  getSemisScore(i, homeORaway): string {
    if (Object.keys(this._tournamentSheetData.theSemiFinals).length == 0) {
      return "";
    }
    if (homeORaway == 0) {
      return Math.max(this._tournamentSheetData.theSemiFinals[i].homePoints, this._tournamentSheetData.theSemiFinals[i].awayPoints).toString();
    }
    return Math.min(this._tournamentSheetData.theSemiFinals[i].homePoints, this._tournamentSheetData.theSemiFinals[i].awayPoints).toString();
  }

  getFinalTeam(i, homeORaway): string {
    if (Object.keys(this._tournamentSheetData.theFinal).length == 0) {
      return "-Keep playing";
    }  
    if (homeORaway == 0) {
      if (this._tournamentSheetData.theFinal[i].homePoints > this._tournamentSheetData.theFinal[i].homePoints) {
        return this._tournamentSheetData.theFinal[i].team1.teamName;
      }
      return this._tournamentSheetData.theFinal[i].team2.teamName;
    }
    else if (homeORaway == 1) {
      if (this._tournamentSheetData.theFinal[i].homePoints < this._tournamentSheetData.theFinal[i].homePoints) {
        return this._tournamentSheetData.theFinal[i].team1.teamName;
      }
      return this._tournamentSheetData.theFinal[i].team2.teamName;
    }
  }

  getFinalScore(i, homeORaway): string {
    if (Object.keys(this._tournamentSheetData.theFinal).length == 0) {
      return "";
    }
    if (homeORaway == 0) {
      return Math.max(this._tournamentSheetData.theFinal[i].homePoints, this._tournamentSheetData.theFinal[i].awayPoints).toString();
    }
    return Math.min(this._tournamentSheetData.theFinal[i].homePoints, this._tournamentSheetData.theFinal[i].awayPoints).toString();
  }

}
