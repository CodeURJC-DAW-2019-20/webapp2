import { Component, OnInit } from '@angular/core';
import {Tournament} from "../model/tournament";
import {PageLengthService} from "../service/page-length.service";
import {SelectTournamentService} from "./select-tournament.service";

@Component({
  selector: 'app-select-tournament',
  templateUrl: './select-tournament.component.html',
})
export class SelectTournamentComponent implements OnInit {

  private _tournamentList: Tournament[] = new Array<Tournament>();
  private currentPage = 1;
  private pageSize = 2;   // Can be changed if we want
  public morePages: boolean = true;

  constructor(private pagelength: PageLengthService, private selectTournamentService: SelectTournamentService) {}

  ngOnInit(): void {
    this.selectTournamentService.getPage(1,this.pageSize).subscribe(
      data =>{
        this._tournamentList = data;
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
            i++;
          }
        }
      }
    );
    this.currentPage++;
    this.pagelength.updatePageLength();
  }


}
