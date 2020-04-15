import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {TeamService} from "../team.service";
import {TeamFileData} from "../../model/team-file-data";

@Component({
  selector: 'app-graphic-pie',
  templateUrl: './graphic-pie.component.html'
})
export class GraphicPieComponent implements OnChanges{

  @Input()
  public teamPieInfo: TeamFileData;

  ngOnChanges(changes: SimpleChanges) {
    this.teamPieInfo = changes.teamPieInfo.currentValue;
    this.single = [
      {
        "name": "MatchesWon",
        "value": changes.teamPieInfo.currentValue.percentageWonMatches
      },
      {
        "name": "MatchesLost",
        "value": changes.teamPieInfo.currentValue.percentageLostMatches
      }
    ];
  }

  single: any [] ;
  view: any[] = [250,250];
  colorScheme = {
    domain: ['#51e28f', '#dc5186']
  };

  constructor() { }

}
