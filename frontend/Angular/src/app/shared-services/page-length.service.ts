import { Injectable } from '@angular/core';
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PageLengthService {

  private lengthChangeSource = new BehaviorSubject(window.innerHeight >= document.body.offsetHeight);
  shortContent = this.lengthChangeSource.asObservable();

  constructor() { }

  updatePageLength() {
    this.lengthChangeSource.next(window.innerHeight >= document.body.offsetHeight);
  }

  setshortContent(value: boolean) {
    this.lengthChangeSource.next(value);
  }
}
