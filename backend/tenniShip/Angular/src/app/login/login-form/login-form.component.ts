import { Component, OnInit, ɵisBoundToModule__POST_R3__ } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { UserService } from 'src/app/service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {

  credentials = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  message = ('');
  emptyMSG: string = ('This field is empty!');
  emptyUsername: boolean;
  emptyPassword: boolean;
  invalidCredentials: boolean;

  constructor(public userService: UserService, public router: Router) { }

  ngOnInit() {
    console.log(this.userService.registerSucceded);
    this.invalidCredentials = false;
  }

  login(username: string, password: string, event: Event) {
    if (this.credentials.controls['username'].value != ('')) {
      if (this.credentials.controls['password'].value != ('')) {
        this.userService.login(username, password).subscribe(
          res => {
            console.log(res);
          },
          error => {
            this.invalidCredentials = true;
            this.message = "Wrong data."
            console.error(error);
          }

        );
      }
      else
        this.emptyPassword = true;
    }
    else
      this.emptyUsername = true;
  }

  navigate() {

  }
}