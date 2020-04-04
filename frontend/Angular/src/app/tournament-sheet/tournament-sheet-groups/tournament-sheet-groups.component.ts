import { Component, OnInit } from '@angular/core';
import {TournamentSheetService} from "../tournament-sheet.service";
import {TournamentSheetData} from "../../model/tournament-sheet-data";
import {AuxiliarClass} from "../../model/tournament-sheet-auxdata";
import {ActivatedRoute} from "@angular/router";
import { UserService} from "../../shared-services/user.service";

@Component({
  selector: 'app-tournament-groups',
  templateUrl: './tournament-sheet-groups.component.html',
  styleUrls: []
})
export class TournamentSheetGroupsComponent implements OnInit {

  public _tournamentSheetData: TournamentSheetData;

  constructor(private route : ActivatedRoute, private tournamentSheetService: TournamentSheetService,
    public userService: UserService){
    this._tournamentSheetData = this.tournamentSheetService._tournamentSheetAux;
  }

  ngOnInit():void {
  }

  getTournamentSheetGroups(): AuxiliarClass[] {
    return this._tournamentSheetData.groups;
  }

  getGroup(i): string {
    var groupIds: string[] = ["A", "B", "C", "D", "E", "F"];
    return groupIds[i];
  }

}
