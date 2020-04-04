import { Component, OnInit } from '@angular/core';
import {UserService} from "../shared-services/user.service";
import {User} from "../model/user.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styles: ["#home { padding-top: 152px }"],
})
export class HomeComponent implements OnInit {

  admin = true;
  registered = true;
  dirImagesPage: string ="/assets/resources/static/img/";

  constructor() { }


  ngOnInit(): void { }

}
