import { Injectable } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class TournamentSheetModalService {

  tournamentsName: string;

  constructor(private route :ActivatedRoute){
    this.route.params.subscribe(
      params=>{ this.tournamentsName = params.tournament_id,
                    console.log(params.tournament_id)}
    )
  }

  setTournamentName(name: string) {
    this.tournamentsName = name;
  }

  getTournamentName() {
   return this.tournamentsName;
  }
}
