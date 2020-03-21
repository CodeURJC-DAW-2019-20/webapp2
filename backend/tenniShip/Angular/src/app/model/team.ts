import {Player} from "./player";
import {Match} from "./match";
import {Tournament} from "./tournament";

export  interface Team {
    teamName: string;
    Tournaments: Tournament [];
    playerList: Player [] ;
    percentageLostMatches: number;
    percentageWonMatches: number;
    matchesList: Match [];
}

