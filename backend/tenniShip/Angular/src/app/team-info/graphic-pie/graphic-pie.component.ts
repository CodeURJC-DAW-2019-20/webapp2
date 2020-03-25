import {Component, Input} from '@angular/core';

import {TeamService} from "../team.service";
import {TeamFileData} from "../../model/team-file-data";

@Component({
  selector: 'app-graphic-pie',
  templateUrl: './graphic-pie.component.html',
  styleUrls: ['./graphic-pie.component.css']
})
export class GraphicPieComponent {
  @Input()
  teamFileData : TeamFileData;

  single: any [];
  view: any[] = [0,400];
  won : string="WonRate";
  lost : string="LostRate";

  // options
  gradient: boolean = true;
  showLegend: boolean = true;
  showLabels: boolean = true;
  isDoughnut: boolean = false;
  legendPosition: string = 'right';

  colorScheme = {
    domain: ['#5AA454', '#A10A28']
  };

  constructor(public teamService: TeamService) {
    const data = [
    {"name":this.won, "value": this.teamFileData.percentageWonMatches },
    {"name":this.lost,"value": this.teamFileData.percentageLostMatches }
    ];
    Object.assign(this.single, {data} );
  }

  onSelect(data): void {
    console.log('Item clicked', JSON.parse(JSON.stringify(data)));
  }

  onActivate(data): void {
    console.log('Activate', JSON.parse(JSON.stringify(data)));
  }

  onDeactivate(data): void {
    console.log('Deactivate', JSON.parse(JSON.stringify(data)));
  }

}
