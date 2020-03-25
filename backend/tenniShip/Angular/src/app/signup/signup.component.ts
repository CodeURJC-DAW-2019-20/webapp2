import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { LoginService } from 'src/app/service/login.service';
import{Router} from '@angular/router';


@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  userData = new FormGroup({
    username: new FormControl(''),
    email: new FormControl(''),
    password: new FormControl(''),
    passwordCheck: new FormControl(''),
    teamName: new FormControl(''),
    teamPic: new FormControl(''),
    nameplayer1: new FormControl(''),
    nameplayer2: new FormControl(''),
    nameplayer3: new FormControl(''),
    nameplayer4: new FormControl(''),
    nameplayer5: new FormControl(''),
    picplayer1: new FormControl(''),
    picplayer2: new FormControl(''),
    picplayer3: new FormControl(''),
    picplayer4: new FormControl(''),
    picplayer5: new FormControl('')
  });

  constructor(public loginService: LoginService, public router:Router) {}

  ngOnInit(): void {
  }

  onSubmit() {
    let password:string=this.userData.controls['password'].value;
    if(password == this.userData.controls['passwordCheck'].value){
      let username:string=this.userData.controls['username'].value;
      let email:string=this.userData.controls['email'].value;
      let teamName:string=this.userData.controls['teamName'].value;
      let nameplayer1:string=this.userData.controls['nameplayer1'].value;
      let nameplayer2:string=this.userData.controls['nameplayer2'].value;
      let nameplayer3:string=this.userData.controls['nameplayer3'].value;
      let nameplayer4:string=this.userData.controls['nameplayer4'].value;
      let nameplayer5:string=this.userData.controls['nameplayer5'].value;
      console.log("passwords match");
      this.loginService.signIn(username,password,email,teamName,[nameplayer1,nameplayer2,nameplayer3,nameplayer4,nameplayer5]).subscribe(
        res =>{
          console.log(res);
        },
        error => {
          console.error(error);
          // this.message="Wrong data. Please, try again."
        }

      );
    }
    else{
      console.log("passwords DO NOT match");
    }

  }
}
