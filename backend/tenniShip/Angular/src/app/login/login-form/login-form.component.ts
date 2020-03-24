import { Component, OnInit, ɵisBoundToModule__POST_R3__ } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { LoginService } from 'src/app/service/login.service';
import{Router} from '@angular/router';
import { Message } from '@angular/compiler/src/i18n/i18n_ast';

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
  message=('');

  constructor(public userService: LoginService, public router:Router) {}

  ngOnInit() {
  }

  login(username: string, password: string, event: Event) {
    this.userService.login(username,password).subscribe(
      res =>{
        console.log(res);
      },
      error => {
        console.error(error);
        this.message="Wrong data. Please, try again."
      }
      
    );
  }

  navigate(){
   
  }
}