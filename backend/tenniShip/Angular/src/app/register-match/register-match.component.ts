import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-register-match',
  templateUrl: './register-match.component.html',
})
export class RegisterMatchComponent implements OnInit {

  public tournament_id: string;

  constructor(private route: ActivatedRoute) {
    this.tournament_id = route.snapshot.params.tournament_id;
  }

  ngOnInit(): void {
  }

}
