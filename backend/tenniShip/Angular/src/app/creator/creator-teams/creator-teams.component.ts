import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { CreatorService } from '../creator.service';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';

@Component({
	selector: 'app-creator-teams',
	templateUrl: './creator-teams.component.html',
	styleUrls: []
})
export class CreatorTeamsComponent implements OnInit {

	@Output()
	changeToRaffle = new EventEmitter<string>();

	public myTeams: string[] = new Array(18);
	// public myTeams: string[] = new Array (
    //     "Valencia",
    //     "Spain",
    //     "Serbia",
    //     "PSG",
    //     "Portugal",
    //     "Poland",
    //     "Ajax",
    //     "Andorra",
    //     "Argentina",
    //     "Finland",
    //     "Australia",
    //     "Barcelona",
    //     "Germany",
    //     "Napoli",
    //     "Norway",
    //     "France",
    //     "Italy",
    //     "Real Madrid"
	// );
	public myErrors: boolean[] = new Array(18);
	public myMsgErrors: string[] = new Array(18);
	public next2: boolean = false;

	constructor(public creatorService: CreatorService) { }

	ngOnInit(): void {
		this.creatorService.getTeams().subscribe(
			teams => this.creatorService.teams = teams
		); 
	}

	checkTeams() {
		for (let index = 0; index < this.myTeams.length; index++) {
			if ((typeof this.myTeams[index] === 'undefined') || this.myTeams[index] === "") {
				this.myErrors[index] = true;
				this.myMsgErrors[index] = 'Introduce a team';
			} else if (!this.creatorService.existTeam(this.myTeams[index])) {
				this.myErrors[index] = true;
				this.myMsgErrors[index] = "Team doesn't exist";
			} else if (this.alreadyIntroduced(this.myTeams[index]) > 1) {
				this.myErrors[index] = true;
				this.myMsgErrors[index] = 'The team has already been introduced';
				} else {
				this.myErrors[index] = false;
				this.myMsgErrors[index] = '';
			}
		}
		this.checkCanContinue ();
	}

	private alreadyIntroduced (team: string){
		let aux = 0;
		for (let index = 0; index < 18; index++) {
			if (this.myTeams[index] === team) {
				aux++;
			}
		}
		return aux;
	}

	public next2Check () {
		this.checkTeams()
		if (this.next2) {
			this.creatorService.setFinalTeams(this.myTeams);
			this.creatorService.active = "raffle";
			this.changeToRaffle.emit(this.creatorService.active);
		}
	}

	public checkCanContinue () {
		this.myErrors.indexOf(true) === -1 ? this.next2 = true :this.next2 = false ;
	}
}
