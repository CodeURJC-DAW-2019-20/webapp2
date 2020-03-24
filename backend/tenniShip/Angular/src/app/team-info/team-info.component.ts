import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Team} from "../model/team";
import {TeamService} from "./team.service";
import {Tournament} from "../model/tournament";
import {Match} from "../model/match";
import {GraphicTeam} from "../model/graphic-team";

@Component({
  selector: 'app-team-info',
  templateUrl: './team-info.component.html',
  styleUrls: ['./team-info.component.css']
})
export class TeamInfoComponent implements OnInit {

  public team:Team;
  public team_id : string;
  public tournamentList : Tournament [] = new Array<Tournament>();
  public graphic_data : GraphicTeam ;
  public matchesList : Match [] = new Array<Match>();

  constructor(private route : ActivatedRoute, private teamService: TeamService){
    this.team_id = route.snapshot.params.team_id;
  }

  ngOnInit():void {
    this.searchTeam(this.team_id);
  }

  searchTeam(teamName:string){
    this.teamService.getTeamFileData(teamName).subscribe(
      data=>{
        this.team.teamName = data;
        this.team.playerList = data;
        this.matchesList = data;
        this.tournamentList = data;
        this.graphic_data = data;
      }
    )
  }


  getTeam(){
    return this.team;
  }

  getTournamentList(): Tournament[] {
    return this.tournamentList;
  }

  getGraphicData(): GraphicTeam {
    return this.graphic_data;
  }

  getMatchesList(): Match[] {
    return this.matchesList;
  }

  handleError(error : any){
    console.error(error);
  }
}
