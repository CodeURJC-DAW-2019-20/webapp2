import {Component} from '@angular/core';
import {TeamService} from "../team.service";

@Component({
  selector: 'app-graphic-pie',
  templateUrl: './graphic-pie.component.html',
  styleUrls: []
})
export class GraphicPieComponent {

  single: any [] = [
    {
      "name": "MatchesWon",
      "value": this.teamService.getWinPercentage()
    },
    {
      "name": "MatchesLost",
      "value": 100 - this.teamService.getWinPercentage()
    }
 ];
  view: any[] = [250,250];
  colorScheme = {
    domain: ['#51e28f', '#dc5186']
  };

  constructor(private teamService: TeamService) {
    Object.assign(this, this.single);
  }
}
