import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
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
export class TournamentSheetGroupsComponent implements OnInit,OnChanges {

  public _tournamentSheetData: TournamentSheetData;

  @Input()
  dataFinalTournament: TournamentSheetData;

  ngOnChanges(changes: SimpleChanges) {
    this.dataFinalTournament = changes.dataFinalTournament.currentValue;
    this._tournamentSheetData = this.dataFinalTournament;
  }

  constructor(public userService: UserService){ }

  ngOnInit():void {
  }

  getTournamentSheetGroups(): AuxiliarClass[] {
    return this._tournamentSheetData.groups;
  }

  getGroup(i): string {
    var groupIds: string[] = ["A", "B", "C", "D", "E", "F"];
    return groupIds[i];
  }

  isGroupStage(){
    return this._tournamentSheetData.quarters.length === 0;
  }

}
