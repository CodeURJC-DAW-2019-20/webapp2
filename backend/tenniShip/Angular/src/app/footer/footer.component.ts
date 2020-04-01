import { Component, OnInit } from '@angular/core';
import {PageLengthService} from "../service/page-length.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  shortContent = false;

  constructor(private pageLength: PageLengthService, private router: Router) {
    this.pageLength.shortContent.subscribe(shortContent => this.shortContent = shortContent);
    router.events.subscribe(
      data => {
        this.setShortContent(); // Refresh short content if the user changes the page
      }
    )
  }

  ngOnInit(): void {

  }

  setShortContent(): void {
    this.shortContent = window.innerHeight >= document.body.offsetHeight + 92;
  }

}
