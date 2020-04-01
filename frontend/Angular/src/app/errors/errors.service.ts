import { Injectable } from '@angular/core';

@Injectable({providedIn: "root"})
export class ErrorService {
    
    public errorNum: number;
    public mainErrorName: string = "page";

    constructor () {}

    public createMsg () {
        if (this.errorNum === 404) {
            this.mainErrorName = "The "+ this.mainErrorName + " you are looking for is not available";
        } else if ((this.errorNum === 403) || (this.errorNum === 401)) {
            this.mainErrorName = "The "+ this.mainErrorName + " you are looking for needs special permissions";
        } else {
            this.mainErrorName = "We regret to inform something went wrong, please try again later"
        } 
    }

    public setMsg (guilty: string) {
        this.mainErrorName= guilty;
    }
}