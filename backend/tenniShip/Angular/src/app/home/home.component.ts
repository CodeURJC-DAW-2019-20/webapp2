import { Component, OnInit } from '@angular/core';
import {UserService} from "../service/user.service";
import {User} from "../model/user.model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: []
})
export class HomeComponent implements OnInit {

  admin = true;
  registered = true;
  dirImagesPage: string ="/assets/resources/static/img/";

  constructor() { }


  ngOnInit(): void { }

}
