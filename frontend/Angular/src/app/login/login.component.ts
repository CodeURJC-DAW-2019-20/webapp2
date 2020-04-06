import { Component, OnInit } from '@angular/core';
import {PageLengthService} from "../shared-services/page-length.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private pageLengthService: PageLengthService) { }

  ngOnInit(): void {
    this.pageLengthService.setshortContent(true);
  }

}
