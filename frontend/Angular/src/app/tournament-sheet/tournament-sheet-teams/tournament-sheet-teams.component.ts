import { Component, OnInit } from '@angular/core';
import {TournamentSheetService} from "../tournament-sheet.service";
import {TournamentSheetData} from "../../model/tournament-sheet-data";
import {AuxiliarClass} from "../../model/tournament-sheet-auxdata";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-tournament-teams',
  templateUrl: './tournament-sheet-teams.component.html',
  styleUrls: []
})
export class TournamentSheetTeamsComponent implements OnInit {

  public _tournamentSheetData: TournamentSheetData;

  constructor(private route : ActivatedRoute, private tournamentSheetService: TournamentSheetService){
    this._tournamentSheetData = this.tournamentSheetService._tournamentSheetAux;
  }

  ngOnInit():void {
  }

  getTournamentSheetTeams(): AuxiliarClass[][] {
    var primeros: AuxiliarClass[] = new Array(6)
    var segundos: AuxiliarClass[] = new Array(6)
    var terceros: AuxiliarClass[] = new Array(6)

    for(var j = 0; j < 6; j++){
      for(var i = 0; i<this._tournamentSheetData.groups.length; i++) {
        if (i == 0) {
          primeros[j] = this._tournamentSheetData.groups[j][i]
        }
        else if (i == 1) {
          segundos[j] = this._tournamentSheetData.groups[j][i]
        }
        else if (i == 2) {
          terceros[j] = this._tournamentSheetData.groups[j][i]
        }
      }
    }
    var teams: AuxiliarClass[][] = new Array(3)
    teams[0] = primeros
    teams[1] = segundos
    teams[2] = terceros
    return teams;

    }

}
