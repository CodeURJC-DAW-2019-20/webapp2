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
  indexPage = true;   // Should change when the current page is not index
  scrolled = false;

  constructor(private router: Router, private userService:UserService) {
    router.events.subscribe(
        data => {
          this.indexPage = (router.url === "/TenniShip"); // Makes header black if the user is not on the index page
          this.setScrolled();
        }
    );
    this.userService.currentUser.subscribe(x => this.currentUser = x);;
  }


  ngOnInit(): void {

  }

  logout() {
    this.userService.logout();
    this.router.navigate(['','TenniShip']);
  }

  // Header scroll class
  // This makes the header smaller when the window is not at the top of the page

  setScrolled(): void {
    this.scrolled = (window.pageYOffset > 100);
  }

}


