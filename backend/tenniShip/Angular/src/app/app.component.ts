import { Component } from '@angular/core';
import {User} from "./model/user.model";
import {NgwWowService} from "ngx-wow";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {
  title = 'Angular';
  admin = true;
  currentUser:User;


}
