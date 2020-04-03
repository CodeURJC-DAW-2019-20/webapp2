import { Component } from '@angular/core';
import { SpinerService } from './service/spiner.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {

  constructor(public spinerService: SpinerService){}

}
