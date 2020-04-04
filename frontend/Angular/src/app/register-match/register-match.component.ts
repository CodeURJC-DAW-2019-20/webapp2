import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {RegisterMatchData} from "../model/register-match-data";
import {Match} from "../model/match";
import {PageLengthService} from "../shared-services/page-length.service";
import {RegisterMatchService} from "./register-match.service";

@Component({
  selector: 'app-register-match',
  templateUrl: './register-match.component.html',
})
export class RegisterMatchComponent implements OnInit {

  public tournament_id: string;
  public round: string;
  public allPlayed: boolean = false;
  private admin: boolean;
  public actionUrl: string;
  // If admin: /TenniShip/RegisterMatch/Tournaments/{{tournament.name}}/Submission
  // If user:  /TenniShip/ADMIN/Tournaments/{{tournament.name}}/EditMatches/{{type}}/Submission
  private registerMatchData: RegisterMatchData;
  dirImagesPage: string ="/assets/resources/static/img/";

  constructor(private pageLength: PageLengthService, private registerMatchService: RegisterMatchService, private route: ActivatedRoute, private router: Router) {
    this.tournament_id = route.snapshot.params.tournament_id;
    // Needs a decision to be made about how to handle admin variation
    this.registerMatchService.getData(this.tournament_id).subscribe(
      data => {
        this.registerMatchData = data;
        this.registerMatchData.matches = data.matches;
        this.round = data.round;
      }
    );
  }

  ngOnInit(): void {

  }

  getMatchList(): Match[] {
    return this.registerMatchData.matches;
  }

  changePoints(i: number, localTeam: boolean, up: boolean) {
    let sum: number;
    if (up) {
      sum = 1;
    }
    else {
      sum = -1;
    }
    if (localTeam) {
      let aux = this.registerMatchData.matches[i].homePoints + sum;
      if ((aux >= 0) && (aux <= 3)) {
        this.registerMatchData.matches[i].homePoints = aux;
      }
    }
    else {
      let aux = this.registerMatchData.matches[i].awayPoints + sum;
      if ((aux >= 0) && (aux <= 3)) {
        this.registerMatchData.matches[i].awayPoints = aux;
      }
    }
  }

  submitMatch(i: number) {
    let aux = this.registerMatchData.matches[i];
    if (((aux.homePoints === 3) && (aux.awayPoints != 3)) || ((aux.homePoints != 3) && (aux.awayPoints === 3))) {
      this.registerMatchService.registerMatch(this.tournament_id, this.registerMatchData.matches[i]).subscribe();
      // IMPORTANT: You have to subscribe first, or the request won't be sent
      this.registerMatchService.registerMatch(this.tournament_id, this.registerMatchData.matches[i]);
      this.router.navigate(['/TenniShip', 'RegisterMatch', 'Tournaments', this.tournament_id]);
    }
    else {
      alert("Error: One and only one of the teams has to have 3 points");
    }
  }

}
