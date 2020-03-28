import { Component, OnInit, ɵisBoundToModule__POST_R3__ } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { UserService } from 'src/app/service/user.service';
import{Router} from '@angular/router';

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
  registeredSuccesfully:boolean;

  constructor(public userService: UserService, public router:Router) {}

  ngOnInit() {
    // this.registeredSuccesfully = this.userService.registerSucceded;
    // this.userService.succesfullBoolean();
    // console.log(this.userService.registerSucceded);
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