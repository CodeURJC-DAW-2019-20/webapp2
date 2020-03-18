import {Component, HostListener, OnInit} from '@angular/core';

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


  constructor() { }

  ngOnInit(): void {
  }

  // Header scroll class
  // This makes the header smaller when the window is not at the top of the page

  setScrolled(): void {
    this.scrolled = (window.pageYOffset > 100);
  }

}


