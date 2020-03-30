import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CreatorService } from '../creator.service';

@Component({
	selector: 'app-creator-tournament',
	templateUrl: './creator-tournament.component.html',
	styleUrls: []
})
export class CreatorTournamentComponent implements OnInit {

	@Output()
	changeToTeams = new EventEmitter<string>();

	constructor(private creatorService: CreatorService) { }

	public tournamentContent: string;
	public tournamentImage: string;
	public msgError: string;
	public error: boolean = false;
	public errorImg: boolean = false;
	public next1: boolean = false;

	ngOnInit(): void {
		this.creatorService.getTournaments().subscribe(
			tournaments => this.creatorService.tournaments = tournaments,
			error => this.handleError(error)  
		);
	}

	checkTournaments(): boolean {
		if (typeof this.tournamentImage === 'undefined'){
			this.errorImg = true;
		} else if (typeof this.tournamentImage !== 'undefined'){
			this.errorImg = false;
		}

		if ((this.tournamentContent === "") || (typeof this.tournamentContent === 'undefined') ){
			this.msgError = "Introduce a tournament";
			this.error = true;
			this.next1 = false;
			return false;
		} else if (this.creatorService.existTournament(this.tournamentContent)){
			this.msgError = "Tournament already exist";
			this.error = true;
			this.next1 = false;
			return false;
		} else {
			this.error = false;
			this.next1 = true;
			return true;
		}
	}

	public next1Check () {
		if (this.checkTournaments()) {
			this.creatorService.setFinalTournament(this.tournamentContent);
			this.creatorService.active = "teams";
			this.changeToTeams.emit(this.creatorService.active);
		}
	}
	
	public selectFile(event) {
		this.creatorService.selectedFiles = event.target.files;		
	}

	public handleError(error: any) {
		console.error(error);
	}
}
