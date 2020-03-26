import { Component, OnInit } from '@angular/core';
import {PageLengthService} from "../service/page-length.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  shortContent = false;



  constructor(private pageLength: PageLengthService) {
    this.pageLength.shortContent.subscribe(shortContent => this.shortContent = shortContent);
  }

  ngOnInit(): void {

  }

  setShortContent(): void {
    this.shortContent = window.innerHeight >= document.body.offsetHeight + 92;
  }

}
