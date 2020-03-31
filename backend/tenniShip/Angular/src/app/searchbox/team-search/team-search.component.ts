import { Component, OnInit } from '@angular/core';
import {Observable} from "rxjs";
import {debounceTime, distinctUntilChanged, map} from "rxjs/operators";
import {AutocompleteService} from "../autocomplete.service";

@Component({
  selector: 'app-team-search',
  templateUrl: './team-search.component.html',
  styleUrls: ['./team-search.component.css']
})
export class TeamSearchComponent implements OnInit {
  private teams: string[] = [];

  search = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      map(term => term.length < 2 ? []
        : this.teams.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
    );

  constructor(private autocomplete: AutocompleteService) { }

  ngOnInit(): void {
    this.autocomplete.getAllTeams().subscribe(
      data => {
        let aux = data;
        let i = 0;
        while (!(typeof aux[i] === 'undefined')) {
          this.teams.push(aux[i].teamName);
          i++;
        }
      }
    )
  }

}
