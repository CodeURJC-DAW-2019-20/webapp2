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

  /* Messages for the template*/
  emptyMSG: string = ('This field is empty!');
  usedUsernameMSG: string = ('Username already exists');
  pssNotMatchMSG: string = ('Passwords do not match');
  pssLengthMSG: string = ('Password needs a minimum of 8 characters');
  usedEmailMSG: string = ('Email already registered');
  invalidEmailMSG: string = ('Enter a valid e-mail direction');
  usedTeamNameMSG: string = ('already exists as a team.');
  usedPlayerNameMSG: string = ('There cannot be two players with the same name');

  /* Boolean values for the database validation response (databaseValidator())*/
  usedUsername: boolean;
  usedEmail: boolean;
  usedTeamName: boolean;

  /* Other boolean values for front-end information validation*/
  pssNotMatch: boolean;              // Passwords do not match
  shortPassword: boolean;                // Password legth is below 8 characters
  invalidEmail: boolean;             // Email is empty or does not follow a correct email format
  notTeamPic: boolean;               // Team Picture is missing
  usedPlayerName: Array<boolean>;    // Repeated player name

  /* Boolean for empty fields*/
  emptyUsername: boolean;
  emptyTeamName: boolean;
  emptyPlayerName: Array<boolean>;

  /* Form fields*/
  username: string = this.userData.controls['username'].value;
  password: string = this.userData.controls['password'].value;
  email: string = this.userData.controls['email'].value;
  teamName: string = this.userData.controls['teamName'].value;
  nameplayer1: string = this.userData.controls['nameplayer1'].value;
  nameplayer2: string = this.userData.controls['nameplayer2'].value;
  nameplayer3: string = this.userData.controls['nameplayer3'].value;
  nameplayer4: string = this.userData.controls['nameplayer4'].value;
  nameplayer5: string = this.userData.controls['nameplayer5'].value;



  /*        IMPLEMENTATION AREA        */
  constructor(public loginService: LoginService, public router: Router) { }

  ngOnInit(): void {
    this.emptyPlayerName = new Array<boolean>(5);
    this.usedPlayerName = new Array<boolean>(5);
  }


  onSubmit() {
    //this.uploadImages();
    /* Update form fields variables */
    this.username = this.userData.controls['username'].value;
    this.password = this.userData.controls['password'].value;
    this.email = this.userData.controls['email'].value;
    this.teamName = this.userData.controls['teamName'].value;
    this.nameplayer1 = this.userData.controls['nameplayer1'].value;
    this.nameplayer2 = this.userData.controls['nameplayer2'].value;
    this.nameplayer3 = this.userData.controls['nameplayer3'].value;
    this.nameplayer4 = this.userData.controls['nameplayer4'].value;
    this.nameplayer5 = this.userData.controls['nameplayer5'].value;

    /* SignUp */
    this.databaseValidator().then(() => {           // Promise -> Checks and waits for database httpResponse
      console.log("Validating database data...");   // HttpResponse gotten
      if (this.validateRegisterFields()) {          // Validation of other front-end fields
        console.log("Credentials are OK");
        this.loginService.signIn(this.username, this.password, this.email, this.teamName, [this.nameplayer1, this.nameplayer2,
        this.nameplayer3, this.nameplayer4, this.nameplayer5]).subscribe(
          res => {
            console.log("Register successfull: " + res);
          },
          error => {
            console.error("Something went wrong: " + error);
          }
        );
        this.userData.reset();
      }
      else {
        console.log("Some input data are wrong");
      }
    });
  }

  validateRegisterFields() {
    return (this.usernameValidator() && this.emailValidator() && this.passwordValidator()
      && this.teamNameValidator() && this.playersNameValidator())
  }

  usernameValidator() {
    if (this.username != ('')) {    // If field is not empty
      this.emptyUsername = false;
      if (!this.usedUsername)       // If is not already registered on the database
        return true;
    } else
      this.emptyUsername = true;
    return false;
  }

  passwordValidator() {
    if (Object.keys(this.password).length > 7) {
      this.shortPassword = false;
      if (this.password == this.userData.controls['passwordCheck'].value) {
        this.pssNotMatch = false;
        return true;
      }
      this.pssNotMatch = true;
    } else
      this.shortPassword = true;
    return false;
  }

  emailValidator() {
    const RE: RegExp = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (RE.test(this.email)) {      // If email format is correct (regular expresion validation)
      this.invalidEmail = false;
      if (!this.usedEmail)          // If is not already registered on the database
        return true;
    } else
      this.invalidEmail = true;
    return false;
  }

  teamNameValidator() {
    if (this.teamName != ('')) {    // If field is not empty
      this.emptyTeamName = false;
      if (!this.usedTeamName)       // If is not already registered on the database
        return true;
    } else
      this.emptyTeamName = true;
    return false;
  }

  playersNameValidator(): boolean {
    /* Auxiliar data structure for validate there is no repeated name on it */
    let aux: Array<string> = new Array<string>(5);
    let error = false;                     // Value to be returned
    for (let i = 0; i < 5; i++) {          // Iteration on the 5 player name fields
      // var player: string = `${'this.nameplayer'}${i+1}`;
      let player: string = this.userData.controls['nameplayer' + (i + 1)].value;
      if (!aux.includes(player)) {         // If the data structure does not already contains the name => name is not repeated
        this.usedPlayerName[i] = false;
        if (player != ('')) {              // If the name is NOT empty
          aux[i] = player;                 // We add the name into de data structure
          this.emptyPlayerName[i] = false;
        } else {
          this.emptyPlayerName[i] = true;
          error = true;
        }
      } else {
        this.usedPlayerName[i] = true;
        error = true;
      }
    }
    return !error;
  }

  databaseValidator(): Promise<any> {
    return new Promise((resolve, reject) => {
      this.loginService.validateDDBB(this.username, this.teamName, this.email).subscribe(
        res => {
          this.usedUsername = res[0].valueOf();
          this.usedTeamName = res[1].valueOf();
          this.usedEmail = res[2].valueOf();
          console.log("Data base info pulled: " + res);
          resolve()
        },
        error => {
          console.error("Something went wrong: undefined: " + error);
          reject()
        }
      );
    })
  }




  // selectedFiles: Array<File>;
  // selectedFile0 = this.userData.controls['teamPic'].value;
  // selectedFile1 = this.userData.controls['picplayer1'].value;
  // selectedFile2 = this.userData.controls['picplayer2'].value;
  // selectedFile3 = this.userData.controls['picplayer3'].value;
  // selectedFile4 = this.userData.controls['picplayer4'].value;
  // selectedFile5 = this.userData.controls['picplayer5'].value;
  // imageSend: FileList;

  // currentFile: File;

  // uploadImages() {
  //   this.loginService.uploadTeamImages([, this.selectedFile0, this.selectedFile1, this.selectedFile2,
  //     this.selectedFile3, this.selectedFile4, this.selectedFile5], "pruebaTeam").subscribe(
  //       res => {
  //         console.log("wiiii: " + res)
  //       },
  //       error => {
  //         console.log("buaaaaa: " + error)
  //       }
  //     );
  // }

}
