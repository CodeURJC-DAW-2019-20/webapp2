import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

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
    return this.http.get('/api/tenniship/signin', httpOptions);
  }

  signIn(un:string, pass:string, email:string, teamName: string, pn: Array<string>) {

    const data = {
      "userName": un, "passwordHash": pass, "email": email,
      "teamName": teamName, "roles": ["ROLE_USER"], "players": pn
    };
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.post<any>('/api/tenniship/signup', data, config);
  }

  validateDDBB(userName:string, teamName:string, email: string) {
    let requestUrl: string = '/api/tenniship/validator/user?userName=' + userName + '&teamName=' + teamName + '&email=' + email;
    const config = { headers: new HttpHeaders().set('Content-Type', 'application/json') };
    return this.http.get<Array<boolean>>(requestUrl, config);
  }

  // uploadTeamImages(files: Array<File>, team: string) {
  //   // const formData: FormData = new FormData();
  //   // formData.append('imageFile', JSON.stringify(files));
  //   return this.http.post('/api/tenniship/teams/' + team + '/image', {'imageFile': files});
  //   // return this.http.post('/api/tenniship/teams/' + team + '/image',formData);
  // }


}