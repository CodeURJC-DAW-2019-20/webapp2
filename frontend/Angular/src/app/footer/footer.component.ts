import { Component, OnInit } from '@angular/core';
import {PageLengthService} from "../shared-services/page-length.service";
import {NavigationEnd, Router} from "@angular/router";
import {tick} from "@angular/core/testing";
import {filter} from "rxjs/operators";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  shortContent = false;

  constructor(private pageLength: PageLengthService, private router: Router) {
    this.pageLength.shortContent.subscribe(shortContent => {this.shortContent = shortContent});
    router.events.subscribe(
      data => {
        this.shortContent = true;
      }
    );
  }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => this.setShortContent());
  }

  setShortContent(): void {
    this.shortContent = window.innerHeight >= document.body.offsetHeight;
  }

}
