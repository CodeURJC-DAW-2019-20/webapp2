import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Team} from "../model/team";
import {TeamService} from "../Services/team.service";
import {HttpClient, HttpClientJsonpModule, HttpHeaders} from "@angular/common/http";


@Component({
  selector: 'app-team-info',
  templateUrl: './team-info.component.html',
  styleUrls: ['./team-info.component.css']
})
export class TeamInfoComponent implements OnInit {

  private _team:Team;
  public team_id : string;

  constructor(private route : ActivatedRoute, private teamService: TeamService){
    this.team_id = route.snapshot.params.team_id;
  }

  ngOnInit():void {
    this.teamService.getTeam(this.team_id).subscribe(
      data => {
        this._team = data;
        console.log(data);
      },
      error=>this.handleError(error)
    );
  }

  getTeam(){
    return this._team;
  }

  handleError(error : any){
    console.error(error);
  }
}
