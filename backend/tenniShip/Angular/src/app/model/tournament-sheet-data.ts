import {Tournament} from "./tournament";
import {Match} from "./match";
import {AuxiliarClass} from "./tournament-sheet-auxdata";

export interface TournamentSheetData {
  tournament: Tournament;
  groups: AuxiliarClass [];
  quarters: Match[];
  theSemiFinals: Match[];
  theFinal: Match[];
  completion: number;
}
