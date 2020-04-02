import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TournamentSheetModalService {

  tournamentsName: string;

  constructor() { }

  setTournamentName(name: string) {
    this.tournamentsName = name;
  }

  getTournamentName() {
   return this.tournamentsName;
  }
}
