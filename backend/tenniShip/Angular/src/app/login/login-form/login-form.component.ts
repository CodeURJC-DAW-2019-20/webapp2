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

  constructor(public userService: UserService, public router:Router) {}

  ngOnInit() {

    // this.userService.login('Marcos', '12345678')
    //   .subscribe(
    //     res => {
    //       console.log(res);
    //     });

  }

  login(username: string, password: string, event: Event) {
    this.userService.login(username,password).subscribe(
      res =>{
        console.log(res);
      },
      error => {
        console.error(error);
      }
      
    );
  }

  navigate(){
   
  }
}