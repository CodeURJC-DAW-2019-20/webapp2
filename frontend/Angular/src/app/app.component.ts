import { Component } from '@angular/core';
import { SpinerService } from './shared-services/spiner.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {

  constructor(public spinerService: SpinerService){}

}
