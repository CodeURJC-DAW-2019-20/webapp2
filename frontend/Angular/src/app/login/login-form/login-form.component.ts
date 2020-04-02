import { Component, OnInit, ɵisBoundToModule__POST_R3__ } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { UserService } from 'src/app/service/user.service';
import { ActivatedRoute, Router } from '@angular/router';


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
  returnUrl: string;

  constructor(public userService: UserService, public router: Router, public route: ActivatedRoute) {
    if (this.userService.currentUserValue) {
      this.router.navigate(['', 'TenniShip']);
    }
  }

  ngOnInit() {
    console.log(this.userService.registerSucceeded);
    this.invalidCredentials = false;

    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/TenniShip';
  }

  login(username: string, password: string, event: Event) {
    if (this.credentials.controls['username'].value != ('')) {
      if (this.credentials.controls['password'].value != ('')) {
        this.userService.login(username, password).subscribe(
          res => {
            console.log(res);
            this.navigate();
          },
          error => {
            this.invalidCredentials = true;
            this.message = "Wrong data.";
            console.error(error);
          }
        );
      }
      else
        this.emptyPassword = true;
    }
    else
      this.emptyUsername = true;
    if (this.userService.registerSucceeded == true)
      this.userService.registerSucceeded = false;
  }

  navigate() {
    this.router.navigate([this.returnUrl]);
  }
}
