import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-creator',
  templateUrl: './creator.component.html',
  styleUrls: []
})
export class CreatorComponent implements OnInit {

  public isMenuCollapsed = true;
  public active = "tournament";

  constructor() { }

  ngOnInit(): void {
  }

}
