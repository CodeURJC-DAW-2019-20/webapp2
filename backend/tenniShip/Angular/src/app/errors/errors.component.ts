import { Component, OnInit } from '@angular/core';
import { ErrorService } from './errors.service';

@Component({
	selector: 'app-errors',
	templateUrl: './errors.component.html',
	styleUrls: []
})
export class ErrorsComponent implements OnInit {

	public errormsg: string;
	constructor(private errorService: ErrorService) { }

	ngOnInit(): void {
		 this.errorService.createMsg();
		 this.errormsg = this.errorService.mainErrorName;
	}

}
