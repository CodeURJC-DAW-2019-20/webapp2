import {Team} from "./team";
import {Tournament} from "./tournament";

export interface Match {
  homePoints: number;
  awayPoints:number;
  team1: Team;
  team2: Team;
  tournament: Tournament;

}
