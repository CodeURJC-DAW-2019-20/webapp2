import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms'

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

  constructor() { }

  ngOnInit(): void {
  }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    console.warn(this.userData.value);
  }
}
