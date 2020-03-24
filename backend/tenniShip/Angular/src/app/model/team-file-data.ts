import {Team} from "./team";
import {Tournament} from "./tournament";
import {Match} from "./match";
import {Player} from "./player";

export interface TeamFileData {
  teamName:string;
  teamImage:boolean;
  playerList:Player[];
  tournamentList : Tournament [];
  percentageLostMatches: number;
  percentageWonMatches: number;
  matchesList : Match [];
}
