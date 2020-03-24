import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TeamService} from "./team.service";
import {TeamFileData} from "../model/team-file-data";

@Component({
  selector: 'app-team-info',
  templateUrl: './team-info.component.html',
  styleUrls: ['./team-info.component.css']
})
export class TeamInfoComponent implements OnInit {

  public _teamFileData: TeamFileData;
  public team_id: string;

  constructor(private route : ActivatedRoute, private teamService: TeamService){
    this.team_id = route.snapshot.params.team_id;
  }

  ngOnInit():void {
    this.teamService.getTeamFileData(this.team_id).subscribe(
      data => {
        this._teamFileData = data;
        console.log(data);
        console.log(this.getTeamFileData());
      }
    );
  }

  getTeamFileData(): TeamFileData {
    return this._teamFileData;
  }

}
