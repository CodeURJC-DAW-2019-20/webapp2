import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { UserService } from "../service/user.service";
import { User } from "../model/user.model";
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {

  admin: boolean = true;
  currentUser: User;
  indexPage = true;   // Should change when the current page is not index
  scrolled = false;

  constructor(private router: Router, private userService: UserService, private http: HttpClient) {
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

  logout(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.userService.logout().subscribe(
        res => {
          console.log(res);

          // remove user from local storage to log user out
          localStorage.removeItem('currentUser');
          this.userService.currentUserSubject.next(null);
          
          this.router.navigate(['', 'TenniShip']);
        },
        error => {
          console.log("marcos");
        }
      )
    })
  }
  // Header scroll class
  // This makes the header smaller when the window is not at the top of the page

  setScrolled(): void {
    this.scrolled = (window.pageYOffset > 100);
  }

}


