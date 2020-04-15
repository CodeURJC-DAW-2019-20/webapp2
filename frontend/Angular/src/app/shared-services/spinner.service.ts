import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class SpinnerService {
	public loading: boolean = false;

	public changeLoading(b: boolean){
		this.loading = b;
	}
}
