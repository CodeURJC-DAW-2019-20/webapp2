import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Team} from "../model/team";
import {TeamService} from "./team.service";
import {HttpClient, HttpClientJsonpModule, HttpHeaders} from "@angular/common/http";
import {Tournament} from "../model/tournament";
import {Match} from "../model/match";
import {GraphicTeam} from "../model/graphic-team";


@Component({
  selector: 'app-team-info',
  templateUrl: './team-info.component.html',
  styleUrls: ['./team-info.component.css']
})
export class TeamInfoComponent implements OnInit {

  private team:Team;
  public team_id : string;
  private _tournaments : Tournament [];
  private _graphic_data : GraphicTeam;
  private _matchesList : Match [];

  constructor(private route : ActivatedRoute, private teamService: TeamService){
    this.team_id = route.snapshot.params.team_id;
  }

  ngOnInit():void {
    this.teamService.getTeam(this.team_id).subscribe((data:Team)=>this.team);
    this.teamService.getGraphicData(this.team_id).subscribe((data:GraphicTeam)=>this._graphic_data);
    this.teamService.getMatchesList(this.team_id).subscribe((data:Match[])=>this._matchesList);
    this.teamService.getTournamentList(this.team_id).subscribe((data:Tournament[])=>this._tournaments)
  }

  getTeam(){
    return this.team;
  }

  get tournaments(): Tournament[] {
    return this._tournaments;
  }

  get graphic_data(): GraphicTeam {
    return this._graphic_data;
  }

  get matchesList(): Match[] {
    return this._matchesList;
  }

  handleError(error : any){
    console.error(error);
  }
}
