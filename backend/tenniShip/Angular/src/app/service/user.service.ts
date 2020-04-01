import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {User} from "../model/user.model";
import {map} from "rxjs/operators";
import {BehaviorSubject, Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public selectFiles:FileList[] = new Array(6);

  redirectToHome:string = "/TenniShip/SignIn";
  registerSucceeded:boolean = false;
  currentUser: Observable<User>;
  currentUserSubject:BehaviorSubject<User>;

  login(un: string, pass: string) {
    var httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa(un + ':' + pass)
      })
    };
    return this.http.get<any>('/api/tenniship/signin', httpOptions).
    pipe(
      map(user=>{
        console.log(user);
        user.authData = window.btoa(un + ':' + pass);
        localStorage.setItem('currentUser',JSON.stringify(user));
        this.currentUserSubject.next(user);
        console.log(user);
      })
    )
  }

  signUp(un:string, pass:string, email:string, teamName: string, pn: Array<string>) {

    const data = {
      "userName": un, "passwordHash": pass, "email": email,
      "teamName": teamName, "roles": ["ROLE_USER"], "players": pn
    };
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post<any>('/api/tenniship/signup', data, config);
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  validateDDBB(userName:string, teamName:string, email: string) {
    let requestUrl: string = '/api/tenniship/validator/user?userName=' + userName + '&teamName=' + teamName + '&email=' + email;
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.get<Array<boolean>>(requestUrl, config);
  }

  uploadTeamImages(files: Array<File>, team: string) {
    console.log("Files to be uploaded: " + files);
    const formData: FormData = new FormData();
    formData.append('imageFile', files[0]);
    formData.append('imageFile',files[1]);
    formData.append('imageFile', files[2]);
    formData.append('imageFile',files[3]);
    formData.append('imageFile', files[4]);
    formData.append('imageFile',files[5]);

    return this.http.post('/api/tenniship/teams/' + team + '/image', formData);
  }

}
