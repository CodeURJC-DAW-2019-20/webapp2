import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'
import { LoginService } from 'src/app/service/login.service';
import { Router } from '@angular/router';


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

  //all could be empty
  emptyMSG: string = ('This field is empty!');

  usedUsernameMSG: string = ('Username already exists');        //username message: used username
  pssNotMatchMSG: string = ('Passwords do not match');       //password message: match
  pssLengthMSG: string = ('Password needs a minimum of 8 characters');       //password message: length
  usedEmailMSG: string = ('Email already registered');       //email message: used
  invalidEmailMSG: string = ('Enter a valid e-mail direction');
  usedTeamNameMSG: string = ('already exists as a team.');       //teamName message: used
  usedPlayerNameMSG: string = ('There cannot be two players with the same name');  //More than one team player with the same name

  usedUsername: boolean;
  pssNotMatch: boolean;    // Passwords do not match
  pssLength: boolean;
  usedEmail: boolean;
  invalidEmail: boolean;
  usedTeamName: boolean;
  notTeamPic: boolean;     //Team Picture is missing
  usedPlayerName: Array<boolean>;       //Repeated player name

  emptyUsername: boolean;
  shortPassword: boolean;
  //emptyEmail: boolean;
  emptyTeamName: boolean;
  emptyPlayerName: Array<boolean>;
  // anyEmptyPlayer:boolean;

  constructor(public loginService: LoginService, public router: Router) { }

  ngOnInit(): void {
    this.emptyPlayerName = new Array<boolean>(5);
    this.usedPlayerName = new Array<boolean>(5);
  }

  onSubmit() {
    if (this.validateRegisterFields()) {
      let username: string = this.userData.controls['username'].value;
      let password: string = this.userData.controls['password'].value;
      let email: string = this.userData.controls['email'].value;
      let teamName: string = this.userData.controls['teamName'].value;
      let nameplayer1: string = this.userData.controls['nameplayer1'].value;
      let nameplayer2: string = this.userData.controls['nameplayer2'].value;
      let nameplayer3: string = this.userData.controls['nameplayer3'].value;
      let nameplayer4: string = this.userData.controls['nameplayer4'].value;
      let nameplayer5: string = this.userData.controls['nameplayer5'].value;

      console.log("Credentials are OK");
      this.loginService.signIn(username, password, email, teamName, [nameplayer1, nameplayer2, nameplayer3, nameplayer4, nameplayer5]).subscribe(
        res => {
          console.log(res);
        },
        error => {
          console.error(error);
        }
      );
      this.userData.reset();
    }
    else {
      console.log("Some input data is wrong");
    }
  }

  validateRegisterFields() {
    return (this.usernameValidator() && this.emailValidator() && this.passwordValidator()
      && this.teamNameValidator() && this.playersNameValidator())
  }

  usernameValidator() {
    if (this.userData.controls['username'].value != ('')) {
      this.emptyUsername = false;
      return true;
    }
    this.emptyUsername = true;
    return false;
  }

  passwordValidator() {
    if (Object.keys(this.userData.controls['password'].value).length > 7) {
      this.pssLength = false;
      if (this.userData.controls['password'].value == this.userData.controls['passwordCheck'].value) {
        this.pssNotMatch = false;
        return true;
      }
      this.pssNotMatch = true;
    } else
      this.pssLength = true;
    return false;
  }

  emailValidator() {
    const RE: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (RE.test(this.userData.controls['email'].value)) {
      this.invalidEmail = false;
      return true;
    }
    this.invalidEmail = true;
    return false;
  }

  teamNameValidator() {
    if (this.userData.controls['teamName'].value != ('')) {
      this.emptyTeamName = false;
      return true;
    }
    this.emptyTeamName = true;
    return false;
  }

  playersNameValidator() {
    let aux: Array<string> = new Array<string>(5);
    let error = false;
    for (let i = 0; i < 5; i++) {
      let player: string = this.userData.controls['nameplayer' + (i + 1)].value;
      if (!aux.includes(player)) {  //if the name is not repeated
        this.usedPlayerName[i] = false;
        if (player != ('')) { //if the name is NOT empty
          aux[i] = player;
          this.emptyPlayerName[i] = false;
        } else {
          this.emptyPlayerName[i] = true;
          error = true;
          // this.anyEmptyPlayer=true;
        }
      } else {  //name is repeated
        this.usedPlayerName[i] = true;
        error = true;
      }
    }
    return !error;
  }
}
