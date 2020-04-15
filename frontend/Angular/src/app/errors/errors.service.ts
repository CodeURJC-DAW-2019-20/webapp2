import { Injectable } from '@angular/core';

@Injectable({providedIn: "root"})
export class ErrorService {
    
    public errorNum: number;
    public apiError: boolean = false;
    public errorResponsible: string = "page";
    public errorInformation: string = "";

    constructor () {}

    public createMsg () {
        if (this.errorNum === 404 || !this.apiError) {
            this.errorInformation = "The "+ this.errorResponsible + " you are looking for is not available";
        } else if ((this.errorNum === 403) || (this.errorNum === 401)) {
            this.errorInformation = "The "+ this.errorResponsible + " you are looking for needs special permissions";
        } else {
            this.errorInformation = "We regret to inform something went wrong, please try again later"
        }
        this.errorResponsible = "page"; 
        this.apiError = false;
    }

    public setMsg (guilty: string) {
        this.errorResponsible= guilty;
    }

    public setapiError () {
        this.apiError = true;
    }
}