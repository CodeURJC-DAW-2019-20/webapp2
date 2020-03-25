import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-select-tournament',
  templateUrl: './select-tournament.component.html',
  styleUrls: []
})
export class SelectTournamentComponent implements OnInit {

  public tournamentList = ["Champions League", "Davis Cup", "EuroCup"];

  constructor() { }

  ngOnInit(): void {
  }

}
