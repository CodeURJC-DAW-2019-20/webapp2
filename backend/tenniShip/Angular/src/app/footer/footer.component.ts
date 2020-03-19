import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  shortContent = false;



  constructor() { }

  ngOnInit(): void {
  }

  setShortContent(): void {
    this.shortContent = window.innerHeight >= document.body.offsetHeight + 92;
  }

}
