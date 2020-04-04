import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {TeamService} from "./team.service";
import {TeamFileData} from "../model/team-file-data";
import {ErrorService} from "../errors/errors.service";
import {ImageService} from "../shared-services/image.service";
import { SpinnerService } from '../shared-services/spinner.service';

@Component({
  selector: 'app-team-info',
  templateUrl: './team-info.component.html',
  styleUrls: ['./team-info.component.css'],
  providers: [TeamService]
})
export class TeamInfoComponent implements OnInit {
  public _teamFileData: TeamFileData;
  public team_id: string;
  public imagesTeam;
  public playerArrayImage: any [];
  public tournamentArrayImage: any [];
  dir:string;
  constructor(private route: ActivatedRoute, private teamService: TeamService, private errorService: ErrorService, private imageService:ImageService, private spinerService: SpinnerService) {}

  ngOnInit(): void {
    this.refreshUrl();
  }

  public refreshUrl():void{
    this.route.params.subscribe(params => {
      this.team_id = params.team_id;
      this.refreshInfo();
    })
  }
  createImageFromBlob(image: Blob,i:number) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      if (i==0){
        this.imagesTeam = reader.result;
      } else if (i<6) {
        this.playerArrayImage[i-1]=reader.result;
      } else {
        this.tournamentArrayImage[i-6] = reader.result;
      }
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }


  public refreshInfo():void{
    this.playerArrayImage = new Array(5);
    this.spinerService.changeLoading(true);
    this.teamService.getTeamFileData(this.team_id).subscribe(
      data => {
        this._teamFileData = data;
        this.tournamentArrayImage = new Array(this._teamFileData.tournamentList.length);
        for(let i=6;i<this.tournamentArrayImage.length+6;i++){
          this.imageService.getTournamentImage(<string><unknown>(this._teamFileData.tournamentList[i-6])).subscribe(
            image=>{
              this.createImageFromBlob(image,i);
            },
          );
        }
        for(let i=0;i<6;i++){
          this.imageService.getTeamImage(this.team_id,i).subscribe(
            image=>{
              this.createImageFromBlob(image,i);
              if (i === 5) {
                this.spinerService.changeLoading(false);
              }
            },
          );
        }
        this.teamService.setWinPercentage(this._teamFileData.percentageWonMatches);
      },error => {this.handleError(error),this.spinerService.changeLoading(false);}
    );

  }

  getPlayerImage(i:number){
    return this.playerArrayImage[i];
  }

  getTournamentImage(i:number){
    if(this.tournamentArrayImage.length===i){
      return this.tournamentArrayImage[0];
    }
    return this.tournamentArrayImage[i];
  }

  getTeamFileData(): TeamFileData {
    return this._teamFileData;
  }

  public handleError(error: any) {
    this.errorService.setMsg("team "+ this.team_id);
    console.error(error);
  }

}
