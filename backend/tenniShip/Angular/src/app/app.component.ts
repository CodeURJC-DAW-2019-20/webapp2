import { Component } from '@angular/core';
import { HeaderComponent } from "./header/header.component";
import {NgwWowService} from "ngx-wow";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {
  title = 'Angular';
  admin = true;
  indexPage = true;   // Should change when it's not on the index page

  constructor(private wowService: NgwWowService){
      this.wowService.init();
  }

}
