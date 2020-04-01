import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "../service/user.service";
import {User} from "../model/user.model";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {

  admin:boolean = true;
  currentUser:User;

  constructor(private router :Router, private userService:UserService) {
    this.userService.currentUser.subscribe(x => this.currentUser = x);
  }

  ngOnInit(): void {

  }

  logout() {
    this.userService.logout();
    this.router.navigate(['','TenniShip']);
  }

}
