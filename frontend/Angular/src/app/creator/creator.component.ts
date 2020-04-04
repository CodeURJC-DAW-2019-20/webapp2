import { Component, OnInit } from '@angular/core';
import { CreatorService } from './creator.service';
import {UserService} from "../shared-services/user.service";
import {User} from "../model/user.model";

@Component({
  selector: 'app-creator',
  templateUrl: './creator.component.html',
  styleUrls: ['./creator.component.css'],
  providers: [CreatorService]
})
export class CreatorComponent implements OnInit {

  public isMenuCollapsed = true;

  public active = this.creatorService.active;

  constructor(private creatorService: CreatorService) { }

  ngOnInit(): void {
  }

  change(navTag: string) {
    this.active = navTag;
  }

}
