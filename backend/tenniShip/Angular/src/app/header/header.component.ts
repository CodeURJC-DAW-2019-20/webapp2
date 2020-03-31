import {Component, HostListener, OnInit} from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {

  admin = false;
  registered = true;
  indexPage = true;   // Should change when the current page is not index
  scrolled = false;


  constructor(private router: Router) {
    router.events.subscribe(
        data => {
          this.indexPage = (router.url === "/TenniShip"); // Makes header black if the user is not on the index page
          this.setScrolled();
        }
    );
  }


  ngOnInit(): void {
  }

  // Header scroll class
  // This makes the header smaller when the window is not at the top of the page

  setScrolled(): void {
    this.scrolled = (window.pageYOffset > 100);
  }

}


