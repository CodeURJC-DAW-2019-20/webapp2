import { Component, OnInit, ɵisBoundToModule__POST_R3__, Output, EventEmitter } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { UserService } from 'src/app/service/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ImageService } from 'src/app/service/image.service';


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

  constructor(public userService: UserService, public router: Router, public route: ActivatedRoute,private imageService:ImageService) {
    if (this.userService.currentUserValue) {
      this.router.navigate(['', 'TenniShip']);
    }
  }

  ngOnInit() {
    this.invalidCredentials = false;
    this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/TenniShip';
  }

  login(username: string, password: string, event: Event) {
    if (this.credentials.controls['username'].value != ('')) {
      if (this.credentials.controls['password'].value != ('')) {
        this.userService.login(username, password).subscribe(
          res => {
            this.userService.loged = true; 
            if (!this.userService.getIsAdmin()) {
              this.imageService.getTeamImage(this.userService.currentUserValue.team,0).subscribe(
                image => {
                  this.userService.createImageFromBlob(image)
                }
              );
            }
            this.navigate()
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
  }

  navigate() {
    this.router.navigate([this.returnUrl]);
  }
}
