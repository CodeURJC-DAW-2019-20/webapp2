import { Component, OnInit } from '@angular/core';
import { CreatorService } from '../creator.service';
import { newTournament } from '../../model/newtournament'; 
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';

@Component({
	selector: 'app-creator-raffle',
	templateUrl: './creator-raffle.component.html',
	styleUrls: []
})
export class CreatorRaffleComponent implements OnInit {

	public raffled: boolean = false;

	constructor(private creatorService: CreatorService) { }

	ngOnInit(): void {
	}

	public getTeams() {
		return this.creatorService.getFinalTeams();
	}

	public getTournament() {
		return this.creatorService.getFinalTournament();
	}

	public raffle() {
		let tournamentName: string = this.creatorService.getFinalTournament();
		let teams: string [] = this.creatorService.getFinalTeams();
		let newTour: newTournament = { tournamentName, teams };
		this.creatorService.newTournament(newTour).subscribe(
			_ => { this.raffled = true},
			catchError(error => Observable.throw('Server error'))
		);
		// ADD IMAGE
	}
}
