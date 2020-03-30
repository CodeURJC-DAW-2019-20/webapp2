import { Injectable } from '@angular/core';

@Injectable({providedIn: "root"})
export class ErrorService {
    
    public errorNum: number;
    public errorMsg: string;

    constructor () {}

    public createMsg () {
        if (this.errorNum === 404) {
            this.errorMsg = "The page you are looking for is not available";
        } else if ((this.errorNum === 403) || (this.errorNum === 401)) {
            this.errorMsg = "The page you are looking for needs special permissions";
        } else {
            this.errorMsg = "We regret to inform something went wrong, please try again later"
        } 
    }
}