import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: []
})
export class HomeComponent implements OnInit {

  admin = true;
  registered = true;

  constructor() { }

  ngOnInit(): void {
  }

}