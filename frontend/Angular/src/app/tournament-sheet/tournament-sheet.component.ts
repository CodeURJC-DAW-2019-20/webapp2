import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TournamentSheetService} from "./tournament-sheet.service";
import {TournamentSheetData} from "../model/tournament-sheet-data";
import { catchError } from 'rxjs/operators';
import {ErrorService} from "../errors/errors.service"
import {UserService} from "../shared-services/user.service";
import {User} from "../model/user.model";
import {ImageService} from "../shared-services/image.service";
import { SpinerService } from '../shared-services/spiner.service';

@Component({
  selector: 'app-tournament-sheet',
  templateUrl: './tournament-sheet.component.html',
  styleUrls: []
})
export class TournamentSheetComponent implements OnInit {

  public isMenuCollapsed = true;
  public active = "groups";
  public _tournamentSheetData: TournamentSheetData;
  public tournament_id: string;
  public imageTournament;

  constructor(private route : ActivatedRoute, private tournamentSheetService: TournamentSheetService,
    private errorService: ErrorService, public userService:UserService, private imageService: ImageService, private spinerService: SpinerService){
  }

  public refreshPage(){
    this.route.params.subscribe(params => {
      this.tournament_id = this.route.snapshot.params.tournament_id;
      this.spinerService.changeLoading(true);
      this.tournamentSheetService.getTournamentSheetData(this.tournament_id).subscribe(
        data => {
          this._tournamentSheetData = data;
          this.tournamentSheetService.setTournamentSheetData(data);

          this.imageService.getTournamentImage(this._tournamentSheetData.tournament.name).subscribe(
            image=>{
              this.createImageFromBlob(image);
              this.spinerService.changeLoading(false);
            },
          );
        },
        error => {this.handleError(error);
          this.spinerService.changeLoading(false);
        }
      );
    });
  }

  ngOnInit():void {
    this.refreshPage();
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageTournament = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getTournamentName(): string {
    return this.tournament_id;
  }

  getTournamentImage() {
    return this.imageTournament;
  }

  getTournamentSheetData(): TournamentSheetData {
    return this._tournamentSheetData;
  }

  public handleError(error: any) {
    this.errorService.setMsg("tournament "+ this.tournament_id);
    console.error(error);
  }

  // public isAdmin():boolean{
  //   return this.currentUser.roles.includes("ROLE_ADMIN");
  // }

}
