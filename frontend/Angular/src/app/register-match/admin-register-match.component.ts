import { Component, OnInit } from '@angular/core';
import {RegisterMatchData} from "../model/register-match-data";
import {PageLengthService} from "../shared-services/page-length.service";
import {RegisterMatchService} from "./register-match.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ImageService} from "../shared-services/image.service";
import {SpinnerService} from "../shared-services/spinner.service";
import {Match} from "../model/match";

@Component({
  selector: 'app-admin-register-match',
  templateUrl: './register-match.component.html',
})
export class AdminRegisterMatchComponent implements OnInit {

  public tournament_id: string;
  public round: string;
  public allPlayed: boolean = false;
  private admin: boolean;
  public actionUrl: string;
  // If admin: /TenniShip/RegisterMatch/Tournaments/{{tournament.name}}/Submission
  // If user:  /TenniShip/ADMIN/Tournaments/{{tournament.name}}/EditMatches/{{type}}/Submission
  private registerMatchData: RegisterMatchData;
  dirImagesPage: string ="/assets/resources/static/img/";
  private homeImages = [];
  private awayImages = [];
  private group: string;

  constructor(private pageLength: PageLengthService, private registerMatchService: RegisterMatchService,
              private route: ActivatedRoute, private router: Router, private imageService: ImageService,
              private spinnerService: SpinnerService, private pageLengthService: PageLengthService) {

    this.tournament_id = route.snapshot.params.tournament_id;
    this.group = route.snapshot.params.group_id;
    this.spinnerService.changeLoading(true);
    // Needs a decision to be made about how to handle admin variation
    this.registerMatchService.getAdminData(this.tournament_id,this.group).subscribe(
      data => {
        if (typeof data === 'undefined') {
          this.router.navigate(['', 'TenniShip', 'Error']);
        }
        else {
          this.registerMatchData = data;
          this.registerMatchData.matches = data.matches;
          this.round = data.round;

          for (let i = 0; i < data.matches.length; i++) {
            this.imageService.getTeamImage(this.registerMatchData.matches[i].team1.teamName, 0).subscribe(
              image => {
                this.createImageFromBlob(image, i, true);
              },
            );
            this.imageService.getTeamImage(this.registerMatchData.matches[i].team2.teamName, 0).subscribe(
              image => {
                this.createImageFromBlob(image, i, false);
                if (i === this.registerMatchData.matches.length - 1) {
                  this.pageLengthService.updatePageLength();
                  this.spinnerService.changeLoading(false);
                }
              },
            );
          }
        }
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

  submitMatch(i: number){
    let aux = this.registerMatchData.matches[i];
    if (((aux.homePoints === 3) && (aux.awayPoints != 3)) || ((aux.homePoints != 3) && (aux.awayPoints === 3))) {
      this.registerMatchService.registerAdminMatch(this.tournament_id, this.registerMatchData.matches[i], this.group).subscribe();
      // IMPORTANT: You have to subscribe first, or the request won't be sent
      this.registerMatchService.registerAdminMatch(this.tournament_id, this.registerMatchData.matches[i], this.group);
      alert("Match updated succesfully");
      this.router.navigate(['', 'TenniShip']);
    }
    else {
      alert("Error: One and only one of the teams has to have 3 points");
    }
  }

  getHomeImage(i: number) {
    return this.homeImages[i];
  }

  getAwayImage(i: number) {
    return this.awayImages[i];
  }

  createImageFromBlob(image: Blob, i:number, home: boolean) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      if (home) {
        this.homeImages[i] = reader.result;
      }
      else {
        this.awayImages[i] = reader.result;
      }
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }
}
