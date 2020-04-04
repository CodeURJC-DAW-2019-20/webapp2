import { Component } from '@angular/core';
import { SpinnerService } from './shared-services/spinner.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {

  constructor(public spinerService: SpinnerService){}

}
