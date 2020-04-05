import { Component, OnInit } from '@angular/core';
import { ErrorService } from './errors.service';
import {SpinnerService} from "../shared-services/spinner.service";

@Component({
	selector: 'app-errors',
	templateUrl: './errors.component.html',
	styleUrls: []
})
export class ErrorsComponent implements OnInit {

	public errormsg: string;
	constructor(private errorService: ErrorService, private spinner: SpinnerService) { }

	ngOnInit(): void {
		 this.errorService.createMsg();
		 this.errormsg = this.errorService.errorInformation;
		 this.spinner.changeLoading(false);
	}

}
