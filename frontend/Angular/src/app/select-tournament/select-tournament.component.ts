import { Component, OnInit } from '@angular/core';
import {Tournament} from "../model/tournament";
import {PageLengthService} from "../shared-services/page-length.service";
import {SelectTournamentService} from "./select-tournament.service";
import {ImageService} from "../service/image.service";
import {SpinerService} from "../service/spiner.service";

@Component({
  selector: 'app-select-tournament',
  templateUrl: './select-tournament.component.html',
})
export class SelectTournamentComponent implements OnInit {

  private _tournamentList: Tournament[] = new Array<Tournament>();
  private tournamentImages = [];
  private currentPage = 0;
  private pageSize = 2;   // Can be changed if we want
  public morePages: boolean = true;

  constructor(private pagelength: PageLengthService, private selectTournamentService: SelectTournamentService,
              private imageService: ImageService, private spinerService: SpinerService) {}

  ngOnInit(): void {
    this.tournamentImages = new Array(this.pageSize);
    this.selectTournamentService.getPage(0,this.pageSize).subscribe(
      data =>{
        this._tournamentList = data;
        for (let i=0; i < this.pageSize; i++) {
          this.imageService.getTournamentImage(this._tournamentList[i].name).subscribe(
            image=>{
              this.createImageFromBlob(image,i);
            },
          );
        }
      }
    );
    this.currentPage++;
  }

  getTournamentList(): Tournament[] {
    return this._tournamentList;
  }

  public showMore(): void {
    this.selectTournamentService.getPage(this.currentPage,this.pageSize).subscribe(
      data => {
        let aux = data;
        let i = 0;
        while(this.morePages && i < this.pageSize) {
          if (typeof aux[i] === 'undefined') {
            this.morePages = false;
          }
          else {
            this._tournamentList.push(aux[i]);
            let realIndex = i+(this.pageSize*(this.currentPage-1));   // This might not work in all cases
            this.imageService.getTournamentImage(this._tournamentList[realIndex].name).subscribe(
              image=>{
                this.createImageFromBlob(image,realIndex);
              },
            );
            i++;
          }
        }
      }
    );
    this.currentPage++;
    this.pagelength.updatePageLength();
  }

  createImageFromBlob(image: Blob, i:number) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.tournamentImages[i] = reader.result;
    }, false);

    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getTournamentImages(i: number) {
    return this.tournamentImages[i];
  }

}
