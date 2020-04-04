import { Component, OnInit } from '@angular/core';
import {TournamentSheetService} from "../tournament-sheet.service";
import {TournamentSheetData} from "../../model/tournament-sheet-data";
import {AuxiliarClass} from "../../model/tournament-sheet-auxdata";
import {ActivatedRoute} from "@angular/router";
import {ImageService} from "../../shared-services/image.service";

@Component({
  selector: 'app-tournament-teams',
  templateUrl: './tournament-sheet-teams.component.html',
  styleUrls: []
})
export class TournamentSheetTeamsComponent implements OnInit {

  public _tournamentSheetData: TournamentSheetData;
  public teamArrayImage: any [];
  public teamArrayName: any [];
  public imageIndex: number = 0;
  public primeros: AuxiliarClass[] = new Array(6)
  public segundos: AuxiliarClass[] = new Array(6)
  public terceros: AuxiliarClass[] = new Array(6)

  constructor(private route : ActivatedRoute, private tournamentSheetService: TournamentSheetService,
    private imageService: ImageService){
    this.teamArrayImage = new Array(18);
    this.teamArrayName = new Array(18);
    this._tournamentSheetData = this.tournamentSheetService._tournamentSheetAux;

    for(var j = 0; j < 6; j++){
      for(var i = 0; i<this._tournamentSheetData.groups.length; i++) {
        if (i == 0) {
          this.primeros[j] = this._tournamentSheetData.groups[j][i]
        }
        else if (i == 1) {
          this.segundos[j] = this._tournamentSheetData.groups[j][i]
        }
        else if (i == 2) {
          this.terceros[j] = this._tournamentSheetData.groups[j][i]
        }
      }
    }


    for(var j = 0; j < 6; j++){
      for(var i = 0; i < 3; i++){
        this.saveImage(this._tournamentSheetData.groups[j][i].team.teamName)
      }
    }
  }

  ngOnInit():void {
  }

  saveImage(name: string) {
    this.imageService.getTeamImage(name, 0).subscribe(
      image=>{
        this.createImageFromBlob(image, name);
      },
    );

  }

  getTournamentSheetTeams(): AuxiliarClass[][] {
    var teams: AuxiliarClass[][] = new Array(3)
    teams[0] = this.primeros
    teams[1] = this.segundos
    teams[2] = this.terceros
    return teams;

    }

    createImageFromBlob(image: Blob, name: string) {
      let reader = new FileReader();
      reader.addEventListener("load", () => {
        this.teamArrayImage[this.imageIndex] = reader.result;
        this.teamArrayName[this.imageIndex] = name;
        this.imageIndex++;
      }, false);

      if (image) {
        reader.readAsDataURL(image);
      }
    }

    getTeamImage(i: number, j: number){
      let name: string;
      if (i == 0)
        name = this.primeros[j].team.teamName;
      else if (i == 1)
        name = this.segundos[j].team.teamName;
      else
        name = this.terceros[j].team.teamName;

      let correctIndex = this.teamArrayName.findIndex((element) => element == name);
      return this.teamArrayImage[ correctIndex ];
    }

}
