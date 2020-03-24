import {Tournament} from "./tournament";
import {Player} from "./player";

export interface TeamFileData {
  teamName:string;
  teamImage:boolean;
  playerList:Player[];
  tournamentList : Tournament [];
  percentageLostMatches: number;
  percentageWonMatches: number;
}
