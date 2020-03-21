import {Player} from "./player";
import {Match} from "./match";
import {Tournament} from "./tournament";

export  interface Team {
    teamName: string;
    playerList: Player [] ;
}

