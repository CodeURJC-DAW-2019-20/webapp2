import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tournament-sheet',
  templateUrl: './tournament-sheet.component.html',
  styleUrls: []
})
export class TournamentSheetComponent implements OnInit {

  public isMenuCollapsed = true;
  public active = "groups";

  constructor() { }

  ngOnInit(): void {
  }

}
