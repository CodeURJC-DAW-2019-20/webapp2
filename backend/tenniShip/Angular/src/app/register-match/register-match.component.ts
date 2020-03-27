import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {RegisterMatchData} from "../model/register-match-data";
import {Match} from "../model/match";
import {PageLengthService} from "../service/page-length.service";
import {RegisterMatchService} from "./register-match.service";

@Component({
  selector: 'app-register-match',
  templateUrl: './register-match.component.html',
})
export class RegisterMatchComponent implements OnInit {

  public tournament_id: string;
  public round: string;
  public allPlayed: boolean = false;
  public tournamentName: string;
  private admin: boolean;
  public actionUrl: string;
  // If admin: /TenniShip/RegisterMatch/Tournaments/{{tournament.name}}/Submission
  // If user:  /TenniShip/ADMIN/Tournaments/{{tournament.name}}/EditMatches/{{type}}/Submission
  private registerMatchData: RegisterMatchData;

  constructor(private pageLength: PageLengthService, private registerMatchService: RegisterMatchService, private route: ActivatedRoute) {
    this.tournament_id = route.snapshot.params.tournament_id;
  }

  ngOnInit(): void {
    // Needs a decision to be made about how to handle admin variation
    this.registerMatchService.getData(this.tournament_id).subscribe(
      data => {
        this.registerMatchData = data;
        this.round = data.round;
      }
    )
  }

  getMatchList(): Match[] {
    return this.registerMatchData.matchList;
  }

}
