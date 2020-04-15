import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {debounceTime, distinctUntilChanged, map} from "rxjs/operators";
import {AutocompleteService} from "../autocomplete.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-tournament-search',
  templateUrl: './tournament-search.component.html',
  styleUrls: ['./tournament-search.component.css']
})
export class TournamentSearchComponent implements OnInit {
  model: any;
  private tournaments: string[] = [];

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.tournaments.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
    );

  constructor(private autocomplete: AutocompleteService, private router: Router) { }

  ngOnInit(): void {
    this.autocomplete.getAllTournaments().subscribe(
      data => {
        let aux = data;
        let i = 0;
        while (!(typeof aux[i] === 'undefined')) {
          this.tournaments.push(aux[i].name);
          i++;
        }
      }
    )
  }

  go() {
    this.router.navigate(['','TenniShip','Tournament',this.model]);
  }

}

