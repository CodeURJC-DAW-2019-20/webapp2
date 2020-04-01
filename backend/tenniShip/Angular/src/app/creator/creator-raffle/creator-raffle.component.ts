import { Component, OnInit } from '@angular/core';
import { CreatorService } from '../creator.service';
import { newTournament } from '../../model/newtournament';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import {User} from "../../model/user.model";
import {UserService} from "../../service/user.service";

@Component({
	selector: 'app-creator-raffle',
	templateUrl: './creator-raffle.component.html',
	styleUrls: []
})
export class CreatorRaffleComponent implements OnInit {

	public raffled: boolean = false;
	public currentFile: File;

	constructor(private creatorService: CreatorService) {

  }

	ngOnInit(): void {
	}

	public getTeams() {
		return this.creatorService.getFinalTeams();
	}

	public getTournament() {
		return this.creatorService.getFinalTournament();
	}

	public upload() {
		this.currentFile = this.creatorService.selectedFiles.item(0);
		this.creatorService.upload(this.currentFile).subscribe(
			_=>_ ,
		error => this.handleError(error)
		);
	}

	public raffle() {
		let tournamentName: string = this.creatorService.getFinalTournament();
		let teams: string [] = this.creatorService.getFinalTeams();
		let newTour: newTournament = { tournamentName, teams };
		this.creatorService.newTournament(newTour).subscribe(
			_ => { this.raffled = true;
				this.upload();
			},
			catchError(error => Observable.throw('Server error'))
		);
	}

	public handleError(error: any) {
		console.error(error);
	}
}
