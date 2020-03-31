import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }

  public selectFiles:FileList[] = new Array(6);

  redirectToHome:string = "/TenniShip/SignIn";
  registerSucceded:boolean = false;

  login(un: string, pass: string) {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Authorization': 'Basic ' + btoa(un + ':' + pass)
      })
    };
    return this.http.get('/api/tenniship/signin', httpOptions);
  }

  signUp(un:string, pass:string, email:string, teamName: string, pn: Array<string>) {

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

  succesfullBoolean(){
    this.registerSucceded = !this.registerSucceded;
  }

}