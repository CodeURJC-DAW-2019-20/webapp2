import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TeamService} from "./team.service";
import {TeamFileData} from "../model/team-file-data";
import {ErrorService} from "../errors/errors.service";

@Component({
  selector: 'app-team-info',
  templateUrl: './team-info.component.html',
  styleUrls: ['./team-info.component.css']
})
export class TeamInfoComponent implements OnInit {
  public _teamFileData: TeamFileData;
  public team_id: string;
  public dir: string = './../../../../images/registered/';

  constructor(private route: ActivatedRoute, private teamService: TeamService, private errorService: ErrorService) {}

  ngOnInit(): void {
    this.refreshUrl()
  }

  public refreshUrl():void{
    this.route.params.subscribe(params => {
      this.team_id = params.team_id;
      this.refreshInfo();
    })
  }

  public refreshInfo():void{
    this.teamService.getTeamFileData(this.team_id).subscribe(
      data => {
        console.log(data);
        this._teamFileData = data;
        this.teamService.setWinPercentage(this._teamFileData.percentageWonMatches);
        console.log(this.getTeamFileData());
      },error => this.handleError(error)
    );
  }


  getTeamFileData(): TeamFileData {
    return this._teamFileData;
  }

  public handleError(error: any) {
    this.errorService.setMsg("team "+ this.team_id);
    console.error(error);
  }

}
