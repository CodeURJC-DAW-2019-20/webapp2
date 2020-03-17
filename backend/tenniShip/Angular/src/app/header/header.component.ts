import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {

  admin = true;
  registered = true;

  constructor() { }

  ngOnInit(): void {
  }

}
