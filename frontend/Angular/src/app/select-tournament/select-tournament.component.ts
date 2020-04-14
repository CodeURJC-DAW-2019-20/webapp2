import { Component, OnInit } from '@angular/core';
import {Tournament} from "../model/tournament";
import {PageLengthService} from "../shared-services/page-length.service";
import {SelectTournamentService} from "./select-tournament.service";
import {ImageService} from "../shared-services/image.service"
import {SpinnerService} from "../shared-services/spinner.service";
import {UserService} from "../shared-services/user.service";
import {Router} from "@angular/router";

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
  public admin: boolean = false;

  constructor(private pagelength: PageLengthService, private selectTournamentService: SelectTournamentService,
              private imageService: ImageService, private spinnerService: SpinnerService,
              private userService: UserService, private router: Router, private pageLengthService: PageLengthService) {}

  ngOnInit(): void {
    this.tournamentImages = new Array(this.pageSize);
    this.admin = this.userService.getIsAdmin();
    if(this.userService.getIsAdmin()) {
      this.admin = true;
    } else {    
      this.spinnerService.changeLoading(true);
      this.selectTournamentService.getPage(0,this.pageSize).subscribe(
        data =>{
          this._tournamentList = data;
          if (typeof data === 'undefined') {
            this.router.navigate(['TenniShip', 'Error']);
          }
          else {
            for (let i = 0; i < this.pageSize; i++) {
              this.imageService.getTournamentImage(this._tournamentList[i].name).subscribe(
                image => {
                  this.createImageFromBlob(image, i);
                  if (i === this.pageSize - 1) {
                    this.pageLengthService.updatePageLength();
                    this.spinnerService.changeLoading(false);
                  }
                },
              );
            }
          }
        }
      );
      this.currentPage++;
    }
  }

  getTournamentList(): Tournament[] {
    return this._tournamentList;
  }

  public showMore(): void {
    this.spinnerService.changeLoading(true);
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
    this.spinnerService.changeLoading(false);
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
