import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})

@Injectable()
export class UserService {
  constructor(private http: HttpClient) { }

  login(un:string, pass:string){
    const httpOptions ={
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Basic ' + btoa(un+':'+pass)
      })
      //'Authorization':'Basic' +btoa(`${un}:${pass}`)
    };
    console.log('adios'); 
    return this.http.get('/api/tenniship/signin',httpOptions);
  }


}