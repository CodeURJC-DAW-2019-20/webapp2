import { Component, OnInit } from '@angular/core';
import { ErrorService } from './errors.service';
import {SpinnerService} from "../shared-services/spinner.service";
import {PageLengthService} from "../shared-services/page-length.service";

@Component({
	selector: 'app-errors',
	templateUrl: './errors.component.html',
	styleUrls: []
})
export class ErrorsComponent implements OnInit {

	public errormsg: string;
	constructor(private errorService: ErrorService, private spinner: SpinnerService, private pageLengthService: PageLengthService) { }

	ngOnInit(): void {
		 this.errorService.createMsg();
		 this.errormsg = this.errorService.errorInformation;
		 this.pageLengthService.updatePageLength();
		 this.spinner.changeLoading(false);
	}

}
