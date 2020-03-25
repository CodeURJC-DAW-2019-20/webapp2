import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) { }

  login(un: string, pass: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa(un + ':' + pass)
      })
    };
    console.log('adios');
    return this.http.get('/api/tenniship/signin', httpOptions);
  }

  signin(un, pass, email, teamName: string, pn: Array<string>) {

    const data = {
      "userName": un, "passwordHash": pass, "email": email,
      "teamName": teamName, "roles": ["ROLE_USER"], "players": pn
    };
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post<any>('/api/tenniship/signup', data, config);

  }


}